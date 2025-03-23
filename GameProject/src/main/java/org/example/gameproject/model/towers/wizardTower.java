package org.example.gameproject.model.towers;

import java.util.ArrayList;

public class wizardTower extends Tower {

    public wizardTower() {
        super(15, 50, 250, 1,"file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\towers\\49.png");
        this.mainImage = "file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\towers\\16.png";

    }

    private ArrayList<String> towerImages = new ArrayList<>();
    private String mainImage;


    public ArrayList<String> getTowerImages() {
        return towerImages;
    }

    public void setTowerImages(ArrayList<String> towerImages) {
        this.towerImages = towerImages;
    }

    public String getMainImage() {
        return mainImage;
    }

}
