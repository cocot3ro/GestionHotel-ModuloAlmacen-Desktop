package com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class ConnectionErrorDialog extends Alert {

    public ConnectionErrorDialog(Node owner, Runnable onRetry, Runnable onSettings) {
        super(AlertType.ERROR);

        setTitle("Error");
        setHeaderText("Error de conexión");
        setContentText("No se pudo conectar al servidor");
        initOwner(owner.getScene().getWindow());

        ButtonType exit = new ButtonType("Salir", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType settings = new ButtonType("Configuración", ButtonBar.ButtonData.HELP);
        ButtonType retry = new ButtonType("Reintentar", ButtonBar.ButtonData.OK_DONE);

        getButtonTypes().setAll(exit, settings, retry);
        showAndWait().ifPresent(buttonType -> {
            if (buttonType == exit) {
                Platform.exit();
            } else if (buttonType == retry) {
                onRetry.run();
            } else if (buttonType == settings) {
                onSettings.run();
            }
        });
    }

}