package com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenItem;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TraerMultipleItemDialogController {

    private final Map<AlmacenItem, Integer> itemMap = new HashMap<>();

    @FXML
    private TableView<AlmacenItem> tblTraerAlmacen;
    @FXML
    private TableColumn<AlmacenItem, String> colNombre;
    @FXML
    private TableColumn<AlmacenItem, Integer> colCantidad;

    public Map<AlmacenItem, Integer> getData() {
        return new HashMap<>(itemMap);
    }

    public void setData(List<AlmacenItem> data) {
        itemMap.putAll(data.stream().collect(HashMap::new, (m, v) -> m.put(v, 0), HashMap::putAll));
        tblTraerAlmacen.getItems().setAll(data);
    }

    public void initialize() {
        tblTraerAlmacen.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            colNombre.setPrefWidth(width * 0.7);
            colCantidad.setPrefWidth(width * 0.3);
        });

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colNombre.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(item);
                if (getTableRow() != null) {
                    AlmacenItem almacenItem = getTableRow().getItem();
                    if (almacenItem.getCantidad() == 0) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }

            }
        });

        colCantidad.setCellFactory(getSpinnerCellFactory());
    }

    private Callback<TableColumn<AlmacenItem, Integer>, TableCell<AlmacenItem, Integer>> getSpinnerCellFactory() {
        return column -> new TraerSpinnerTableCell(itemMap, almacenItem -> 0, AlmacenItem::getCantidad, itemMap::get);
    }
}
