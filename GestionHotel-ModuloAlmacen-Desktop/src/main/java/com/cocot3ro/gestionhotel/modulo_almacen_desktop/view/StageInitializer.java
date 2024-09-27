package com.cocot3ro.gestionhotel.modulo_almacen_desktop.view;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.view.event.StageReadyEvent;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final String title;
    private final ApplicationContext context;
    @Value("classpath:/fxml/main-frame.fxml")
    private Resource res;

    public StageInitializer(@Value("${spring.application.ui.title}") String title, ApplicationContext context) {
        this.title = title;
        this.context = context;
    }

    private void removeInitialFocus(Scene scene) {
        Platform.runLater(() -> scene.getRoot().requestFocus());
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(res.getURL());
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setMinWidth(800);
                newStage.setMinHeight(600);
                newStage.setTitle(title);

                removeInitialFocus(scene);

                event.getStage().close();
                newStage.show();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
