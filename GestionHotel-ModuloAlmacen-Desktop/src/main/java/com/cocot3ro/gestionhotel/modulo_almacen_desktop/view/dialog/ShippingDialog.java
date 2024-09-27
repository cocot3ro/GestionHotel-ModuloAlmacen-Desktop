package com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.dialog;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller.ShippingDialogController;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ShippingDialog extends Dialog<String> {
    @Setter
    private Consumer<Map<AlmacenItem, Integer>> onAccept;

    private ShippingDialogController controller;

    public ShippingDialog(List<AlmacenItem> data) {
        loadFxml();

        setTitle("Añadir al almacen");

        controller.setData(data);

        ButtonType acceptButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shipping-dialog.fxml"));

        try {
            setDialogPane(loader.load());
            controller = loader.getController();
        } catch (Exception e) {
            Logger.getLogger(ShippingDialog.class.getName()).log(Level.SEVERE, "No se pudo cargar el layout", e);
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
