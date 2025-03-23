package org.example.gameproject.controler.databaseManage;

import org.example.gameproject.model.player.Backpack;
import org.example.gameproject.model.player.Player;

import java.sql.*;

public class PlayerDatabaseManage {
    private final String Url;
    private final String userId;
    private final String password;
    private static PlayerDatabaseManage playerDatabaseManage;

    public PlayerDatabaseManage() {
        Url = "jdbc:mysql://localhost/kingdomrush";
        userId = "root";
        password = "iamsabwina";
    }

    public static PlayerDatabaseManage getPlayerDatabaseManage() {
        if (playerDatabaseManage == null) {
            playerDatabaseManage = new PlayerDatabaseManage();
        }
        return playerDatabaseManage;
    }

    //پیدا کردن آیدی بازیکن به کمک پسورد =====================================================
    public int findIdByPassword(String password, String username) {
        int id = -1;
        String query = "SELECT `Id` FROM `player` WHERE `username`= ? AND `password`= ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("Id");
            } else {
                throw new SQLException("No record found for given username and password.");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    //پیدا کردن آیدی بازیکن به کمک یوزرنیم=====================================================
    public int findIdByUsername(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("SELECT `Id` FROM `player` WHERE `username`= '%s'", username);
            Statement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
//پیدا کردن پلیر به کمک آیدی =====================================================
    public Player findPlayer(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("SELECT `Id`, `username`, `password`, `level`, `jewel`, `health`, `freeze`, `coinSpell`, `Littleboy`, `heal` ,`money` FROM `player` WHERE `Id`= '%d' ", id);
            Statement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return new Player(id, rs.getString("username"), rs.getString("password"), rs.getInt("level"), rs.getInt("jewel"), rs.getInt("health"), new Backpack(rs.getInt("heal"), rs.getInt("Littleboy"), rs.getInt("coinSpell"), rs.getInt("freeze")), rs.getInt("money"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

// تغییر پسورد =====================================================
    public void updatePassword(String password, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("UPDATE `player` SET 'password' = '%s'  WHERE `id`='%d'", password, id);
            Statement statement = con.prepareStatement(sql);
            statement.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUsername(String username, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("UPDATE `player` SET 'username' = '%s'  WHERE `Id`='%d'", username, id);
            Statement statement = con.prepareStatement(sql);
            statement.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLevel(int level, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("UPDATE `player` SET 'level' = '%d'  WHERE `Id`='%d'", level, id);
            Statement statement = con.prepareStatement(sql);
            statement.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSpells(int freeze, int coin, int boy, int heal, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("UPDATE `player` SET `freeze`='%d',`coinSpell`='%d',`Littleboy`='%d',`heal`='%d'  WHERE `Id`='%d'", freeze, coin, boy, heal, id);
            Statement statement = con.prepareStatement(sql);
            statement.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateHealth(int health, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("UPDATE `player` SET `health` = '%d'  WHERE `Id`='%d'", health, id);
            Statement statement = con.prepareStatement(sql);
            statement.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateJewel(int jewel, int id) {
        try {
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = " UPDATE `player` SET `jewel` = ? WHERE `Id` = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, jewel);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPlayer(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("INSERT INTO `player`(`username`, `password`) VALUES ('%s','%s')", username, password);
            Statement stmt = con.prepareStatement(sql);
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMoney(int money, int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Url, userId, this.password);
            String sql = String.format("UPDATE `player` SET `money`= '%d'  WHERE `Id`='%d'", money, id);
            Statement statement = con.prepareStatement(sql);
            statement.execute(sql);
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}