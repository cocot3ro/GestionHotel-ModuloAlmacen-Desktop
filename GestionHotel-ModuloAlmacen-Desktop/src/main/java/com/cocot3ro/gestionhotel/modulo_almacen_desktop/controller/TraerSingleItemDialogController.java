package com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class TraerSingleItemDialogController {

    @FXML
    private Label lblNombre;

    private AlmacenItem item;

    @FXML
    private Spinner<Integer> spnCantidad;

    public Pair<AlmacenItem, Integer> getData() {
        return new Pair<>(item, spnCantidad.getValue());
    }

    public void setData(AlmacenItem item) {
        this.item = item;
        lblNombre.setText(item.getNombre());
        spnCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, item.getCantidad(), 0));
    }
}
