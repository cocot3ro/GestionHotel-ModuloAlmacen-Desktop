package com.cocot3ro.gestionhotel.modulo_almacen_desktop;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.MainFrame;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModuloAlmacenDesktopApplication {

    public static void main(String[] args) {
        Application.launch(MainFrame.class, args);
    }

}
