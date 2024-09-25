package com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event;

import org.springframework.context.ApplicationEvent;

public class BaseUrlChangedEvent extends ApplicationEvent {

    public BaseUrlChangedEvent(String baseUrl) {
        super(baseUrl);
    }

    public String getBaseUrl() {
        return (String) getSource();
    }
}
