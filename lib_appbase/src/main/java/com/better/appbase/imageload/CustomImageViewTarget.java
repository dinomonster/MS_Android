package com.better.appbase.imageload;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.better.appbase.intef.LoadListener;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: CustomImageViewTarget.java
 * Author: Better
 * Create: 2018/3/26 20:07
 * <p>
 * Changes (from 2018/3/26)
 * -----------------------------------------------------------------
 * 2018/3/26 : Create CustomImageViewTarget.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class CustomImageViewTarget extends ImageViewTarget<Bitmap> {

    private LoadListener<Bitmap> bitmapLoadListener;

    public CustomImageViewTarget(ImageView view, LoadListener<Bitmap> bitmapLoadListener) {
        super(view);

        this.bitmapLoadListener = bitmapLoadListener;
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        view.setImageBitmap(resource);

        if (bitmapLoadListener != null) {
            bitmapLoadListener.onLoadComplete(resource);
        }
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);

        if (bitmapLoadListener != null) {
            bitmapLoadListener.onLoadFailed(null);
        }
    }
}
