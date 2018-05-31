package com.better.appbase.view.empty;

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

@IntDef({EmptyType.LODING, EmptyType.NODATA, EmptyType.NET_ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface EmptyType {

    //加载
    int LODING = 0;

    //无数据
    int NODATA = 1;

    //网络错误
    int NET_ERROR = 2;
}
