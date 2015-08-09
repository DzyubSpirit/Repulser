package com.company.phobjects;

import com.company.PhModel;

import java.awt.*;
import java.util.Random;

/**
 * Created by Dzyub_000 on 09.08.2015.
 */
class Ball implements PhObject {
    Point.Double coords;
    Point.Double vel;
    Point.Double radius;

    PhModel model;

    Random r = new Random();

    public Ball(PhModel model) {
        this.model = model;
        radius = new Point.Double(10, 10);

        Rectangle.Double bounds = model.getBounds();

        double x = (r.nextDouble() * (bounds.getMaxX() - bounds.getMinX() - radius.x)) +
                bounds.getMinX();
        double y = (r.nextDouble() * (bounds.getMaxY() - bounds.getMinY() - radius.y)) +
                bounds.getMinY();
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

        Rectangle.Double bounds = model.getBounds();

        if (coords.x < bounds.getMinX() || coords.x + radius.x > bounds.getMaxX()) {
            coords.x -=dx;
            vel.x *= -1;
        }

        if (coords.y < bounds.getMinY() || coords.y + radius.y  > bounds.getMaxY()) {
            coords.y -=dy;
            vel.y *= -1;
        }



        return 0;
    }

    public int draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval((int) Math.round(this.getX()),
                (int) Math.round( this.getY()),
                (int) this.getRadiusX(),
                (int) this.getRadiusY());
        return 0;
    }
}
