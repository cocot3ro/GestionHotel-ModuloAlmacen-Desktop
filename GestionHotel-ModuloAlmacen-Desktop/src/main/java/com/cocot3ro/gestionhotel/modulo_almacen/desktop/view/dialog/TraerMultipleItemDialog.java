package com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog;

import com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller.TraerMultipleItemDialogController;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.model.AlmacenItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TraerMultipleItemDialog extends Dialog<String> {

    @Setter
    private Consumer<Map<AlmacenItem, Integer>> onAccept;

    private TraerMultipleItemDialogController controller;

    public TraerMultipleItemDialog(List<AlmacenItem> data) {
        loadFxml();

        setTitle("Traer del almacen");

        controller.setData(data);

        ButtonType acceptButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(acceptButtonType, cancelButtonType);

        Button acceptButton = (Button) getDialogPane().lookupButton(acceptButtonType);
        acceptButton.setOnAction(event -> handleAcceptAction());

        setResultConverter(buttonType -> {
            if (buttonType == acceptButtonType) {
                return "Aceptar";
            }

            return null;
        });
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/traer-multiple-item-dialog.fxml"));

        try {
            setDialogPane(loader.load());
            controller = loader.getController();
        } catch (Exception e) {
            Logger.getLogger(TraerMultipleItemDialog.class.getName()).log(Level.SEVERE, "No se pudo cargar el layout", e);
            throw new RuntimeException(e);
        }
    }

    private void handleAcceptAction() {
        onAccept.accept(
                controller.getData()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() > 0)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        close();
    }
}
