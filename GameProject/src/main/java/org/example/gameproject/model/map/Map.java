package org.example.gameproject.model.map;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Map {
    private final int wave;
    private final ArrayList<Point2D> direction = new ArrayList<>();
    private final Point2D endPoint;
    private final int coin;
    private final ArrayList<Point2D> roadBend = new ArrayList<>();
    private final ArrayList<Point2D> roadBend2 = new ArrayList<>();
    private final ArrayList<Point2D> roadBend3 = new ArrayList<>();

    public Map(int wave, Point2D endPoint, int coin) {
        this.wave = wave;
        this.endPoint = endPoint;
        this.coin = coin;
    }

    public int getWave() {
        return wave;
    }

    public ArrayList<Point2D> getDirection() {
        return direction;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public int getCoin() {
        return coin;
    }

    public void setRoadBend(Point2D point) {
        roadBend.add(point);
    }

    public ArrayList<Point2D> getRoadBend() {
        return roadBend;
    }

    public ArrayList<Point2D> getRoadBend2() {
        return roadBend2;
    }

    public ArrayList<Point2D> getRoadBend3() {
        return roadBend3;
    }


    public void setRoadBend2(Point2D point) {
        roadBend2.add(point);
    }

    public void setRoadBend3(Point2D point) {
        roadBend2.add(point);
    }
}
