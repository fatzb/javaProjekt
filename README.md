# Hamster Kombat - JavaFX Project

A simple clicker game developed in Java using JavaFX.  
In this game, you collect coins, buy upgrades, and earn passive income over time.

## Features
- Click the hamster to collect coins.
- Buy upgrades to increase your income.
- Passive income generation over time.
- Multilingual support (language bundle).
- Save and load system using MySQL database.

## Project Structure
- src/main/java/com/example/demo8hamster/ — Java source files.
- src/main/resources/com/example/demo8hamster/ — FXML layout files and assets (images, bundles).

## Requirements
- Java 17+
- JavaFX 17+
- (Optional) MySQL server if you want to enable the save/load feature.

## Database Setup (Optional)
If you want to enable the save/load functionality, set up a MySQL database:

1. Create a database called hamsterdb.
2. Create the following table:

`sql
create table person.game_data
(
    id            int auto_increment
        primary key,
    playername    varchar(100)                        null,
    coins         double                              null,
    passiveIncome double                              null,
    saveTime      timestamp default CURRENT_TIMESTAMP null,
    upgradeLevel  int       default 1                 null

);
