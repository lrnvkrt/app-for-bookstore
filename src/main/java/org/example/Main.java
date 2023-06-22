package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scene/main_menu_scene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Книжный магазин");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            System.out.println("Connection established! :)");
        }
    }
}