package com.company.MyMath;

import java.awt.*;

/**
 * Created by Dzyub_000 on 15.08.2015.
 */
public class Line {
    private Point.Double start;
    private Point.Double end;

    public Line(Point.Double start, Point.Double end) {
        this.start = start;
        this.end = end;
    }

    public Point.Double getStart() {
        return start;
    }

    public Point.Double getEnd() {
        return end;
    }
}
