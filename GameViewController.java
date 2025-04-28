package com.example.demo8hamster;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {
    @FXML
    private Button resetButton;
    @FXML
    private Label levelLabel;
    @FXML
    private Label coinsLabel;
    @FXML
    private Label passiveIncomeLabel;
    @FXML
    private Button upgradeButton;
   // @FXML
   // private Label upgradeLevel;
    @FXML
    private Button clickButton;
    @FXML
    private Button saveButton;
    private ResourceBundle bundle;
    private String playerName;
    private int upgradeLevel =1;
    private double coins = 0;
    private double passiveIncome =0.0;
    private double upgradeCost =10;
    private Timeline passiveIncomeTimer;
    @FXML
    public void handleClick(ActionEvent actionEvent) {
        coins++;
        updateUI();
        updateUpgradeButton();
        System.out.println("clicking test");
    }
    private void updateUI(){
        System.out.println(" test coins ="+coins );
        coinsLabel.setText(String.format(bundle.getString("coins"),coins));
        passiveIncomeLabel.setText(String.format(bundle.getString("passiveIncome"), getPassiveIncome()));
        upgradeButton.setText(String.format(bundle.getString("upgrade_button"),upgradeCost));
    }

    @Override
    public void initialize(URL location ,ResourceBundle resources){
        this.bundle=resources;
        updateUI();
        updateUpgradeButton();
        clickButton.setText(bundle.getString("click"));
        saveButton.setText(bundle.getString("game_save"));
        resetButton.setText(bundle.getString("game_reset"));
        passiveIncomeLabel.setText(bundle.getString("passiveIncome"));
        /////////////////////////////////ResourceBundle
        passiveIncomeTimer = new Timeline(new KeyFrame(Duration.seconds(20),e -> {
            coins+=passiveIncome;
            updateUI();
            updateUpgradeButton();
        }));
        passiveIncomeTimer.setCycleCount(Timeline.INDEFINITE);
        passiveIncomeTimer.play();
        ////////////////////////////////////////Timer
        GameData data = DatabaseManager.LoaLatestGame();
        if (data!=null){
            this.playerName= data.getPlayerName();
            this.coins= data.getCoins();
            this.passiveIncome=data.getPassiveIncome();
            this.upgradeLevel= data.getUpgradeLevel();
            updateUI();
        }
        ////////////////////////////Loadlatestgame
        loadSavedGame();
        updateUI();
    }
    @FXML
    public void handleSave(ActionEvent actionEvent) {
    GameData data = new GameData(playerName,coins,passiveIncome ,upgradeLevel);
    DatabaseManager.saveGame(data);
    showAlert(bundle.getString("game_save"));
    }
    @FXML
    public void handleUpgrade(ActionEvent actionEvent) {
        if (coins >= upgradeCost){
            coins -= upgradeCost;
            upgradeLevel++;
            passiveIncome+=2;
            upgradeCost*=10;
            updateUI();
            updateUpgradeButton();
            upgradeLevelLabel();
        }else{
            System.out.println("no more coins");
        }


    }

    private void upgradeLevelLabel() {
        levelLabel.setText(String.format(bundle.getString("level_display") , upgradeLevel));
    }

    private void updateUpgradeButton(){
        String labelText;
        if (coins>=upgradeCost){
            labelText = String.format(bundle.getString("upgrade_button"),upgradeCost);
            upgradeButton.setDisable(false);
        }else {
            labelText = String.format(bundle.getString("upgrade_button"), upgradeCost);
            upgradeButton.setDisable(true);
        }
        upgradeButton.setText(labelText);

    }
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public double getPassiveIncome() {

        return passiveIncome;
    }

    public void setPassiveIncome(double passiveIncome) {
        this.passiveIncome = passiveIncome;
    }

    @FXML
    private void handleReset(ActionEvent event) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "DELETE FROM game_data";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.println("info get cleared");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("error cant clear");
        }
        coins = 0;
        passiveIncome = 0;
        updateUI();
        updateUpgradeButton();
        showAlert(bundle.getString("game_reset"));
    }

    private void showAlert(String message) {
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void loadSavedGame() {
       GameData data = DatabaseManager.loadGame(playerName);

        if (data != null) {
            this.playerName = data.getPlayerName();
            this.coins = data.getCoins();
            this.passiveIncome = data.getPassiveIncome();
            this.upgradeLevel = data.getUpgradeLevel();
            updateUI();
            updateUpgradeButton();
            upgradeLevelLabel();
            System.out.println("loaded info  : " + coins + "coins & level : " +upgradeLevel);
        }
    }
}
