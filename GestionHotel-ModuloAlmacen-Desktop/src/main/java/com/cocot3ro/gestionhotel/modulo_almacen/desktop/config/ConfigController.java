package com.cocot3ro.gestionhotel.modulo_almacen.desktop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ConfigController {

    private final ServerConfig serverConfig;

    @Value("${gestion-hotel.modulo-almacen.desktop.config-file}")
    private String configPath;

    private void saveConfig() {
        try (FileWriter writer = new FileWriter(configPath)) {
            String str = """
                    gestion-hotel:
                      server:
                        address: %s
                        port: %d
                    """;

            writer.write(String.format(str, serverConfig.getAddress(), serverConfig.getPort()));
        } catch (IOException e) {
            Logger.getLogger(ConfigController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getServerAddress() {
        return serverConfig.getAddress();
    }

    public void setServerAddress(String newAddress) {
        serverConfig.setAddress(newAddress);
        serverConfig.updateBaseUrl();
        saveConfig();
    }

    public String getServerBaseUrl() {
        return serverConfig.getBaseUrl();
    }

    public int getServerPort() {
        return serverConfig.getPort();
    }

    public void setServerPort(int newPort) {
        serverConfig.setPort(newPort);
        serverConfig.updateBaseUrl();
        saveConfig();
    }
}
