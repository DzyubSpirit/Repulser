package com.company.phobjects;

import com.company.phobjects.PhObject;
import com.company.phobjects.PhObjectFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dzyub_000 on 07.08.2015.
 */

public class PhModel {

    private Random r = new Random();

    private double left = 0;
    private double right = 1;
    private double top = 1;
    private double bottom = 0;

    private int ballNum;
    private int wallNum;

    private ArrayList<PhObject> objs;
    private ArrayList<Ball> balls;
    private ArrayList<Wall> walls;
    private ArrayList<Rect> rects;

    private PhObjectFactory factory = new PhObjectFactory(new Ball(this), new Wall(this), new Rect(this));

    public PhModel() {
        this(0, 0);
    }

    public PhModel(int ballNum, int wallNum) {
        this.ballNum = ballNum;
        this.wallNum = wallNum;
        createObjects();
    }

    public int setFactory(PhObjectFactory fact) {
        this.factory = fact;
        return 0;
    }

    private void createObjects() {
        objs = new ArrayList<>();
        balls = createBalls(ballNum);
        for (Ball ball : balls) {
            objs.add(ball);
        }
        walls = createWalls(wallNum);
        for (Wall wall : walls) {
            objs.add(wall);
        }
        rects = new ArrayList<Rect>();
    }

    public int createBall(Point.Double coords, Point.Double vel) {
        Ball ball = factory.createBall();
        ball.coords = coords;
        ball.vel = vel;
        balls.add(ball);
        objs.add(ball);
        return 0;
    }

    public int createRect() {
        Rect rect = factory.createRect();
        rects.add(rect);
        objs.add(rect);
        return 0;
    }

    public int createRect(Point.Double coords, Point.Double size, double angle) {
        Rect rect = factory.createRect(coords, size, angle);
        rects.add(rect);
        objs.add(rect);
        return 0;
    }

    public int createWall(Point.Double start, Point.Double end) {
        Wall wall = factory.createWall(start, end);
        walls.add(wall);
        objs.add(wall);
        return 0;
    }

    private ArrayList<Ball> createBalls(int ballNum) {
        ArrayList<Ball> res = new ArrayList<>();
        for (int i = 0; i < ballNum; i++) {
            res.add(factory.createBall(this));
        }
        return res;
    }

    private ArrayList<Wall> createWalls(int wallNum) {
        ArrayList<Wall> res = new ArrayList<>();
        for (int i = 0; i < wallNum; i++) {
            res.add(factory.createWall(this));
        }
        return res;
    }

    public ArrayList<PhObject> getObjs() {
        return objs;
    }

    public Rectangle.Double getBounds() {
        return new Rectangle.Double(left, bottom, right-left, top-bottom);
    }

    public int tick(long dt) {
//        Counting impulce of all balls
//        double sum = 0;
//        double count = 0;
//        for (Ball ball : balls) {
//            count++;
//            sum += ball.vel.x*ball.vel.x + ball.vel.y*ball.vel.y;
//        }

//        System.out.println(sum / count);


//        Iteracting of objects
        for (PhObject obj : objs) {

            for (Ball ball : balls) {
                obj.iteractWithBall(ball, dt);
            }

            for (Wall wall : walls) {
                obj.iteractWithWall(wall, dt);
            }
        }


//        Calculating new coords
        for (PhObject obj : objs) {
            obj.tick(dt);
        }


        return 0;
    }

    public int setSize(double left, double right, double bottom, double top) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        createObjects();

        return 0;
    }


}

