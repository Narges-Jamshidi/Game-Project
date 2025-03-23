package org.example.gameproject.model.spells;

import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;

public class Freeze implements Spell {
    @Override
    public int getPrice() {
        return 1;
    }

    @Override
    public void drop(Player player) {

        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells(
                (player.getBackpack().getFreeze() - 1)
                , player.getBackpack().getCoin()
                , player.getBackpack().getLittleBoy()
                , player.getBackpack().getHeal(), player.getId());
        player.getBackpack().setFreeze(player.getBackpack().getFreeze() - 1);

    }
}
