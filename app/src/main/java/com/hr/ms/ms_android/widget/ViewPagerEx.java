package com.hr.ms.ms_android.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lianghuiyong on 2017/7/17.
 * mViewPager.setPagingEnabled(false);//禁止左右滑动
 * mViewPager.setPagingEnabled(true);//开启左右滑动
 */

public class ViewPagerEx extends ViewPager {
    //传入false,禁止滚动动画
    private boolean isPagingEnabled = false;
    //传入false,禁止切换动画
    private boolean isSmoothScrollEnabled = false;

    public ViewPagerEx(Context context) {
        super(context);
    }
    public ViewPagerEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    //禁止滚动
    public void setPagingEnabled(boolean canScroll) {
        this.isPagingEnabled = canScroll;
    }

    /**
     * 传入false,禁止切换动画
     */
    public void setSmoothScrollEnabled(boolean smoothScrollEnabled) {
        isSmoothScrollEnabled = smoothScrollEnabled;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, isSmoothScrollEnabled);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, isSmoothScrollEnabled);//传入false禁止切换动画
    }
}
