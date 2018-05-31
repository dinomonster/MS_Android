package com.better.appbase.badge;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BadgeNumberManagerXiaoMi.java
 * Author: Better
 * Create: 2018/2/1 9:52
 * <p>
 * Changes (from 2018/2/1)
 * -----------------------------------------------------------------
 * 2018/2/1 : Create BadgeNumberManagerXiaoMi.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BadgeNumberManagerXiaoMi {
    public static void setBadgeNumber(Context context, int number) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Notification notification = builder.build();
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, number);

        } catch (Exception e) {
            Log.e("Xiaomi" + " Badge error", "set Badge failed");
        }
    }
}
