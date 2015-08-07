package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dzyub_000 on 07.08.2015.
 */

public class PhModel {


    private double left = 0;
    private double right = 1;
    private double top = 1;
    private double bottom = 0;

    private Random r = new Random();

    private ArrayList<Ball> balls;

    public PhModel() {
        this(10, 0);
    }

    public PhModel(int ballNum, int wallNum) {
        createBalls(ballNum);
    }

    private void createBalls(int ballNum) {
        balls = new ArrayList<Ball>();
        for (int i = 0; i < ballNum; i++) {
            balls.add(new Ball(this));
        }
    }


    public int tick(long dt) {
        for (Ball ball : balls) {
            ball.tick(dt);
        }

        return 0;
    }

    public int setSize(double left, double right, double bottom, double top) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        createBalls(balls.size());

        return 0;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public class Ball {
        Point.Double coords;
        Point.Double vel;
        Point.Double radius;

        public Ball(PhModel model) {
            radius = new Point.Double(10, 10);

            double x = (r.nextDouble() * (model.right - model.left - radius.x)) + model.left;
            double y = (r.nextDouble() * (model.top - model.bottom - radius.y)) + model.bottom;
            coords = new Point.Double(x, y);

            x = r.nextDouble()*2 - 1;
            y = r.nextDouble()*2 - 1;
            x *= 5;
            y *= 5;
            vel = new Point.Double(x, y);
        }

        public double getX() {
            return coords.x;
        }

        public double getY() {
            return coords.y;
        }

        public double getRadiusX() {
            return radius.x;
        }

        public double getRadiusY() {
            return radius.y;
        }

        public int tick(long dt) {
//            System.out.println(vel.x*dt/35.);
            double dx = vel.x*dt/35.;
            double dy = vel.y*dt/35.;

            coords.x += dx;
            coords.y += dy;

            if (coords.x < left || coords.x + radius.x > right) {
                coords.x -=dx;
                vel.x *= -1;
            }

            if (coords.y < bottom || coords.y + radius.y  > top) {
                coords.y -=dy;
                vel.y *= -1;
            }



            return 0;
        }
    }

    public class Wall {

    }
}

