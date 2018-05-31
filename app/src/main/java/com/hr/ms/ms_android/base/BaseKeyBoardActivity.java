package com.hr.ms.ms_android.base;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.better.appbase.utils.KeyboardUtils;
import com.sxzx.swideback.SwipeBackActivityBase;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BaseKeyBoardActivity.java
 * Author: Better
 * Create: 2018/3/16 19:28
 * <p>
 * Changes (from 2018/3/16)
 * -----------------------------------------------------------------
 * 2018/3/16 : Create BaseKeyBoardActivity.java (梁惠涌);
 * -----------------------------------------------------------------
 */

// 点击空白区域 自动隐藏软键盘
public abstract class BaseKeyBoardActivity extends AppCompatActivity implements SwipeBackActivityBase {

    //启用[自动隐藏软键盘]功能控制
    private boolean enableBlankAreaHide = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (enableBlankAreaHide){
            if (null != this.getCurrentFocus()) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (enableBlankAreaHide){
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    KeyboardUtils.hideSoftInput(this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setEnableBlankAreaHide(boolean enableBlankAreaHide) {
        this.enableBlankAreaHide = enableBlankAreaHide;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }
}
