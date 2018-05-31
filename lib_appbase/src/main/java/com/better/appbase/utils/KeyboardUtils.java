package com.better.appbase.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 键盘相关工具类
 * </pre>
 */
public class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 避免输入法面板遮挡
     * <p>在manifest.xml中activity中设置</p>
     * <p>android:windowSoftInputMode="stateVisible|adjustResize"</p>
     */

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 动态隐藏软键盘
     *
     * @param context 上下文
     * @param edit    输入框
     */
    public static void hideSoftInput(Context context, EditText edit) {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    /**
     * 动态显示软键盘
     *
     * @param context 上下文
     * @param edit    输入框
     */
    public static void showSoftInput(Context context, EditText edit) {
        if (edit != null) {
            edit.requestFocus();
            edit.setSelection(edit.getText().length());
            showSoftInput(context, edit, 0);
        }
    }

    /**
     * 动态显示软键盘
     *
     * @param context 上下文
     * @param edit    输入框
     */
    public static void showSoftInput(final Context context, final EditText edit, long delayTime) {
        if (edit != null) {
            edit.requestFocus();
            edit.setSelection(edit.getText().length());

            if (delayTime != 0) {
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(edit, 0);
                        }
                    }
                }, delayTime);
            } else {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edit, 0);
            }
        }
    }

    /**
     * 判断键盘是否显示
     *
     * @param context 上下文
     * @return {@code true}: 显示<br>{@code false}: 不显示
     */
    public static boolean isShowSoftInput(Context context) {
        return ((InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE)).isActive();
    }

    //判断键盘是否显示
    public static boolean isShowSoftInput(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom != 0;
    }

    /**
     * 获取键盘的高度
     */
    public static int getSoftKeyboardHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //屏幕当前可见高度，不包括状态栏
        int displayHeight = rect.bottom - rect.top;
        //屏幕可用高度
        int availableHeight = getAvailableScreenHeight(activity);
        //用于计算键盘高度
        int softInputHeight = availableHeight - displayHeight - ScreenUtils.getStatusBarHeight(activity);
        return softInputHeight;
    }

    /**
     * 返回屏幕可用高度
     * 当显示了虚拟按键时，会自动减去虚拟按键高度
     */
    private static int getAvailableScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 返回屏幕可用高度
     * 当显示了虚拟按键时，会自动减去虚拟按键高度
     */
    public static void addOnKeyboardListener(final View view, final OnKeyboardListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int heightDifference = view.getRootView().getHeight() - r.bottom;
                //LogUtils.debug("heightDifference-->" + heightDifference);
                if (heightDifference > 200) {
                    //LogUtils.debug("键盘显示");

                    listener.onShow();
                }else {
                    //LogUtils.debug("键盘隐藏");
                    listener.onHide();
                }
            }
        });
    }

    public interface OnKeyboardListener {
        void onShow();

        void onHide();
    }
}