package com.better.appbase.intef;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: LoadListener.java
 * Author: Better
 * Create: 2018/3/26 20:28
 * <p>
 * Changes (from 2018/3/26)
 * -----------------------------------------------------------------
 * 2018/3/26 : Create LoadListener.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public abstract class LoadListener<T> extends LoadCompleteCallback<T> implements ProgressListener {

    public void progress(long bytesRead, long contentLength, boolean done) {
    }

    public void onLoadComplete(T t) {
    }

    public void onLoadFailed(Exception info) {
    }
}
