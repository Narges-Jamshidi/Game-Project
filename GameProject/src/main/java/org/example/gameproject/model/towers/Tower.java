package org.example.gameproject.model.towers;

abstract public class Tower {
    int power;
    int cost;
    int radius;
    int level;
    private String bullet;


    public Tower(int power, int cost, int radius, int level , String bullet) {
        this.power = power;
        this.cost = cost;
        this.radius = radius;
        this.level = level;
        this.bullet=bullet;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getLevel() {
        return level;
    }

    public String getBullet() {
        return bullet;
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
