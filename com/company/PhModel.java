package com.company;

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

    public PhModel() {
        this(10, 0);
    }

    public PhModel(int ballNum, int wallNum) {
        this.ballNum = ballNum;
        this.wallNum = wallNum;
        objs = createBalls(ballNum);
        objs.addAll(createWalls(wallNum));
    }

    private ArrayList<PhObject> createBalls(int ballNum) {
        ArrayList<PhObject> res = new ArrayList<PhObject>();
        for (int i = 0; i < ballNum; i++) {
            res.add(PhObjectFactory.createBall(this));
        }
        return res;
    }

    private ArrayList<PhObject> createWalls(int wallNum) {
        ArrayList<PhObject> res = new ArrayList<PhObject>();
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

        return 0;
    }

    public int setSize(double left, double right, double bottom, double top) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        objs = createBalls(ballNum);
        objs.addAll(createWalls(wallNum));

        return 0;
    }

}

