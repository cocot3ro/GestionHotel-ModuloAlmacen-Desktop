package com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ToolBarController {

    @Setter
    private Runnable onNuevo;

    @Setter
    private Runnable onTraer;

    @Setter
    private Runnable onShipping;

    @Setter
    private Runnable onFilter;

    @Getter
    @FXML
    private ToggleButton btnFilter;

    @FXML
    public void onBtnNuevoClicked() {
        if (onNuevo != null) {
            onNuevo.run();
        }
    }

    @FXML
    public void onBtnTraerClicked() {
        if (onTraer != null) {
            onTraer.run();
        }
    }

    @FXML
    public void onBtnShippingClicked() {
        if (onShipping != null) {
            onShipping.run();
        }
    }

    @FXML
    public void onBtnFilterClicked() {
        if (onFilter != null) {
            onFilter.run();
        }
    }
}
