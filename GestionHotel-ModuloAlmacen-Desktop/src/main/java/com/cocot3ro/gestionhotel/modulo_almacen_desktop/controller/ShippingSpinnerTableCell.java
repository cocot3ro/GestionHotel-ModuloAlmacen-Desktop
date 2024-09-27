package com.cocot3ro.gestionhotel.modulo_almacen_desktop.controller;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.model.AlmacenItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;

import java.util.Map;
import java.util.function.Function;

public class ShippingSpinnerTableCell extends TableCell<AlmacenItem, Integer> {

    private final Spinner<Integer> spinner = new Spinner<>();

    private final Function<AlmacenItem, Integer> min;
    private final Function<AlmacenItem, Integer> max;
    private final Function<AlmacenItem, Integer> value;

    public ShippingSpinnerTableCell(
            Map<AlmacenItem, Integer> itemMap,
            Function<AlmacenItem, Integer> min,
            Function<AlmacenItem, Integer> max,
            Function<AlmacenItem, Integer> value
    ) {
        this.min = min;
        this.max = max;
        this.value = value;

        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            AlmacenItem almacenItem = getTableRow().getItem();
            if (almacenItem != null) {
                itemMap.put(almacenItem, newValue);
            }
        });
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
            setGraphic(null);
            return;
        }

        AlmacenItem almacenItem = getTableRow().getItem();

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min.apply(almacenItem), max.apply(almacenItem), value.apply(almacenItem));
        spinner.setValueFactory(valueFactory);

        setGraphic(spinner);
    }
}

