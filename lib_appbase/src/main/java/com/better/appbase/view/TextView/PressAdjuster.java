package com.better.appbase.view.TextView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Project Name:SuperTextView
 * Author:CoorChice
 * Date:2017/8/2
 * Notes:
 */

public class PressAdjuster extends SuperTextView.Adjuster {

    private int pressBgColor = Color.TRANSPARENT;
    private int pressTextColor = -99;
    private int normalTextColor = -99;
    private boolean press = false;
    private Path solidPath;
    private RectF solidRectF;
    private Paint paint;

    protected PressAdjuster(int pressBgColor) {
        this.pressBgColor = pressBgColor;
        setOpportunity(Opportunity.BEFORE_DRAWABLE);
        initPaint();
    }

    private void initPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    protected PressAdjuster setPressTextColor(int pressTextColor) {
        this.pressTextColor = pressTextColor;
        return this;
    }

    @Override
    protected void adjust(SuperTextView v, Canvas canvas) {
        if (press) {
            if (solidPath == null) {
                solidPath = new Path();
            } else {
                solidPath.reset();
            }
            if (solidRectF == null) {
                solidRectF = new RectF();
            } else {
                solidRectF.setEmpty();
            }
            float strokeWidth = v.getStrokeWidth();
            solidRectF.set(strokeWidth, strokeWidth, v.getWidth() - strokeWidth,
                    v.getHeight() - strokeWidth);
            solidPath.addRoundRect(solidRectF, v.getCorners(), Path.Direction.CW);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(pressBgColor);
            canvas.drawPath(solidPath, paint);
        }
    }


    @Override
    public boolean onTouch(SuperTextView v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (normalTextColor == -99) {
                    normalTextColor = v.getCurrentTextColor();
                }
                if (pressTextColor != -99 && v.getCurrentTextColor() != pressTextColor) {
                    v.setTextColor(pressTextColor);
                }
                press = true;
                v.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (normalTextColor != -99 && v.getCurrentTextColor() != normalTextColor) {
                    v.setTextColor(normalTextColor);
                }
                press = false;
                v.postInvalidate();
                break;
        }
        return true;
    }
}