package com.company.phobjects;

import java.awt.*;
import java.util.Random;

/**
 * Created by Dzyub_000 on 09.08.2015.
 */
class Ball implements PhObject {
    Point.Double coords;
    Point.Double vel;
    double radius = 10;

    PhModel model;

    Random r = new Random();

    public Ball(PhModel model) {
        this.model = model;

        Rectangle.Double bounds = model.getBounds();

        double x = (r.nextDouble() * (bounds.getMaxX() - bounds.getMinX() - radius)) +
                bounds.getMinX();
        double y = (r.nextDouble() * (bounds.getMaxY() - bounds.getMinY() - radius)) +
                bounds.getMinY();

        coords = new Point.Double(x, y);

        x = r.nextDouble()*2 - 1;
        y = r.nextDouble()*2 - 1;
        x *= 5;
        y *= 5;
        vel = new Point.Double(x, y);
    }

    public Ball(PhModel model, Point.Double coords, Point.Double vel) {
        this(model, coords, vel, 10);
    }

    public Ball(PhModel model, Point.Double coords, Point.Double vel, double radius) {
        this.model = model;
        this.coords = coords;
        this.vel = vel;
        this.radius = radius;
    }

    public double getX() {
        return coords.x;
    }

    public double getY() {
        return coords.y;
    }

    public double getRadius() { return radius; }

    public int tick(long dt) {
//            System.out.println(vel.x*dt/35.);
        double dx = vel.x*dt/35.;
        double dy = vel.y*dt/35.;

        coords.x += dx;
        coords.y += dy;

        Rectangle.Double bounds = model.getBounds();

        if (coords.x - radius < bounds.getMinX() || coords.x + radius > bounds.getMaxX()) {
            coords.x -=dx;
            vel.x *= -1;
        }

        if (coords.y - radius < bounds.getMinY() || coords.y + radius  > bounds.getMaxY()) {
            coords.y -=dy;
            vel.y *= -1;
        }



        return 0;
    }

    public int draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval((int) Math.round(this.getX()-radius),
                (int) Math.round( this.getY()-radius),
                (int) (this.getRadius()+radius),
                (int) (this.getRadius()+radius));
        return 0;
    }

    public int iteractWithBall(Ball ball, long dt) {
        if (this == ball) {
            return 1;
        }
        double dx = ball.getX() - this.getX();
        double dy = ball.getY() - this.getY();
        double r = Math.sqrt(dx*dx + dy*dy);

        if (r == 0) {
            return 3;
        }

        double sumR = ball.radius + this.radius;
        double dr = sumR - r;
        if (dr < 0) {
            return 2;
        }
        double ndx = dx / r;
        double ndy = dy / r;

        dr += 1E-8;
        double pr = dr*ball.radius/sumR;
        this.coords.x -= ndx*pr;
        this.coords.y -= ndy*pr;

        pr = dr*this.radius/sumR;
        ball.coords.x += ndx*pr;
        ball.coords.y += ndy*pr;

//        right reflection
        double v1 = this.vel.x*ndx + this.vel.y*ndy;
        this.vel.x -= ndx*v1;
        this.vel.y -= ndy*v1;
        double v2 = ball.vel.x*ndx + ball.vel.y*ndy;
        ball.vel.x -= ndx*v2;
        ball.vel.y -= ndy*v2;

        double m1 = this.radius*this.radius;
        double m2 = ball.radius*ball.radius;
        double E = (m1*v1*v1 + m2*v2*v2)/2;
        double P = m1*v1 + m2*v2;
        double D = Math.sqrt(m1*m2*(2*E*(m1+m2) - P*P));
        double nv2, nv1;
        if (v1 < v2) {
            nv2 = (P*m2 - D)/m2/(m1+m2);
        } else {
            nv2 = (P*m2 + D)/m2/(m1+m2);
        }
        nv1 = (P-m2*nv2)/m1;

        this.vel.x += ndx*nv1;
        this.vel.y += ndy*nv1;
        ball.vel.x += ndx*nv2;
        ball.vel.y += ndy*nv2;




//        simple reflection
//        double o = this.vel.x * ndx + this.vel.y * ndy;
//        this.vel.x -= 2*ndx*o;
//        this.vel.y -= 2*ndy*o;
//
//        o = ball.vel.x * (-ndx) + ball.vel.y * (-ndy);
//        ball.vel.x += 2*ndx*o;
//        ball.vel.y += 2*ndy*o;

//        Don`t decomment
//        this.vel.x = 0;
//        this.vel.y = 0;
//        ball.vel.x = 0;
//        ball.vel.y = 0;

        return 0;
    }

    public int iteractWithRect(Rect rect, long dt) {

        return 0;
    }

    public int iteractWithWall(Wall wall, long dt) {
        Point.Double beg = wall.getStart();
        Point.Double end = wall.getEnd();

        double wdx = end.x - beg.x;
        double wdy = end.y - beg.y;
        double wr = Math.sqrt(wdx * wdx + wdy * wdy);
        double wndx = wdx / wr;
        double wndy = wdy / wr;

        double pr = (this.coords.x - beg.x)*wndx + (this.coords.y - beg.y)*wndy;
        if (pr < -this.radius || pr > wr + this.radius) {
            return 1;
        }
        Point.Double inter = new Point.Double(beg.x + wndx*pr, beg.y + wndy*pr);

        double dx = inter.x - this.coords.x;
        double dy = inter.y - this.coords.y;
        boolean side1 = dx*wndy - dy*wndx >= 0;

        double r = Math.sqrt(dx*dx + dy*dy);

        this.tick(dt);
        double pr2 = (this.coords.x - beg.x)*wndx + (this.coords.y - beg.y)*wndy;
        Point.Double inter2 = new Point.Double(beg.x + wndx*pr2, beg.y + wndy*pr2);

        double dx2 = inter2.x - this.coords.x;
        double dy2 = inter2.y - this.coords.y;
        boolean side2 = dx2*wndy - dy2*wndx >= 0;
        this.tick(-dt);

        boolean isOneSide = side1 == side2;

        double dr = this.radius - r;
        if (isOneSide) {
            if (dr < 0) {
                return 2;
            }
        }

        dr += 1E-8;
        dx /= r;
        dy /= r;
        this.coords.x -= dx*dr;
        this.coords.y -= dy*dr;

        double o = dx*this.vel.x + dy*this.vel.y;
        this.vel.x -= 2*dx*o;
        this.vel.y -= 2*dy*o;
//        this.vel.x = 0;
//        this.vel.y = 0;

        return 0;
    }

    public Ball clone() {
        Ball ball = new Ball(model, (Point.Double)coords.clone(), (Point.Double)vel.clone(), radius);
        return ball;
    }
}
