package com.cocot3ro.gestionhotel.modulo_almacen.desktop.view.dialog;

import com.cocot3ro.gestionhotel.modulo_almacen.desktop.controller.TraerSingleItemDialogController;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.model.AlmacenItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Pair;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TraerSingleItemDialog extends Dialog<String> {

    @Setter
    private Consumer<Pair<AlmacenItem, Integer>> onAccept;

    private TraerSingleItemDialogController controller;

    public TraerSingleItemDialog(AlmacenItem item) {
        loadFxml();

        setTitle("Traer del almacen");

        controller.setData(item);

        ButtonType acceptButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(acceptButtonType, cancelButtonType);

        Button acceptButton = (Button) getDialogPane().lookupButton(acceptButtonType);
        acceptButton.setOnAction(event -> handleAcceptAction());

        setResultConverter(buttonType -> {
            if (buttonType == acceptButtonType) {
                return "Aceptar";
            }

            return null;
        });
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/traer-single-item-dialog.fxml"));

        try {
            setDialogPane(loader.load());
            controller = loader.getController();
        } catch (Exception e) {
            Logger.getLogger(TraerSingleItemDialog.class.getName()).log(Level.SEVERE, "No se pudo cargar el layout", e);
        }
    }

    private void handleAcceptAction() {
        onAccept.accept(controller.getData());
        close();
    }
}
