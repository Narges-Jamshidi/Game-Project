package org.example.gameproject.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.gameproject.PaneController;

public class HelloController {

    @FXML
    void finishGame(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void startGame(MouseEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        Scene scene = new Scene(PaneController.getPaneController().getLoginPage(), 1000, 630);
        stage.setScene(scene);
        stage.show();
    }
}