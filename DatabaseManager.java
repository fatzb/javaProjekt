package com.example.demo8hamster;
import java.sql.*;



public class DatabaseManager {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url ="jdbc:mysql://localhost:3208/person";
        String user ="fatemeh";
        String password ="Hamid021@";
        return DriverManager.getConnection(url,user,password);
    }
    public static void saveGame(GameData data){
        //ایا کاربر از قبل بوده یا نبوده (اخر اضافه شده)
        //اگه هست update اگه نیست insert
        try {
        Connection conn = getConnection();
            String checkSql= "SELECT COUNT(*) FROM game_data WHERE playername =?";
            PreparedStatement checkstat = conn.prepareStatement(checkSql);
            checkstat.setString(1,data.getPlayerName());
            ResultSet re = checkstat.executeQuery();
            re.next();
            String sql;

            if (re.next() && re.getInt(1)>0){
                sql = "UPDATE game_data SET coins =? , passiveIncome=?, upgradeLevel=? WHERE playername=? ";
            }else {
                sql ="INSERT INTO game_data (coins ,passiveIncome,upgradeLevel ,playername) VALUES (?,?,?,?)";}
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setDouble(1,data.getCoins());
        stm.setDouble(2,data.getPassiveIncome());
        stm.setInt(3,data.getUpgradeLevel());
        stm.setString(4,data.getPlayerName());
        stm.executeUpdate();
        System.out.println("Game save successfully");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static GameData LoaLatestGame(){
        String sql = "SELECT playername , coins , passiveIncome , upgradeLevel FROM game_data ORDER BY id DESC LIMIT 1";
        try
            ( Connection con= getConnection();
        PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery()){
                if (rs.next()) {
                    String name = rs.getString("playerName");
                    double coins = rs.getDouble("coins");
                    double passiveIncome = rs.getDouble("passiveIncome");
                    int upgradeLevel = rs.getInt("upgradeLevel");
                    return new GameData(name, coins, passiveIncome ,upgradeLevel);

                }
        } catch (SQLException e) {
            System.out.println("error laoding game");
            e.printStackTrace();
        }return null ;
    }
    public static GameData loadGame(String playername) {
        String sql = "SELECT * FROM game_data WHERE playername= ? ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setString(1,playername);
             ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String playerName = rs.getString("playername");
                double coins = rs.getDouble("coins");
                double passiveIncome = rs.getDouble("passiveIncome");
                int upgradeLevel = rs.getInt("upgradeLevel");
                return new GameData(playerName, coins, passiveIncome,upgradeLevel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }




}
