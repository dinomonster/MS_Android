package com.better.appbase.badge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BadgeNumberManagerOPPO.java
 * Author: Better
 * Create: 2018/2/1 9:51
 * <p>
 * Changes (from 2018/2/1)
 * -----------------------------------------------------------------
 * 2018/2/1 : Create BadgeNumberManagerOPPO.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BadgeNumberManagerDefault {
    public static void setBadgeNumber(Context context, int number) {
        try {
            // 获取你当前的应用launcher
            String launcherClassName = getLauncherClassName(context);
            if (launcherClassName == null) {
                return;
            }

            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", number);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Default" + " Badge error", "set Badge failed");
        }
    }

    private static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }
}
