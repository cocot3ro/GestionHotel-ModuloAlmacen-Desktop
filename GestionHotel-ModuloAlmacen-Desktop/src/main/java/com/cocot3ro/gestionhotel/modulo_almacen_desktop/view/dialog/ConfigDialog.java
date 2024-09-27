package com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.dialog;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller.ConfigDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import lombok.Setter;

import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigDialog extends Dialog<String> {

    @Setter
    private BiConsumer<String, Integer> onAccept;

    private ConfigDialogController controller;

    public ConfigDialog(String ip, int port) {
        loadFxml();

        setTitle("Configuración");

        controller.setIp(ip);
        controller.setPort(port);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/config-dialog.fxml"));

        try {
            setDialogPane(loader.load());
            controller = loader.getController();
        } catch (Exception e) {
            Logger.getLogger(TraerMultipleItemDialog.class.getName()).log(Level.SEVERE, "No se pudo cargar el layout", e);
            throw new RuntimeException(e);
        }
    }

    private void handleAcceptAction() {
        if (!controller.validate()) {
            return;
        }

        onAccept.accept(
                controller.getIp(),
                controller.getPort()
        );

        close();
    }
}
