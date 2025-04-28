package com.example.demo8hamster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    @FXML
    private Label selectLanguage;
    @FXML
    private Label enterName;
    @FXML
    private Button startButton;
    @FXML
    private ComboBox<String> languageBox;
    @FXML
    private TextField nameField;
    private ResourceBundle bundle;

    @FXML
    private void handleStart(ActionEvent event) {
        String name = nameField.getText();
        String language = languageBox.getValue();


        if (name == null || name.trim().isEmpty()) {
            showAlert("نام را وارد کنید / Please enter your name");
            return;
        }

        if (language == null) {
            showAlert("زبان را انتخاب کنید / Please select a language");
            return;
        }

        Locale selectedLocale;
        switch (language) {
            case "English":
                selectedLocale = new Locale("en");
                break;
            case "Deutsch":
                selectedLocale = new Locale("de");
                break;
            case "فارسی":
            default:
                selectedLocale = new Locale("fa");
                break;
        }

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.example.demo8hamster.bundle.strings", selectedLocale);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo8hamster/game_view.fxml"), bundle);
            Parent root = loader.load();

            // فرستادن نام کاربر به کنترلر بازی
            GameViewController controller = loader.getController();
            controller.setPlayerName(name);
            controller.loadSavedGame();

            // تغییر صحنه
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("خطا در بارگذاری صفحه بازی");
        }
    }
    @FXML
    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }



    @Override
    public void initialize(URL url, ResourceBundle resource) {
       this.bundle=resource;
        languageBox.getItems().addAll( "English" , "فارسی" , "Deutsch");
       languageBox.getSelectionModel().selectFirst();
        startButton.setText(bundle.getString("start"));
        enterName.setText(bundle.getString("EnterName"));
       selectLanguage.setText(bundle.getString("SelectLanguage"));

    }
}
