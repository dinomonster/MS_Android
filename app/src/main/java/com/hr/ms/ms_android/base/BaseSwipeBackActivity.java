package com.hr.ms.ms_android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxzx.swideback.SwipeBackActivityBase;
import com.sxzx.swideback.SwipeBackActivityHelper;
import com.sxzx.swideback.SwipeBackLayout;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: BaseSwipeBackActivity.java
 * Author: Better
 * Create: 2018/3/16 20:02
 * <p>
 * Changes (from 2018/3/16)
 * -----------------------------------------------------------------
 * 2018/3/16 : Create BaseSwipeBackActivity.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public abstract class BaseSwipeBackActivity extends BaseKeyBoardActivity implements SwipeBackActivityBase {

    SwipeBackActivityHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSwipeBackHelper = new SwipeBackActivityHelper(this);
        mSwipeBackHelper.onActivityCreate();

        setSwipeBackEnable(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mSwipeBackHelper.findViewById(id);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackHelper.getSwipeBackLayout();
    }

    /**
     * 是否手势滑动
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
