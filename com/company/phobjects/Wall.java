package com.company.phobjects;

import java.awt.*;
import java.util.Random;

/**
 * Created by Dzyub_000 on 09.08.2015.
 */
class Wall implements PhObject {
    private Point.Double coordsStart;
    private Point.Double coordsEnd;

    private Random r = new Random();

    public Wall(PhModel model) {
        Rectangle.Double bounds = model.getBounds();

        double x1 = r.nextDouble() * (bounds.getMaxX() - bounds.getMinX()) + bounds.getMinX();
        double y1 = r.nextDouble() * (bounds.getMaxY() - bounds.getMinY()) + bounds.getMinY();
        double x2 = r.nextDouble() * (bounds.getMaxX() - bounds.getMinX()) + bounds.getMinX();
        double y2 = r.nextDouble() * (bounds.getMaxY() - bounds.getMinY()) + bounds.getMinY();

        coordsStart = new Point.Double(x1, y1);
        coordsEnd = new Point.Double(x2, y2);
    }

    public Wall(double startX, double startY, double endX, double endY) {
        coordsStart = new Point.Double(startX, startY);
        coordsEnd = new Point.Double(endX, endY);
    }

    public Point.Double getStart() {
        return coordsStart;
    }

    public Point.Double getEnd() {
        return coordsEnd;
    }


    public int tick(long dt) {
        return 0;
    }

    public int draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point.Double start = getStart();
        Point.Double end = getEnd();
        g2d.drawLine(
                (int) start.x,
                (int) start.y,
                (int) end.x,
                (int) end.y);
        return 0;
    }

    public int iteractWithBall(Ball ball) {
        return ball.iteractWithWall(this);
    }

    public int iteractWithWall(Wall wall) {
        return 0;
    }
}
