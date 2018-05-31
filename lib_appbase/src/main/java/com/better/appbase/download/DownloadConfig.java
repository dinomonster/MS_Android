package com.better.appbase.download;

import android.os.Environment;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: DownloadConfig.java
 * Author: Better
 * Create: 2018/3/30 14:20
 * <p>
 * Changes (from 2018/3/30)
 * -----------------------------------------------------------------
 * 2018/3/30 : Create DownloadConfig.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class DownloadConfig {

    //项目下载目录
    public static String getPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "SeniorOnline";
    }
}
