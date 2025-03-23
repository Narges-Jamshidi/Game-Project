package org.example.gameproject.model.towers;

import java.util.ArrayList;

public class ArcherTower extends Tower {

    private ArrayList<String> towerImages = new ArrayList<>();
    private String mainImage;
    private String bullet;


    public ArcherTower() {
        super(5, 20, 200, 1, "file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\towers\\49.png");
        this.mainImage = "file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\towers\\14.png";
    }

    public ArrayList<String> getTowerImages() {
        return towerImages;
    }

    public String getBullet() {
        return bullet;
    }

    public String getMainImage() {
        return mainImage;
    }

}
