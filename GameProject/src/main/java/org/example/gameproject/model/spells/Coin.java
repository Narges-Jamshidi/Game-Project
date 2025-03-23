package org.example.gameproject.model.spells;

import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;

public class Coin implements Spell {
    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    public void drop(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney() + 200, player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells((player.getBackpack().getFreeze())
                , player.getBackpack().getCoin() - 1
                , player.getBackpack().getLittleBoy()
                , player.getBackpack().getHeal(), player.getId());
        player.setMoney(player.getMoney() + 200);
        player.getBackpack().setCoin(player.getBackpack().getCoin() - 1);

    }
}
