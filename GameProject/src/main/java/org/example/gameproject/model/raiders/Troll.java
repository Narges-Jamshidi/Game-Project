package org.example.gameproject.model.raiders;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Troll extends Raider {

    private boolean shield;

    public Troll() {
        super(200, 160, 20);
        super.setWalkingImage("file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\trolls\\output-onlinegiftools.gif");
        shield = true;

    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }
}
