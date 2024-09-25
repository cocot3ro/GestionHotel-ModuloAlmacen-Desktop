package com.cocot3ro.gestionhotel.modulo_almacen.desktop.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AlmacenSubscriber implements Consumer<AlmacenItem[]> {
    private final ObservableList<AlmacenItem> data = FXCollections.observableArrayList();

    @Override
    public void accept(AlmacenItem[] almacenItems) {
        Platform.runLater(() -> data.setAll(almacenItems));
    }

    public ObservableList<AlmacenItem> data() {
        return data;
    }
}
