package com.better.appbase.intef;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: LoadCompleteCallback.java
 * Author: Better
 * Create: 2018/3/26 20:31
 * <p>
 * Changes (from 2018/3/26)
 * -----------------------------------------------------------------
 * 2018/3/26 : Create LoadCompleteCallback.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public abstract class LoadCompleteCallback<T> {

    public abstract void onLoadComplete(T t);

    public abstract void onLoadFailed(Exception exception);
}
