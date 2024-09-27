package com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.event;

import org.springframework.context.ApplicationEvent;

public class CloseEvent extends ApplicationEvent {

    public CloseEvent(Object source) {
        super(source);
    }
}
