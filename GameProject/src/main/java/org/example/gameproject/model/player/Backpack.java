package org.example.gameproject.model.player;

public class Backpack {
    int heal;
    int littleBoy;
    int coin;
    int freeze;

    public Backpack(int heal, int littleBoy, int coin, int freeze) {
        this.heal = heal;
        this.littleBoy = littleBoy;
        this.coin = coin;
        this.freeze = freeze;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getLittleBoy() {
        return littleBoy;
    }

    public void setLittleBoy(int littleBoy) {
        this.littleBoy = littleBoy;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }
}
