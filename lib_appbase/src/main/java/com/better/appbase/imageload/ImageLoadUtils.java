package com.better.appbase.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.better.appbase.R;
import com.better.appbase.intef.LoadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;


/**
 * 图片加载工具类
 * 这里用的Glide框架
 * Created by Dino on 11/17 0017.
 */

public class ImageLoadUtils {


    /**
     * 加载原图片
     *
     * @param image
     * @param imageView
     */
    public static void loadImageNoHolder(Context context, Object image, ImageView imageView) {
        //Android 7.0 及以上开启动画效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (image instanceof Bitmap) {
                Glide.with(context)
                        .load(image)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
            }
        } else {
            Glide.with(context)
                    .load(image)
                    .into(imageView);
        }
    }

    /**
     * 加载原图片
     *
     * @param image
     * @param imageView
     */
    public static void loadImageAddListener(Object imageUrl, ImageView imageView, LoadListener<Bitmap> listener) {
        //Android 7.0 及以上开启动画效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (imageUrl instanceof Bitmap) {
                Glide.with(imageView.getContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(new CustomImageViewTarget(imageView,listener));
            } else {
                Glide.with(imageView.getContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(new CustomImageViewTarget(imageView,listener));
            }
        } else {
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomImageViewTarget(imageView,listener));
        }
    }


    /**
     * 加载原图片
     *
     * @param image
     * @param imageView
     */
    public static void loadImage(Context context, Object image, ImageView imageView) {

        //Android 7.0 及以上开启动画效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (image instanceof Bitmap) {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.default_back))
                        //.transition(BitmapTransitionOptions.withCrossFade())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.default_back))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
            }
        } else {
            Glide.with(context)
                    .load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.default_back))
                    .into(imageView);
        }
    }

    public static void loadImage(Context context, Object image, ImageView imageView, int defaultImage) {

        //Android 7.0 及以上开启动画效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (image instanceof Bitmap) {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(defaultImage == 0 ? R.drawable.default_back : defaultImage))
                        //.transition(BitmapTransitionOptions.withCrossFade())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(defaultImage == 0 ? R.drawable.default_back : defaultImage))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
            }
        } else {
            Glide.with(context)
                    .load(image)
                    .apply(new RequestOptions().placeholder(defaultImage == 0 ? R.drawable.default_back : defaultImage))
                    .into(imageView);
        }
    }

    /**
     * 处理成圆形
     *
     * @param image
     * @param imageView
     */
    public static void loadCropCircleImage(final Context context, Object image, final ImageView imageView) {
        //Android 7.0 及以上开启动画效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (image instanceof Bitmap) {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.default_back))
                        //.transition(BitmapTransitionOptions.withCrossFade())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.default_head).circleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
            }
        } else {
            Glide.with(context)
                    .load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.default_head).circleCrop())
                    .into(imageView);
        }
    }

    public static void loadCropCircleImage(final Context context, Object image, final ImageView imageView, int defaultImage) {
        //Android 7.0 及以上开启动画效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (image instanceof Bitmap) {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.default_back))
                        //.transition(BitmapTransitionOptions.withCrossFade())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(image)
                        .apply(new RequestOptions().placeholder(defaultImage == 0 ? R.drawable.default_head : defaultImage).circleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
            }
        } else {
            Glide.with(context)
                    .load(image)
                    .apply(new RequestOptions().placeholder(defaultImage == 0 ? R.drawable.default_head : defaultImage).circleCrop())
                    .into(imageView);
        }

    }


}
