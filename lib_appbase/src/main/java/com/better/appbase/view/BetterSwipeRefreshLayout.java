package com.better.appbase.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.socks.library.KLog;

public class BetterSwipeRefreshLayout extends SwipeRefreshLayout {
    public BetterSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public BetterSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float lastx = 0;
    float lasty = 0;
    boolean ismovepic = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            lastx = ev.getX();
            lasty = ev.getY();
            ismovepic = false;
            return super.onInterceptTouchEvent(ev);
        }

        final int action = MotionEventCompat.getActionMasked(ev);
        KLog.v(ev.getX() + "---" + ev.getY());

        int x2 = (int) Math.abs(ev.getX() - lastx);
        int y2 = (int) Math.abs(ev.getY() - lasty);

        //滑动图片最小距离检查
        KLog.v("滑动差距 - >" + x2 + "--" + y2);
        if (x2>y2){
            if (x2>=100)ismovepic = true;
            return false;
        }

        //是否移动图片(下拉刷新不处理)
        if (ismovepic){
            KLog.v("滑动差距 - >" + x2 + "--" + y2);
            return false;
        }

        boolean isok = super.onInterceptTouchEvent(ev);

        KLog.v("isok ->" + isok);

        return isok;
    }

}
