package com.better.appbase.utils;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: ImageViewUtils.java
 * Author: Better
 * Create: 2018/3/26 19:26
 * <p>
 * Changes (from 2018/3/26)
 * -----------------------------------------------------------------
 * 2018/3/26 : Create ImageViewUtils.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class ImageViewUtils {

    //改变tint色调
    public static void setTintColor(ImageView imageView, @ColorInt int tintColor) {
        Drawable originalBitmapDrawable = imageView.getDrawable();
        imageView.setImageDrawable(tintDrawable(originalBitmapDrawable, ColorStateList.valueOf(tintColor)));

    }

    private static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
