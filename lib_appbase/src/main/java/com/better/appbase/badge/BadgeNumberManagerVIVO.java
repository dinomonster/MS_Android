package com.better.appbase.badge;

import android.content.Context;
import android.content.Intent;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BadgeNumberManagerVIVO.java
 * Author: Better
 * Create: 2018/2/1 9:52
 * <p>
 * Changes (from 2018/2/1)
 * -----------------------------------------------------------------
 * 2018/2/1 : Create BadgeNumberManagerVIVO.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BadgeNumberManagerVIVO {
    public static void setBadgeNumber(Context context, int number) {
        try {
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            intent.putExtra("className", launchClassName);
            intent.putExtra("notificationNum", number);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
