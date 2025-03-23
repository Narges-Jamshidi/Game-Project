package org.example.gameproject.model.spells;

import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;

public class Heal implements Spell {
    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    public void drop(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateHealth(player.getHealth() + 5, player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells(
                (player.getBackpack().getFreeze())
                , player.getBackpack().getCoin()
                , player.getBackpack().getLittleBoy()
                , player.getBackpack().getHeal() - 1
                , player.getId());
        player.setHealth(player.getHealth() + 5);
        player.getBackpack().setHeal(player.getBackpack().getHeal() - 1);
    }
}
