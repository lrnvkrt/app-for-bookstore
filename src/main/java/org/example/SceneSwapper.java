package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwapper {
    public static void changeScene(Node node, String fxml){
        try {
            Parent pane = FXMLLoader.load(
                    Objects.requireNonNull(Main.class.getResource(fxml))
            );
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
