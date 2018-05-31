package com.better.appbase.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import com.socks.library.KLog;

import static android.view.View.VISIBLE;

/**
 * Created by liang on 2018/3/15.
 *
 *      必须设置属性为 android:windowSoftInputMode="adjustResize"
 */

public class SoftHideKeyBoardUtil {

    public static void assistActivity(View observerView, View view) {
        new SoftHideKeyBoardUtil(observerView, view);
    }

    public static void assistActivity(View view) {
        new SoftHideKeyBoardUtil(view, view);
    }

    //键盘显示与隐藏时的高度
    private int viewHeightOnKeyShow;
    private int viewHeightOnKeyHide;

    //记录布局显示的宽高
    private int viewHeightOld;
    private int viewHeightNew;

    private boolean isFirst = true;

    private SoftHideKeyBoardUtil(View observerView, final View view) {
        observerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (view.getVisibility() == VISIBLE) {
                    possiblyResizeChildOfContent(view);
                }
            }
        });
    }

    private void possiblyResizeChildOfContent(View view) {

        viewHeightNew = (int) (computeUsableHeight(view) - view.getY());

        if (!isFirst && viewHeightNew != viewHeightOld) {

            if (KeyboardUtils.isShowSoftInput(view.getContext())) {

                // keyboard became visible
                viewHeightOnKeyShow = viewHeightOnKeyHide - KeyboardUtils.getSoftKeyboardHeight((Activity) view.getContext());
                changeHeight(view, viewHeightOnKeyHide, viewHeightOnKeyShow);

            } else {
                // keyboard became hide
                changeHeight(view, viewHeightOnKeyShow, viewHeightOnKeyHide);
            }

            viewHeightOld = viewHeightNew;
        }

        if (isFirst) {
            viewHeightOnKeyHide = view.getHeight();
            isFirst = false;
        }
    }

    private void changeHeight(final View view, final int statHeight, final int endHeight) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = statHeight + (int) (currentValue * (endHeight - statHeight));
                view.setLayoutParams(params);
            }
        });
        animator.setDuration(80).start();
    }

    private int computeUsableHeight(View view) {
        Rect r = new Rect();
        view.getWindowVisibleDisplayFrame(r);
        return r.bottom;
    }

}
