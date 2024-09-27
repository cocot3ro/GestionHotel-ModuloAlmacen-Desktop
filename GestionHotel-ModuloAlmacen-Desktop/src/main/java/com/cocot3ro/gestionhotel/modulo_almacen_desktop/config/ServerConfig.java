package com.cocot3ro.gestionhotel.modulo_almacen_desktop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gestion-hotel.server")
public class ServerConfig {

    private String address;
    private int port;

    private String baseUrl;

    public void updateBaseUrl() {
        this.baseUrl = String.format("%s:%d/api", address, port);
    }
}
