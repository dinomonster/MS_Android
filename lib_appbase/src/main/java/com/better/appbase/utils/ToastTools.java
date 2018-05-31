package com.better.appbase.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import com.socks.library.KLog;

import es.dmoral.toasty.Toasty;

/**
 * Toast工具类
 */
public class ToastTools {

    //单例实现
    private Application application;

    @SuppressLint("StaticFieldLeak")
    private static ToastTools instances;

    private ToastTools(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        instances = new ToastTools(application);
    }

    public static void showToast(String message) {
        if (instances == null) {
            KLog.e("ToastUtils instances is null, you need call init() method.");
            return;
        }
        Toasty.normal(instances.application, message).show();
    }

    public static void showToast(int message) {
        if (instances == null) {
            KLog.e("ToastUtils instances is null, you need call init() method.");
            return;
        }
        Toasty.normal(instances.application, "" + message).show();
    }

    public static void showErrorToast(String string) {

        if (instances == null) {
            KLog.e("ToastUtils instances is null, you need call init() method.");
            return;
        }

        Toasty.error(instances.application, string, Toast.LENGTH_SHORT, true).show();
    }

    public static void showSuccessToast(String string) {

        if (instances == null) {
            KLog.e("ToastUtils instances is null, you need call init() method.");
            return;
        }

        Toasty.success(instances.application, string, Toast.LENGTH_SHORT, true).show();
    }

}
