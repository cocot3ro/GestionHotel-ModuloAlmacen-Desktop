module clientdesktop {
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires reactor.core;
    requires org.reactivestreams;
    requires reactor.netty.http;
    requires io.netty.common;
    requires spring.messaging;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller to javafx.fxml;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.model;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.model to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.view;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.view to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    opens fxml;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.config;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.config to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.client;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.client to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

    exports com.cocot3ro.gestionhotel.modulo_almacen.desktop.client.rsclient;
    opens com.cocot3ro.gestionhotel.modulo_almacen.desktop.client.rsclient to javafx.fxml, spring.beans, spring.context, spring.core, lombok;

}