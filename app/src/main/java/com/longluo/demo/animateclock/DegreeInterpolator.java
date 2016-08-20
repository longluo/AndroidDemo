package com.longluo.demo.animateclock;

import android.animation.TimeInterpolator;


/**
 * Created by luolong on 2016/8/12.
 */
public class DegreeInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float time) {
        float timeLeft = 1.0f - time;
        return timeLeft * timeLeft * timeLeft * 0.0f
                + 3 * timeLeft * timeLeft * time * 1.0f
                + 3 * timeLeft * time * time * 1.0f
                + time * time * time * 1.0f;

    }
}


