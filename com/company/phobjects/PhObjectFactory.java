package com.company.phobjects;

import java.awt.*;

/**
 * Created by Dzyub_000 on 09.08.2015.
 */
public class PhObjectFactory {
    public static Ball createBall(PhModel model) {
        return new Ball(model);
    }

    public static Ball createBall(PhModel model, Point.Double coords, Point.Double vel) {
        return new Ball(model, coords, vel);
    }

    public static Wall createWall(PhModel model) {
        return new Wall(model);
    }

    Ball ball;
    Wall wall;
    Rect rect;

    public PhObjectFactory(Ball ball, Wall wall, Rect rect) {
        this.ball = ball;
        this.wall = wall;
        this.rect = rect;
    }

    public Ball createBall() {
        return ball.clone();
    }

    public Rect createRect() {
        return rect.clone();
    }

    public Rect createRect(Point.Double coords, Point.Double size, double angle) {
        Rect rect = this.rect.clone();
        rect.coords = coords;
        rect.size = size;
        rect.angle = angle;
        return rect;
    }

    public Wall createWall() {
        return wall.clone();
    }

    public Wall createWall(Point.Double start, Point.Double end) {
        Wall wall = this.wall.clone();
        wall.setStart(start);
        wall.setEnd(end);
        return wall;
    }

}
