package com.cocot3ro.gestionhotel.modulo_almacen_desktop.view;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.GestionHotelModuloAlmacenDesktopApplication;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.event.CloseEvent;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.event.WelcomeEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainFrame extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(GestionHotelModuloAlmacenDesktopApplication.class)
                .properties("spring.config.additional-location=" + getClass().getResource("/config/application-updated.yml"))
                .run(getParameters().getRaw().toArray(String[]::new));
    }

    @Override
    public void start(Stage stage) {
        context.publishEvent(new WelcomeEvent(stage));
    }

    @Override
    public void stop() {
        context.publishEvent(new CloseEvent(this));
        context.close();
        Platform.exit();
    }
}
