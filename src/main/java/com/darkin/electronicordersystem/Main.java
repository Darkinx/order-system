package com.darkin.electronicordersystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static final String CURRENCY = "₱";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login.fxml"));
//      Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//      Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage.initStyle(StageStyle.UNDECORATED); //this remove the window button (maximize, close, and minimize)
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}