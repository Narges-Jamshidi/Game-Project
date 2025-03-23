package org.example.gameproject.controler.playerOperation;

import org.example.gameproject.controler.Regex;
import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;

import java.sql.DatabaseMetaData;

public class CreateAccount {

    private static CreateAccount createAccount;

    public static CreateAccount getCreateAccount() {
        if (createAccount == null) {
            createAccount = new CreateAccount();
        }
        return createAccount;
    }

    public void signup(String username, String password, String email) {
        PlayerDatabaseManage.getPlayerDatabaseManage().insertPlayer(username, password);
    }

    public Player login(String password ,String username) {
        return PlayerDatabaseManage.getPlayerDatabaseManage().findPlayer(PlayerDatabaseManage.getPlayerDatabaseManage().findIdByPassword(password , username));
    }

    public void setNewPassword(String password, String username) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updatePassword(password, PlayerDatabaseManage.getPlayerDatabaseManage().findIdByUsername(username));
    }

}