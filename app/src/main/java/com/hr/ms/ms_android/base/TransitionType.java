package com.hr.ms.ms_android.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: TransitionType.java
 * Author: Better
 * Create: 2018/1/29 12:28
 * <p>
 * Changes (from 2018/1/29)
 * -----------------------------------------------------------------
 * 2018/1/29 : Create TransitionType.java (梁惠涌);
 * -----------------------------------------------------------------
 */

@IntDef({TransitionType.DEFAULT, TransitionType.SPLASH, TransitionType.NO_ANIMATOR})
@Retention(RetentionPolicy.SOURCE)
public @interface TransitionType {

    //默认动画
    int DEFAULT = 0;

    //欢迎页动画
    int SPLASH = 1;

    //无动画效果
    int NO_ANIMATOR = 2;
}
