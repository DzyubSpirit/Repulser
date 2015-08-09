package com.company.phobjects;

import com.company.PhModel;

/**
 * Created by Dzyub_000 on 09.08.2015.
 */
public class PhObjectFactory {
    public static Ball createBall(PhModel model) {
        return new Ball(model);
    }

    public static Wall createWall(PhModel model) {
        return new Wall(model);
    }
}
