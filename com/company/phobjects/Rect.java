package com.company.phobjects;

import com.company.MyMath.Line;
import com.company.MyMath.MyMath;

import java.awt.*;

/**
 * Created by Dzyub_000 on 15.08.2015.
 */
public class Rect implements PhObject {
    public Point.Double coords = new Point.Double(0, 0);
    public Point.Double vel = new Point.Double(0, 0);
    public Point.Double size = new Point.Double(0, 0);
    public double angle = 0;
    public double wVel = 0;

    private PhModel model;

    public Rect(PhModel model) {
        this.model = model;
    }

    public Point.Double[] getPoints() {
        Point.Double[] points = new Point.Double[4];
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double vx1 = this.size.x * cosA;
        double vy1 = this.size.x * sinA;
        double vx2 = this.size.y * cosA;
        double vy2 = - this.size.y * sinA;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                points[i*2+j] = new Point.Double(
                        this.coords.x + i*vx1 + j*vx2,
                        this.coords.y + i*vy1 + j*vy2
                );
            }
        }
        Point.Double pr = points[2];
        points[2] = points[3];
        points[3] = pr;
        return points;
    }

    public int iteractWithRect(Rect rect, long dt) {
        if (this == rect) {
            return 1;
        }
        return 0;
    }

    public int iteractWithBall(Ball ball, long dt) {
        return 0;
    }

    public int iteractWithWall(Wall wall, long dt) {
        Point.Double[] points = getPoints();

        tick(dt);
        Point.Double[] newPoints = getPoints();
        tick(-dt);

        Line[] lines = new Line[points.length];
        for (int i = 0; i < points.length; i++) {
            lines[i] = new Line(points[i], newPoints[i]);
        }

        Point.Double[] intersections = MyMath.getIntersections(
                lines, new Line(wall.getStart(), wall.getEnd()));

        boolean isIntersection = false;
        for (int i = 0; i < points.length; i++) {
            if (intersections[i] != null) {
                isIntersection = true;
            }
        }

        if (!isIntersection) {
            return 1;
        }

        this.vel.x = 0;
        this.vel.y = 0;
        this.wVel = 0;

        return 0;
    }

    public int draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int[] pointX = new int[4];
        int[] pointY = new int[4];
        Point.Double[] points = getPoints();
        for (int i = 0; i < 4; i++) {
            pointX[i] = (int) points[i].x;
            pointY[i] = (int) points[i].y;
        }
        g2d.drawPolyline(pointX, pointY, 4);
        return 0;
    }

    public int tick(long dt) {
        coords.x += vel.x*dt;
        coords.y += vel.y*dt - 0.1*dt;
        angle += wVel;
        return 0;
    }

    public Rect clone() {
        Rect rect = new Rect(model);
        rect.wVel = wVel;
        rect.coords = (Point.Double) coords.clone();
        rect.vel = (Point.Double) vel.clone();
        rect.size = (Point.Double) size.clone();
        return rect;
    }
}
