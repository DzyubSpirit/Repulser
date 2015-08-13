package com.company.phobjects;

import java.awt.*;

/**
 * Created by Dzyub_000 on 09.08.2015.
 */
public interface PhObject {
    int tick(long dt);
    int draw(Graphics g);
    int iteractWithBall(Ball ball);
    int iteractWithWall(Wall wall);

}
