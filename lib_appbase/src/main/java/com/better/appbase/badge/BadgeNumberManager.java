package com.better.appbase.badge;

import android.content.Context;
import android.os.Build;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BadgeNumberManager.java
 * Author: Better
 * Create: 2018/2/1 9:49
 * <p>
 * Changes (from 2018/2/1)
 * -----------------------------------------------------------------
 * 2018/2/1 : Create BadgeNumberManager.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class BadgeNumberManager {
    private Context mContext;

    private BadgeNumberManager(Context context) {
        mContext = context;
    }

    public static BadgeNumberManager from(Context context) {
        return new BadgeNumberManager(context);
    }

    private static final Impl IMPL;


    /**
     * 设置应用在桌面上显示的角标数字
     *
     * @param number 显示的数字
     */
    public void setBadgeNumber(int number) {
        IMPL.setBadgeNumber(mContext, number);
    }

    interface Impl {

        void setBadgeNumber(Context context, int number);

    }

    static class ImplHuaWei implements Impl {

        @Override
        public void setBadgeNumber(Context context, int number) {
            BadgeNumberManagerHuaWei.setBadgeNumber(context, number);
        }
    }

    static class ImplXiaoMi implements Impl {

        @Override
        public void setBadgeNumber(Context context, int number) {
            //小米机型的桌面应用角标API跟通知绑定在一起了，所以单独做处理
            BadgeNumberManagerXiaoMi.setBadgeNumber(context, number);
        }
    }

    static class ImplVIVO implements Impl {

        @Override
        public void setBadgeNumber(Context context, int number) {
            BadgeNumberManagerVIVO.setBadgeNumber(context, number);
        }
    }

    static class ImplOPPO implements Impl {

        @Override
        public void setBadgeNumber(Context context, int number) {
            BadgeNumberManagerOPPO.setBadgeNumber(context, number);
        }
    }

    static class ImplSamsung implements Impl {

        @Override
        public void setBadgeNumber(Context context, int number) {
            BadgeNumberManagerSamsung.setBadgeNumber(context, number);
        }
    }


    static class ImplBase implements Impl {

        @Override
        public void setBadgeNumber(Context context, int number) {
            //do nothing
            BadgeNumberManagerDefault.setBadgeNumber(context, number);
        }
    }

    static {
        String manufacturer = Build.MANUFACTURER;
        if (manufacturer.equalsIgnoreCase(MobileBrand.HUAWEI)) {
            IMPL = new ImplHuaWei();
        } else if (manufacturer.equalsIgnoreCase(MobileBrand.XIAOMI)) {
            IMPL = new ImplXiaoMi();
        } else if (manufacturer.equalsIgnoreCase(MobileBrand.VIVO)) {
            IMPL = new ImplVIVO();
        } else if (manufacturer.equalsIgnoreCase(MobileBrand.OPPO)) {
            IMPL = new ImplOPPO();
        } else if (manufacturer.equalsIgnoreCase(MobileBrand.SAMSUNG)){
            IMPL = new ImplSamsung();
        } else {
            IMPL = new ImplBase();
        }
    }
}
