package org.example.gameproject.model.spells;

import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;

public class LittleBoy implements Spell {
    @Override
    public int getPrice() {
        return 5;
    }

    @Override
    public void drop(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells(
                (player.getBackpack().getFreeze())
                , player.getBackpack().getCoin()
                , player.getBackpack().getLittleBoy() - 1
                , player.getBackpack().getHeal(), player.getId());
        player.getBackpack().setLittleBoy(player.getBackpack().getLittleBoy() - 1);
    }
}
