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

    public PhModel() {
        this(10, 0);
    }

    public PhModel(int ballNum, int wallNum) {
        this.ballNum = ballNum;
        this.wallNum = wallNum;
        createObjects();
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
    }

    private ArrayList<Ball> createBalls(int ballNum) {
        ArrayList<Ball> res = new ArrayList<>();
        for (int i = 0; i < ballNum; i++) {
            res.add(PhObjectFactory.createBall(this));
        }
        return res;
    }

    private ArrayList<Wall> createWalls(int wallNum) {
        ArrayList<Wall> res = new ArrayList<>();
        for (int i = 0; i < wallNum; i++) {
            res.add(PhObjectFactory.createWall(this));
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
        for (PhObject obj : objs) {
            obj.tick(dt);
        }

        double sum = 0;
        double count = 0;
        for (Ball ball : balls) {
            count++;
            sum += ball.vel.x*ball.vel.x + ball.vel.y*ball.vel.y;
        }

//        System.out.println(sum / count);

        for (PhObject obj : objs) {

            for (Ball ball : balls) {
                obj.iteractWithBall(ball);
            }

            for (Wall wall : walls) {
                obj.iteractWithWall(wall);
            }
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

