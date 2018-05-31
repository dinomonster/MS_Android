package com.better.appbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.better.appbase.R;
import com.better.appbase.base.BaseCustomView;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: DottedVerticalView.java
 * Author: Better
 * Create: 2018/1/22 15:47
 * <p>
 * Changes (from 2018/1/22)
 * -----------------------------------------------------------------
 * 2018/1/22 : Create DottedVerticalView.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class DottedVerticalView extends BaseCustomView {

    private Paint paint;

    public DottedVerticalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DottedVerticalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DottedVerticalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initCustomView(Context context, AttributeSet attrs) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseCustomView);
        int color = typedArray.getColor(R.styleable.BaseCustomView_color, Color.parseColor("#ececec"));

        float width =  1;
        float dashGap = getResources().getDimensionPixelSize(R.dimen.sx_dp3);
        float dashWidth = getResources().getDimensionPixelSize(R.dimen.sx_dp3);

        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashGap}, 0));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(mViewWidth / 2, 0, mViewWidth / 2, mViewHeight, paint);
    }
}
