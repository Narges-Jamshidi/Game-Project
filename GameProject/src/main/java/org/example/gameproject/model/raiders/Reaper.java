package org.example.gameproject.model.raiders;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Reaper extends Raider {
    private boolean running = false;
    private ArrayList<String> runningReaper = new ArrayList<>();

    public Reaper() {
        super(200, 100, 8);
        super.setWalkingImage("file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\reaper\\Walking\\output-onlinegiftoolsReaper.gif");
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<String> getRunningReaper() {
        return runningReaper;
    }
}
