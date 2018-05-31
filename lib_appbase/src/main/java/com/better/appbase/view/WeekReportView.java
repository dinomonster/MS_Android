package com.better.appbase.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.better.appbase.R;
import com.better.appbase.base.BaseCustomView;
import com.better.appbase.utils.LanguageUtil;
import com.socks.library.KLog;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: WeekReportView.java
 * Author: Better
 * Create: 2018/1/18 10:17
 * <p>
 * Changes (from 2018/1/18)
 * -----------------------------------------------------------------
 * 2018/1/18 : Create WeekReportView.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class WeekReportView extends BaseCustomView {

    /**/
    private Paint paint;
    private double percent1;
    private String percent1_str;
    private String percent2_str;
    private String percent1_val;
    private String percent2_val;
    double percent2;
    double percent3;

    public WeekReportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeekReportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHeight = mViewHeight - getPadBottom();
    }

    @Override
    public void initCustomView(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setData(double percent1, double percent2, double percent3, String percent1_str, String percent2_str, String percent1_val, String percent2_val) {
        this.percent1 = percent1;
        this.percent2 = percent2;
        this.percent3 = percent3;
        this.percent1_str = percent1_str;
        this.percent2_str = percent2_str;
        this.percent1_val = percent1_val;
        this.percent2_val = percent2_val;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        if (percent1 == 0) {

            paint.setColor(Color.parseColor("#CEBDA7"));
            path.moveTo(getPadLeft(), getPadTop());
            path.lineTo(getPadLeft() + mViewWidth * 0.5f, mViewHeight);
            path.lineTo(getPadLeft(), mViewHeight);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, paint);
            return;
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        float x_1 = getPadLeft() + (float) (mViewWidth * 0.5 * Math.sqrt(percent1));
        float y_1 = getPadTop() + (float) ((mViewHeight - getPadTop()) * Math.sqrt(percent1));

        float x_2 = getPadLeft() + (float) (mViewWidth * 0.5 * Math.sqrt(percent2));
        float y_2 = getPadTop() + (float) ((mViewHeight - getPadTop()) * Math.sqrt(percent2));


        paint.setColor(Color.parseColor("#F6F3EF"));
        path.moveTo(getPadLeft(), getPadTop());
        path.lineTo(getPadLeft() + mViewWidth * 0.5f, mViewHeight);
        path.lineTo(getPadLeft(), mViewHeight);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(Color.parseColor("#CEBDA7"));
        path.moveTo(getPadLeft(), getPadTop());
        path.lineTo(x_2, y_2);
        path.lineTo(getPadLeft(), y_2);
        path.close();
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(Color.parseColor("#a78760"));
        path.moveTo(getPadLeft(), getPadTop());
        path.lineTo(x_1, y_1);
        path.lineTo(getPadLeft(), y_1);
        path.close();
        canvas.drawPath(path, paint);

        /* *********************************************第10名*****************************************************************/
        paint.setShadowLayer(4, 2, 16, Color.parseColor("#16000000"));
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#c7a275"));
        paint.setPathEffect(null);
        canvas.drawLine(getLeftLevel(y_1), y_1 - getUpLevel(y_1), x_2 + mViewWidth / 10, y_1 - getUpLevel(y_1), paint);

        //x虚线
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#dcdcdc"));
        path.reset();
        path.moveTo(x_2 + mViewWidth / 10, y_1 - getUpLevel(y_1));
        path.lineTo(mViewWidth * 8 / 10, y_1 - getUpLevel(y_1));
        canvas.drawPath(path, paint);

        //圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#a78760"));
        canvas.drawCircle(x_2 + mViewWidth / 10, y_1 - getUpLevel(y_1), 10, paint);
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#dcdcdc"));
        canvas.drawCircle(mViewWidth * 8 / 10, y_1 - getUpLevel(y_1), 10, paint);

        //文字
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sx_sp12));
        paint.setColor(Color.parseColor("#999999"));
        canvas.drawText(percent1_str, mViewWidth * 8 / 10 + 20, y_1, paint);

        //文字
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sx_sp12));
        paint.setColor(Color.parseColor("#c7a275"));
        canvas.drawText(percent1_val, x_2 + mViewWidth / 10, y_1 - getUpLevel(y_1) - 20, paint);

        /* *********************************************第50名*****************************************************************/
        //第50名
        paint.setShadowLayer(4, 2, 16, Color.parseColor("#16000000"));
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
        paint.setColor(Color.parseColor("#c7a275"));
        canvas.drawLine(getLeftLevel(y_2), y_2 - getUpLevel(y_1), x_2 + mViewWidth / 10, y_2 - getUpLevel(y_1), paint);
        //x虚线
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#dcdcdc"));
        path.reset();
        path.moveTo(x_2 + mViewWidth / 10, y_2 - getUpLevel(y_1));
        path.lineTo(mViewWidth * 8 / 10, y_2 - getUpLevel(y_1));
        canvas.drawPath(path, paint);

        //圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#c7a275"));
        canvas.drawCircle(x_2 + mViewWidth / 10, y_2 - getUpLevel(y_1), 10, paint);
        paint.clearShadowLayer();
        paint.setColor(Color.parseColor("#dcdcdc"));
        canvas.drawCircle(mViewWidth * 8 / 10, y_2 - getUpLevel(y_1), 10, paint);

        //文字
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sx_sp12));
        paint.setColor(Color.parseColor("#999999"));
        canvas.drawText(percent2_str, mViewWidth * 8 / 10 + 20, y_2, paint);

        //文字
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sx_sp12));
        paint.setColor(Color.parseColor("#c7a275"));
        canvas.drawText(percent2_val, x_2 + mViewWidth / 10, y_2 - getUpLevel(y_1) - 20, paint);

         /* ********************************************我的标注******************************************************************/

        //画圆
        // 2 、圆点坐标
        float circle_x = getLocation_x();
        float circle_y = getPadTop() + (float) ((mViewHeight - getPadTop()) * Math.sqrt(percent3));
        paint.setShadowLayer(2, 2, 8, Color.parseColor("#16000000"));
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);//充满
        canvas.drawCircle(circle_x, circle_y, 8, paint);

        //圆环
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(3);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.parseColor("#a78760"));
        canvas.drawCircle(circle_x, circle_y, 8, circlePaint);

        //画圆角矩形
        // 1、矩形长宽
        float axis_x = 160;
        float axis_y = 60;
        paint.setShadowLayer(4, 2, 8, Color.parseColor("#16000000"));
        paint.setColor(Color.WHITE);       //设置画笔颜色

        //圆角矩形
        RectF rectF = new RectF(circle_x - 16, circle_y - axis_y - 30, circle_x - 16 + axis_x, circle_y - 30);
        canvas.drawRoundRect(rectF, 10, 10, paint);

        //小三角
        path.reset();
        path.moveTo(circle_x - 9, circle_y - 30 - 1);
        path.lineTo(circle_x + 9, circle_y - 30 - 1);
        path.lineTo(circle_x, circle_y - 30 + 9);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        //文字
        paint.clearShadowLayer();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sx_sp12));
        paint.setColor(Color.parseColor("#666666"));
        canvas.drawText(LanguageUtil.onLanguage(getContext(), new LanguageUtil.IOnLanguage() {
            @Override
            public String onCN() {
                return "你在这里";
            }

            @Override
            public String onTW() {
                return "你在這裡";
            }

            @Override
            public String onEnglish() {
                return "You here";
            }
        }), circle_x - 16 + axis_x / 2, circle_y - axis_y / 2 - 30 + 12, paint);
    }

    private float getUpLevel(float level) {
        return 0;
    }

    private float getLeftLevel(float y) {
        float x = ((y - getPadTop()) / mViewHeight) * (mViewWidth * 0.5f);
        return x > 30 ? 30 + getPadLeft() : x + getPadLeft();
    }

    //距离顶部高度
    private float getPadTop() {
        return percent1 != 0 && (percent1 < 0.1 || percent3 < 0.1) ? 80 : 0;
    }

    //距离底部高度
    private int getPadBottom() {
        return 10;
    }

    //距离顶部高度
    private float getPadLeft() {
        return getResources().getDimensionPixelSize(R.dimen.sx_dp6);
    }

    //定点的左距离
    private float getLocation_x() {
        float x_3 = getPadLeft() + (float) (mViewWidth * 0.5 * Math.sqrt(percent3));
        float maxLocationLeft = 30 + getPadLeft();
        return x_3 > maxLocationLeft ? maxLocationLeft : x_3;
    }
}
