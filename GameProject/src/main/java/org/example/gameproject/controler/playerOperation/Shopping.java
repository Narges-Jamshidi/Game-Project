package org.example.gameproject.controler.playerOperation;

import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;
import org.example.gameproject.model.spells.Coin;
import org.example.gameproject.model.spells.Freeze;
import org.example.gameproject.model.spells.Heal;
import org.example.gameproject.model.spells.LittleBoy;
import org.example.gameproject.model.towers.ArcherTower;
import org.example.gameproject.model.towers.StoneTower;
import org.example.gameproject.model.towers.Tower;
import org.example.gameproject.model.towers.wizardTower;

public class Shopping {
    private static Shopping shopping;

    private Shopping() {
    }

    public static Shopping getShopping() {
        if (shopping == null) {
            shopping = new Shopping();
        }
        return shopping;
    }

    public void buyFreeze(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateJewel(player.getJewel() - new Freeze().getPrice(), player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells((player.getBackpack().getFreeze() + 1)
                , player.getBackpack().getCoin()
                , player.getBackpack().getLittleBoy()
                , player.getBackpack().getHeal(), player.getId());
        player.setJewel(player.getJewel() - new Freeze().getPrice());
        player.getBackpack().setFreeze(player.getBackpack().getFreeze() + 1);
    }

    public void buyCoin(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateJewel(player.getJewel() - new Coin().getPrice(), player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells((player.getBackpack().getFreeze())
                , player.getBackpack().getCoin() + 1
                , player.getBackpack().getLittleBoy()
                , player.getBackpack().getHeal(), player.getId());
        player.setJewel(player.getJewel() - new Coin().getPrice());
        player.getBackpack().setCoin(player.getBackpack().getCoin() + 1);
    }

    public void buyHeal(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateJewel(player.getJewel() - new Heal().getPrice(), player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells((player.getBackpack().getFreeze())
                , player.getBackpack().getCoin()
                , player.getBackpack().getLittleBoy()
                , player.getBackpack().getHeal() + 1, player.getId());
        player.setJewel(player.getJewel() - new Heal().getPrice());
        player.getBackpack().setHeal(player.getBackpack().getHeal() + 1);

    }

    public void buyBoy(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateJewel(player.getJewel() - new LittleBoy().getPrice(), player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateSpells((player.getBackpack().getFreeze())
                , player.getBackpack().getCoin()
                , player.getBackpack().getLittleBoy() + 1
                , player.getBackpack().getHeal(), player.getId());
        player.getBackpack().setLittleBoy(player.getBackpack().getLittleBoy() + 1);
        player.setJewel(player.getJewel() - new LittleBoy().getPrice());
        player.getBackpack().setLittleBoy(player.getBackpack().getLittleBoy() + 1);
    }

    public void buyJewel(Player player) {
        PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney() - 20, player.getId());
        PlayerDatabaseManage.getPlayerDatabaseManage().updateJewel(player.getJewel() + 1, player.getId());
        player.setJewel(player.getJewel() + 1);
        player.setMoney(player.getMoney() - 20);
    }

    public Tower buyTower(String towerName, Player player) {
        Tower tower = null;
        if (towerName.equals("archer")) {
            tower = new ArcherTower();
        } else if (towerName.equals("stone")) {
            tower = new StoneTower();
        } else if (towerName.equals("wizard")) {
            tower = new wizardTower();
        }
        assert tower != null;
        PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney() - tower.getCost(), player.getId());
        player.setMoney(player.getMoney() - tower.getCost());
        return tower;
    }
}
