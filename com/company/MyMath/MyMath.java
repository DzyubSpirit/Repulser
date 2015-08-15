package com.company.MyMath;

import com.company.MyMath.Line;

import java.awt.*;

/**
 * Created by Dzyub_000 on 15.08.2015.
 */
public class MyMath {
    public static Point.Double[] getIntersections(Line[] lines, Line base) {
        Point.Double[] res = new  Point.Double[lines.length];
        Point.Double[] allPoints = new Point.Double[lines.length*2];
        for (int i = 0; i < lines.length; i++) {
            allPoints[i*2] = lines[i].getStart();
            allPoints[i*2+1] = lines[i].getEnd();
        }
        Point.Double[] new_points = getCoordsInSystem(
                allPoints,
                base.getStart(),
                base.getEnd()
        );
        for (int i = 0; i < lines.length; i++) {

            Point.Double nst = new_points[i*2];
            Point.Double ned = new_points[i*2+1];
            double a = nst.x + (ned.x - nst.x) * nst.y / (nst.y - ned.y);
            if (a < 0 || a > 1) {
                res[i] = null;
                continue;
            }
            res[i] = new Point.Double(base.getStart().x + (base.getEnd().x - base.getStart().x)*a,
                    base.getStart().y + (base.getEnd().y - base.getStart().y)*a);
        }
        return res;
    }

    public static Point.Double[] getCoordsInSystem(Point.Double[] points, Point.Double start, Point.Double end) {
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double r = Math.sqrt(dx*dx + dy*dy);
        double ndx = dx / r;
        double ndy = dy / r;

        Point.Double[] new_points = new Point.Double[points.length];
        for (int i = 0; i < points.length; i++) {
            double new_x = (ndx * (points[i].x - start.x) + ndy * (points[i].y - start.y)) / r;
            double new_y = (ndy * (points[i].x - start.x) + (-ndx) * (points[i].y - start.y)) / r;
            new_points[i] = new Point.Double(new_x, new_y);
        }
        return new_points;
    }
}
