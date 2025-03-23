package org.example.gameproject.model.towers;

import java.util.ArrayList;

public class StoneTower extends Tower {

    private ArrayList<String> towerImages = new ArrayList<>();
    private String mainImage;
    private String bullet;

    public StoneTower() {
        super(50, 30, 100, 1,"file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\towers\\49.png");
        this.mainImage = "file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\towers\\3.png";
    }

    public String getBullet() {
        return bullet;
    }

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
