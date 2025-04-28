package com.example.demo8hamster;

public class GameData {
    private  int upgradeLevel;
    private String playerName;
    private double coins;

    private double passiveIncome;
    public double getCoins() {
        return coins;
    }


    public GameData(String playerName, double coins, double passiveIncome , int upgradeLevel) {
        this.playerName = playerName;
        this.coins = coins;
        this.passiveIncome = passiveIncome;
        this.upgradeLevel =upgradeLevel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }



    public void setCoins(double coins) {
        this.coins = coins;
    }

    public double getPassiveIncome() {
        return passiveIncome;
    }

    public void setPassiveIncome(double passiveIncome) {
        this.passiveIncome = passiveIncome;
    }
    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
}
