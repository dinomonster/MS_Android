package com.better.appbase.badge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BadgeNumberManagerHuaWei.java
 * Author: Better
 * Create: 2018/2/1 9:50
 * <p>
 * Changes (from 2018/2/1)
 * -----------------------------------------------------------------
 * 2018/2/1 : Create BadgeNumberManagerHuaWei.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BadgeNumberManagerHuaWei {
    /**
     * 设置应用的桌面角标，已在一些华为手机上测试通过,但是无法保证在所有华为手机上都生效
     *
     * @param context context
     * @param number  角标显示的数字
     */
    public static void setBadgeNumber(Context context, int number) {
        try {
            if (number < 0) number = 0;
            Bundle bundle = new Bundle();
            bundle.putString("package", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            bundle.putString("class", launchClassName);
            bundle.putInt("badgenumber", number);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
