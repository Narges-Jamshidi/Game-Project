package org.example.gameproject.model.raiders;

import java.util.ArrayList;

public class Attacker extends Raider {
    private String kicking;

    public Attacker() {
        super(300, 20, 10);
        super.setWalkingImage("file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\attacker\\Walking\\output-onlinegiftoolsAttacker.gif");
        kicking = "file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\attacker\\Kicking\\output-onlinegiftoolsAttacker.gif";
    }

    public String getKicking() {
        return kicking;
    }
}
