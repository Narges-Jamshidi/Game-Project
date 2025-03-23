package org.example.gameproject.model.raiders;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class Raider {

    private int health;
    private double speed;
    private int loot;
    /*private ArrayList<String> animationImage=new ArrayList<>();*/
   /* private ArrayList<Point2D> pointS=new ArrayList<>();*/
    private String walkingImage;

    public Raider(int health, double speed, int loot) {
        this.health = health;
        this.speed = speed;
        this.loot = loot;

    }


    public String getWalkingImage() {
        return walkingImage;
    }

    public void setWalkingImage(String mainImage) {
        this.walkingImage = mainImage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getLoot() {
        return loot;
    }

    public void setLoot(int loot) {
        this.loot = loot;
    }

 /*   public ArrayList<Point2D> getPointS() {
        return pointS;
    }

    public void setPointS(ArrayList<Point2D> pointS) {
        this.pointS = pointS;
    }*/

}
