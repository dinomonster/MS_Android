package com.better.appbase.intef;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: ProgressListener.java
 * Author: Better
 * Create: 2018/3/26 20:27
 * <p>
 * Changes (from 2018/3/26)
 * -----------------------------------------------------------------
 * 2018/3/26 : Create ProgressListener.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public interface ProgressListener {
    void progress(long bytesRead, long contentLength, boolean done);
}
