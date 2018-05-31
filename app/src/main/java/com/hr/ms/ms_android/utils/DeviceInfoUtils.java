package com.hr.ms.ms_android.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.better.appbase.utils.SPUtils;
import com.hr.ms.ms_android.AppConfig;
import com.socks.library.KLog;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/21.
 */

public class DeviceInfoUtils {
    static String strImei;
    private static final DeviceInfoUtils ourInstance = new DeviceInfoUtils();

    public static DeviceInfoUtils getInstance() {
        return ourInstance;
    }

    //android 7.0以上获取权限需要动态申请，与不能实时进行网络请求，关闭获取硬件的imei
    public String getImei() {

        //尝试获取保存的随机IMEI
        strImei = SPUtils.getString("STR_IMEI");

        //生成IMEI
        if (strImei == null) {
            strImei = genRandomImei(13);

            //保存
            SPUtils.putString("STR_IMEI", strImei);
        }
        return strImei;
    }

    /**
     * 生成随机Imei
     */
    private String genRandomImei(int lenth) {
        final int maxNum = 10;
        int count = 0; // 生成随机数的坐标

        StringBuffer randomStr = new StringBuffer("");
        Random random = new Random();
        while (count < lenth) {
            // 生成随机数，取绝对值，防止生成负数，
            int i = Math.abs(random.nextInt(maxNum));
            if (i >= 0 && i < 10) {
                randomStr.append(i + "");
                count++;
            }
        }
        return randomStr.toString();
    }

    public String getVersionName() {
        String versionName;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = AppConfig.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(AppConfig.getInstance().getPackageName(), 0);
            versionName = packInfo.versionName;

            if (versionName.length() > 5) {
                versionName = versionName.substring(0, 5);
            }
        } catch (Exception e) {
            versionName = "3.0.0";
            return versionName;
        }

        return versionName;
    }


    /**
     * 返回当前程序版本名
     */
    public int getAppVersionCode() {
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = AppConfig.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(AppConfig.getInstance().getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            KLog.e(e.getMessage(), e);
        }
        return versioncode;
    }
}
