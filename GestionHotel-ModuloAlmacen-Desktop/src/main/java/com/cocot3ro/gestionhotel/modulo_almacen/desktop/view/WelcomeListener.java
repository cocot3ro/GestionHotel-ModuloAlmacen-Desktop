package com.cocot3ro.gestionhotel.modulo_almacen.desktop.view;

import com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller.WelcomeController;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.event.WelcomeEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WelcomeListener implements ApplicationListener<WelcomeEvent> {

    private final ApplicationContext context;

    @Value("${spring.application.ui.title}")
    private String title;

    @Value("classpath:/fxml/welcome-view.fxml")
    private Resource res;

    @Override
    public void onApplicationEvent(WelcomeEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(res.getURL());
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            WelcomeController controller = loader.getController();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

            Stage stage = event.getStage();
            stage.setTitle(title);

            controller.setStage(stage);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
