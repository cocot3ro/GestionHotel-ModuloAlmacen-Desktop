package com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class NewItemMenuController {

    @Setter
    private Consumer<AlmacenItem> onGuardar;

    @Setter
    private Runnable onCancelar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtPack;

    @FXML
    private TextField txtMinimo;

    public void initialize() {
        setStringProperty(txtNombre);
        setIntegerProperty(txtCantidad);
        setIntegerProperty(txtPack);
        setIntegerProperty(txtMinimo);
    }

    private void setStringProperty(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                textField.setStyle("");
            } else {
                textField.setStyle("-fx-border-color: red");
            }
        });
    }

    private void setIntegerProperty(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && newValue.matches("\\d+")) {
                textField.setStyle("");
            } else {
                textField.setStyle("-fx-border-color: red");
            }
        });
    }

    public void clear() {
        txtNombre.clear();
        txtNombre.setStyle("");

        txtCantidad.clear();
        txtCantidad.setStyle("");

        txtPack.clear();
        txtPack.setStyle("");

        txtMinimo.clear();
        txtMinimo.setStyle("");
    }

    @FXML
    public void save() {
        if (onGuardar != null) {
            onGuardar.accept(new AlmacenItem(
                    txtNombre.getText(),
                    Integer.parseInt(txtCantidad.getText()),
                    Integer.parseInt(txtPack.getText()),
                    Integer.parseInt(txtMinimo.getText())
            ));
        }
    }

    @FXML
    public void cancel() {
        if (onCancelar != null) {
            onCancelar.run();
        }
    }

    public boolean validate() {
        boolean isValid = validateStringField(txtNombre);
        isValid = validateIntegerField(txtCantidad) && isValid;
        isValid = validateIntegerField(txtPack) && isValid;
        isValid = validateIntegerField(txtMinimo) && isValid;

        return isValid;
    }

    private boolean validateIntegerField(TextField textField) {
        if (textField.getText().isEmpty() || !textField.getText().matches("\\d+")) {
            textField.setStyle("-fx-border-color: red");
            return false;
        } else {
            textField.setStyle("");
            return true;
        }
    }

    private boolean validateStringField(TextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-border-color: red");
            return false;
        } else {
            textField.setStyle("");
            return true;
        }
    }
}
