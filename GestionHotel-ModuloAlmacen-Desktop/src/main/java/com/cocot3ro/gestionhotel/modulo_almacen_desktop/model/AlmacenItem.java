package com.cocot3ro.gestionhotel.modulo_almacen_desktop.model;

import javafx.beans.property.*;

import java.util.Objects;

public class AlmacenItem {
    private final LongProperty id;
    private final StringProperty nombre;
    private final IntegerProperty cantidad;
    private final IntegerProperty pack;
    private final IntegerProperty minimo;

    public AlmacenItem() {
        this(0L, "", 0, 0, 0);
    }

    public AlmacenItem(String nombre, Integer cantidad, Integer pack, Integer minimo) {
        this(0L, nombre, cantidad, pack, minimo);
    }

    public AlmacenItem(Long id, String nombre, Integer cantidad, Integer pack, Integer minimo) {
        this.id = new SimpleLongProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.pack = new SimpleIntegerProperty(pack);
        this.minimo = new SimpleIntegerProperty(minimo);
    }

    public AlmacenModel toModel() {
        return new AlmacenModel(this.id.get(), this.nombre.get(), this.cantidad.get(), this.minimo.get(), this.pack.get());
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public int getPack() {
        return pack.get();
    }

    public void setPack(int pack) {
        this.pack.set(pack);
    }

    public IntegerProperty packProperty() {
        return pack;
    }

    public int getMinimo() {
        return minimo.get();
    }

    public void setMinimo(int minimo) {
        this.minimo.set(minimo);
    }

    public IntegerProperty minimoProperty() {
        return minimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlmacenItem that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNombre(), that.getNombre()) && Objects.equals(getCantidad(), that.getCantidad()) && Objects.equals(getPack(), that.getPack()) && Objects.equals(getMinimo(), that.getMinimo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getCantidad(), getPack(), getMinimo());
    }

    @Override
    public String toString() {
        return "AlmacenItem{" +
                "id=" + getId() +
                ", nombre=" + getNombre() +
                ", cantidad=" + getCantidad() +
                ", pack=" + getPack() +
                ", minimo=" + getMinimo() +
                '}';
    }
}