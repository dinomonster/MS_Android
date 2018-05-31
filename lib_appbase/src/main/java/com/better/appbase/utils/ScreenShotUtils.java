package com.better.appbase.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.socks.library.KLog;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ScreenShotUtils.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/25 上午11:43
 *
 * Changes (from 2017/12/25)
 * -----------------------------------------------------------------
 * 2017/12/25 : Create ScreenShotUtils.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public class ScreenShotUtils {
    public static Bitmap createShareImage(WebView webView, float webViewHeight) {
        webView.buildDrawingCache();
        webView.setDrawingCacheEnabled(true);
        int width = webView.getWidth();
        if (webViewHeight <= 0.0f || width <= 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, (int) webViewHeight, Bitmap.Config.ARGB_8888);
        webView.draw(new Canvas(bitmap));
        return bitmap;
    }

    public static Bitmap createShareImage(ViewGroup viewGroup) {
        viewGroup.setDrawingCacheEnabled(true);
        viewGroup.buildDrawingCache();
        Bitmap result = viewGroup.getDrawingCache();
        if (result == null) {
            return getScreenShotBitmap(viewGroup);
        }
        return result;
    }

    static Bitmap getScreenShotBitmap(ViewGroup viewGroup) {
        boolean opaque;
        Bitmap.Config quality;
        boolean clear;
        int width = viewGroup.getRight() - viewGroup.getLeft();
        int height = viewGroup.getBottom() - viewGroup.getTop();
        opaque = viewGroup.getDrawingCacheBackgroundColor() != 0 || viewGroup.isOpaque();
        KLog.i("opaque:" + opaque);
        if (opaque) {
            quality = Bitmap.Config.RGB_565;
        } else {
            viewGroup.getDrawingCacheQuality();
            quality = Bitmap.Config.ARGB_8888;
        }
        Bitmap bitmap = Bitmap.createBitmap(viewGroup.getResources().getDisplayMetrics(), width, height, quality);
        if (opaque) {
            bitmap.setHasAlpha(false);
        }
        bitmap.setDensity(viewGroup.getResources().getDisplayMetrics().densityDpi);
        clear = viewGroup.getDrawingCacheBackgroundColor() != 0;
        Canvas canvas = new Canvas(bitmap);
        if (clear) {
            bitmap.eraseColor(viewGroup.getDrawingCacheBackgroundColor());
        }
        viewGroup.computeScroll();
        int restoreCount = canvas.save();
        canvas.translate((float) (-viewGroup.getScrollX()), (float) (-viewGroup.getScrollY()));
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        viewGroup.draw(canvas);
        canvas.restoreToCount(restoreCount);
        canvas.setBitmap(null);
        return bitmap;
    }

    public static Bitmap roundCorners(Bitmap bitmap, float radius) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        Bitmap clipped = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        new Canvas(clipped).drawRoundRect(new RectF(0.0f, 0.0f, (float) width, (float) height), radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        Bitmap rounded = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rounded);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(clipped, 0.0f, 0.0f, paint);
        return rounded;
    }

    public static Bitmap setViewToBitmapImage(View view) {
//Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}
