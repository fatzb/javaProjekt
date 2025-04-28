package com.example.demo8hamster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class
Main  extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Locale selectedLocale = new Locale("en");
        ResourceBundle bundle=ResourceBundle.getBundle("com.example.demo8hamster.bundle.strings" , selectedLocale);
        FXMLLoader loader=  new FXMLLoader(getClass().getResource("main_view.fxml"),bundle);
        Parent parent= loader.load();
        Scene scene= new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
