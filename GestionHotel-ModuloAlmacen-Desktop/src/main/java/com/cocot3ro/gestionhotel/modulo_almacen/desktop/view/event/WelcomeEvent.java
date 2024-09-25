package com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class WelcomeEvent extends ApplicationEvent {

    public WelcomeEvent(Stage stage) {
        super(stage);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}
