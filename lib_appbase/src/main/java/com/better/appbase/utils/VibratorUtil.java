package com.better.appbase.utils;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

import com.socks.library.KLog;

/**
 * 手机震动工具类
 * @author 梁惠涌
 *
 */
public class VibratorUtil {

    /**
     * 单次震动
     *
     * @param activity 调用该方法的Activity实例: 如，MainActivity.this
     */
    public static void Vibrate(final Activity activity) {
        try {
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(40);
        }catch (Exception e){
            KLog.e(e.getMessage());
        }
    }

    /**
     * 单次震动
     *
     * @param activity 调用该方法的Activity实例: 如，MainActivity.this
     * @param milliseconds 震动时长, 单位毫秒(ms).
     */
    public static void Vibrate(final Activity activity, long milliseconds) {
        try {
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(milliseconds);
        }catch (Exception e){
            KLog.e(e.getMessage());
        }
    }

    /**
     * 自定义震动
     *
     * @param activity 调用该方法的Activity实例
     * @param pattern  自定义震动模式:
     *                 数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长...];
     *                 时长的单位是毫秒.
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        try {
            Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(pattern, isRepeat ? 1 : -1);
        }catch (Exception e){
            KLog.e(e.getMessage());
        }
    }
}
