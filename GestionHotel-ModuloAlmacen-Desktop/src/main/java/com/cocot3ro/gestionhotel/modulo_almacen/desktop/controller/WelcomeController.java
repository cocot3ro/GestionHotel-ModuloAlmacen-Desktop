package com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller;

import com.cocot3ro.gestionhotel.modulo_almacen.desktop.client.ICoreClient;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.config.ConfigController;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog.ConfigDialog;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog.ConnectionErrorDialog;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event.BaseUrlChangedEvent;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event.CloseEvent;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event.StageReadyEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WelcomeController implements ApplicationListener<CloseEvent>, Disposable {

    private final ICoreClient coreClient;
    private final ConfigController configController;
    private final ApplicationContext context;

    @Setter
    private Stage stage;

    @FXML
    private VBox root;

    @FXML
    private Label lblServerAddress;

    private Disposable disposable;

    public void initialize() {
        lblServerAddress.setText(String.format("%s:%d", configController.getServerAddress(), configController.getServerPort()));

        coreClient.setOnConnectError(() -> Platform.runLater(this::connectionError));

        checkConnection();
    }

    private void checkConnection() {
        dispose();

        disposable = coreClient.checkConnection()
                .map(Objects::nonNull)
                .subscribe(response -> {
                    if (response) {
                        dispose();
                        context.publishEvent(new StageReadyEvent(stage));
                    } else {
                        Platform.runLater(this::connectionError);
                    }
                });
    }

    private void connectionError() {
        new ConnectionErrorDialog(root, this::checkConnection, this::showSettingsDialog);
    }

    private void showSettingsDialog() {
        ConfigDialog dialog = new ConfigDialog(configController.getServerAddress(), configController.getServerPort());
        dialog.setOnAccept((address, port) -> {
            configController.setServerAddress(address);
            configController.setServerPort(port);

            context.publishEvent(new BaseUrlChangedEvent(configController.getServerBaseUrl()));

            dispose();

            checkConnection();
        });
        dialog.showAndWait();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void onApplicationEvent(CloseEvent event) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
