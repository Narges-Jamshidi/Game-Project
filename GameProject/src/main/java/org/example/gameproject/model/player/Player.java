package org.example.gameproject.model.player;

public class Player {
    private int id;
    private String username;
    private String password;
    private int level;
    private int jewel;
    private int health;
    private Backpack backpack;
    private int money;

    public Player(int id, String username, String password, int level, int jewel, int health, Backpack backpack, int money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.jewel = jewel;
        this.health = health;
        this.backpack = backpack;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getJewel() {
        return jewel;
    }

    public void setJewel(int jewel) {
        this.jewel = jewel;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
