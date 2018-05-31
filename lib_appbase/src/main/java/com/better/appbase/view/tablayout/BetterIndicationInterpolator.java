package com.better.appbase.view.tablayout;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BetterIndicationInterpolator.java
 * Author: Better
 * Create: 2018/3/12 13:50
 * <p>
 * Changes (from 2018/3/12)
 * -----------------------------------------------------------------
 * 2018/3/12 : Create BetterIndicationInterpolator.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BetterIndicationInterpolator {
    private static final float DEFAULT_INDICATOR_INTERPOLATION_FACTOR = 3.0f;

    private final Interpolator leftEdgeInterpolator;
    private final Interpolator rightEdgeInterpolator;

    public BetterIndicationInterpolator() {
        this(DEFAULT_INDICATOR_INTERPOLATION_FACTOR);
    }

    public BetterIndicationInterpolator(float factor) {
        leftEdgeInterpolator = new AccelerateInterpolator(factor);
        rightEdgeInterpolator = new DecelerateInterpolator(factor);
    }

    public float getLeftEdge(float offset) {
        return leftEdgeInterpolator.getInterpolation(offset);
    }

    public float getRightEdge(float offset) {
        return rightEdgeInterpolator.getInterpolation(offset);
    }

    public float getThickness(float offset) {
        return 1f; //Always the same thickness by default
    }
}
