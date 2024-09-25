package com.cocot3ro.gestionhotel.modulo_almacen.desktop.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlmacenModel {

    public Long id;
    public String nombre;
    public Integer cantidad;
    public Integer minimo;
    public Integer pack;

    public AlmacenItem toItem() {
        return new AlmacenItem(this.id, this.nombre, this.cantidad, this.pack, this.minimo);
    }
}
