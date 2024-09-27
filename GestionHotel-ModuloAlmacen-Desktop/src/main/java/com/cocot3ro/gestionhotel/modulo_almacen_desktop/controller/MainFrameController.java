package com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.IAlmacenClient;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.config.ConfigController;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenItem;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenModel;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenSubscriber;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.dialog.*;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.event.BaseUrlChangedEvent;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.event.CloseEvent;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MainFrameController implements ApplicationListener<CloseEvent>, Disposable {

    private final ApplicationContext context;

    private final IAlmacenClient almacenClient;

    private final ConfigController configController;

    private final Predicate<AlmacenItem> filter = item -> item.getCantidad() <= item.getMinimo();

    @SuppressWarnings("FieldCanBeLocal")
    private SortedList<AlmacenItem> sortedList;
    private FilteredList<AlmacenItem> filteredList;

    private Disposable disposable;

    private Node newItemMenuPane;
    private NewItemMenuController newItemMenuController;

    private Node editItemMenuPane;
    private EditItemMenuController editItemMenuController;

    @FXML
    private BorderPane root;

    @FXML
    private ToolBarController toolbarController;

    @FXML
    private TableView<AlmacenItem> tblAlmacen;
    @FXML
    private TableColumn<AlmacenItem, String> colNombre;
    @FXML
    private TableColumn<AlmacenItem, Integer> colCantidad;
    @FXML
    private TableColumn<AlmacenItem, Integer> colPack;
    @FXML
    private TableColumn<AlmacenItem, Integer> colMinimo;

    private ContextMenu listContextMenu;

    public MainFrameController(
            ApplicationContext context,
            IAlmacenClient almacenClient,
            ConfigController configController
    ) {
        this.context = context;
        this.almacenClient = almacenClient;
        this.configController = configController;
    }

    @FXML
    public void initialize() {
        initToolBar();

        initContextMenu();

        initTable();

        fetchData();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void onApplicationEvent(CloseEvent event) {
        dispose();
    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @FXML
    public void dataImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setTitle("Importar datos");

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            try (var stream = Files.lines(file.toPath())) {
                stream.forEach(line -> {
                    String[] data = line.split(";");
                    AlmacenItem almacenItem = new AlmacenItem(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                    almacenClient.updateAlmacenEntry(almacenItem.toModel()).subscribe();
                });
            } catch (IOException e) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    @FXML
    public void dataExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setTitle("Exportar datos");
        fileChooser.setInitialFileName("almacen.csv");

        File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        if (file != null) {
            try (var writer = Files.newBufferedWriter(file.toPath())) {
                tblAlmacen.getItems().forEach(item -> {
                    try {
                        writer.write(item.getNombre() + ";" + item.getCantidad() + ";" + item.getPack() + ";" + item.getMinimo() + System.lineSeparator());
                    } catch (IOException e) {
                        Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                });
            } catch (IOException e) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    @FXML
    public void showSettingsDialog() {
        ConfigDialog dialog = new ConfigDialog(configController.getServerAddress(), configController.getServerPort());
        dialog.setOnAccept((address, port) -> {
            configController.setServerAddress(address);
            configController.setServerPort(port);

            context.publishEvent(new BaseUrlChangedEvent(configController.getServerBaseUrl()));

            dispose();

            fetchData();
        });
        dialog.showAndWait();
    }

    private void initToolBar() {
        toolbarController.setOnNuevo(() -> {
            if (root.getLeft() == getOrCreateNewItemMenu()) {
                root.setLeft(null);
            } else {
                root.setLeft(getOrCreateNewItemMenu());
            }
        });

        toolbarController.setOnTraer(() -> {
            List<AlmacenItem> items = tblAlmacen.getItems();
            if (!items.isEmpty()) {
                TraerMultipleItemDialog dialog = new TraerMultipleItemDialog(items);
                dialog.setOnAccept(data -> {
                    for (Map.Entry<AlmacenItem, Integer> entry : data.entrySet()) {
                        AlmacenItem almacenItem = entry.getKey();
                        almacenItem.setCantidad(almacenItem.getCantidad() - entry.getValue());
                        almacenClient.updateAlmacenEntry(almacenItem.toModel()).subscribe();
                    }
                });
                dialog.showAndWait();
            }
        });

        toolbarController.setOnShipping(() -> {
            List<AlmacenItem> items = tblAlmacen.getItems();
            if (!items.isEmpty()) {
                ShippingDialog dialog = new ShippingDialog(items);
                dialog.setOnAccept(entry -> {
                    for (Map.Entry<AlmacenItem, Integer> e : entry.entrySet()) {
                        AlmacenItem almacenItem = e.getKey();
                        almacenItem.setCantidad(almacenItem.getCantidad() + e.getValue());
                        almacenClient.updateAlmacenEntry(almacenItem.toModel()).subscribe();
                    }
                });
                dialog.showAndWait();
            }
        });

        toolbarController.setOnFilter(() -> {
            if (filteredList.getPredicate() == null) {
                filteredList.setPredicate(filter);

                ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/filter_off.png"))));
                image.setFitHeight(32);
                image.setFitWidth(32);
                toolbarController.getBtnFilter().setGraphic(image);
            } else {
                filteredList.setPredicate(null);

                ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/filter.png"))));
                image.setFitHeight(32);
                image.setFitWidth(32);
                toolbarController.getBtnFilter().setGraphic(image);
            }
        });
    }

    private Node getOrCreateNewItemMenu() {
        if (newItemMenuPane == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/new-item-menu.fxml"));
            try {
                newItemMenuPane = loader.load();
                newItemMenuController = loader.getController();

                newItemMenuController.setOnGuardar(almacenItem -> {
                    if (!newItemMenuController.validate()) return;

                    almacenClient.saveAlmacenEntry(almacenItem.toModel()).subscribe();
                    root.setLeft(null);
                });

                newItemMenuController.setOnCancelar(() -> root.setLeft(null));
            } catch (IOException e) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }

        newItemMenuController.clear();

        return newItemMenuPane;
    }

    private Node getOrCreateEditItemMenu() {
        if (editItemMenuPane == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit-item-menu.fxml"));
            try {
                editItemMenuPane = loader.load();
                editItemMenuController = loader.getController();

                editItemMenuController.setOnGuardar(almacenItem -> {
                    if (!editItemMenuController.validate()) return;

                    almacenClient.updateAlmacenEntry(almacenItem.toModel()).subscribe();
                    root.setLeft(null);
                });

                editItemMenuController.setOnCancelar(() -> root.setLeft(null));
            } catch (IOException e) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }

        editItemMenuController.clear();

        return editItemMenuPane;
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private void initContextMenu() {
        listContextMenu = new ContextMenu();

        MenuItem editMenuItem = new MenuItem("Editar");
        editMenuItem.setOnAction(actionEvent -> {
            AlmacenItem item = tblAlmacen.getSelectionModel().getSelectedItem();
            if (item != null) {
                if (root.getLeft() == getOrCreateEditItemMenu()) {
                    root.setLeft(null);
                } else {
                    root.setLeft(getOrCreateEditItemMenu());
                    editItemMenuController.setItem(item);
                }
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Borrar");
        deleteMenuItem.setOnAction(actionEvent -> {
            AlmacenItem almacenItem = tblAlmacen.getSelectionModel().getSelectedItem();
            if (almacenItem == null) return;

            deleteItem(almacenItem);
        });

        MenuItem bringMenuItem = new MenuItem("Traer");
        bringMenuItem.setOnAction(actionEvent -> {
            AlmacenItem item = tblAlmacen.getSelectionModel().getSelectedItem();
            if (item != null) {
                TraerSingleItemDialog dialog = new TraerSingleItemDialog(item);

                dialog.setOnAccept(entry -> {
                    AlmacenItem almacenItem = entry.getKey();
                    almacenItem.setCantidad(almacenItem.getCantidad() - entry.getValue());
                    almacenClient.updateAlmacenEntry(almacenItem.toModel()).subscribe();
                });

                dialog.showAndWait();
            }
        });

        listContextMenu.getItems().addAll(bringMenuItem, editMenuItem, deleteMenuItem);
    }

    private void deleteItem(AlmacenItem almacenItem) {
        Dialog<ButtonType> dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Confirmación");
        dialog.setHeaderText("¿Está seguro de que desea borrar el elemento?");
        dialog.setContentText(almacenItem.getNombre());

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                almacenClient.deleteAlmacenEntry(almacenItem.toModel());
            }
        });
    }

    private void initTable() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPack.setCellValueFactory(new PropertyValueFactory<>("pack"));
        colMinimo.setCellValueFactory(new PropertyValueFactory<>("minimo"));

        tblAlmacen.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            colNombre.setPrefWidth(width * 0.50);
            colCantidad.setPrefWidth(width * 0.15);
            colPack.setPrefWidth(width * 0.15);
            colMinimo.setPrefWidth(width * 0.15);
        });

        tblAlmacen.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(AlmacenItem item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                    getStyleClass().removeAll("red-row", "orange-row");
                } else {
                    if (item.getCantidad() == 0) {
                        if (!getStyleClass().contains("red-row")) {
                            getStyleClass().add("red-row");
                        }
                    } else if (item.getCantidad() <= item.getMinimo()) {
                        if (!getStyleClass().contains("orange-row")) {
                            getStyleClass().add("orange-row");
                        }
                    } else {
                        getStyleClass().removeAll("red-row", "orange-row");
                    }
                }

                contextMenuProperty().bind(
                        Bindings.when(emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(listContextMenu)
                );
            }
        });

        tblAlmacen.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                AlmacenItem item = tblAlmacen.getSelectionModel().getSelectedItem();

                if (item == null) return;

                deleteItem(item);
            }
        });
    }

    private void fetchData() {
        AlmacenSubscriber almacenSubscriber = new AlmacenSubscriber();
        almacenClient.setOnConnectError(() ->
                Platform.runLater(() -> new ConnectionErrorDialog(root, this::fetchData, this::showSettingsDialog))
        );

        disposable = almacenClient.getAlmacenEntries()
                .map(almacenModels -> Arrays.stream(almacenModels)
                        .map(AlmacenModel::toItem)
                        .toList()
                        .toArray(AlmacenItem[]::new)
                ).subscribe(almacenSubscriber);

        filteredList = new FilteredList<>(almacenSubscriber.data());

        sortedList = new SortedList<>(filteredList, Comparator.comparing(AlmacenItem::getId));

        sortedList.comparatorProperty().bind(tblAlmacen.comparatorProperty());

        tblAlmacen.setItems(sortedList);
    }
}
