package com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class ConfigDialogController {

    @FXML
    private TextField txtDireccionIP;

    @FXML
    private TextField txtPuerto;

    public String getIp() {
        return txtDireccionIP.getText();
    }

    public void setIp(String ip) {
        txtDireccionIP.setText(ip);
    }

    public int getPort() {
        return Integer.parseInt(txtPuerto.getText());
    }

    public void setPort(int port) {
        txtPuerto.setText(String.valueOf(port));
    }

    public boolean validate() {
        if (txtDireccionIP.getText().isEmpty()) {
            return false;
        }

        if (!txtDireccionIP.getText().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") && !txtDireccionIP.getText().matches("localhost")) {
            return false;
        }

        if (txtPuerto.getText().isEmpty()) {
            return false;
        }

        if (Integer.parseInt(txtPuerto.getText()) < 0) {
            return false;
        }

        if (Integer.parseInt(txtPuerto.getText()) > 65535) {
            return false;
        }

        return true;
    }
}
