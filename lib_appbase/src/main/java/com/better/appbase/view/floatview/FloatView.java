package com.better.appbase.view.floatview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.better.appbase.R;
import com.better.appbase.imageload.ImageLoadUtils;


/**
 * Created by huiyong on 2017/11/23.
 */

public class FloatView extends FrameLayout {
    private AppCompatImageView imageCover;
    private ObjectAnimator mCoverAnimator;
    private Context context;

    public FloatView(@NonNull Context context) {
        this(context, null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initCustomAttrs(context, attrs, defStyleAttr);
    }

    /**
     * 确定图片起始坐标与旋转中心坐标
     */
    private void initCustomAttrs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.float_layout, this);
        imageCover = layoutView.findViewById(R.id.image_cover);

        layoutView.isInEditMode();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        start();
    }

    public void initAnimator() {
        mCoverAnimator = ObjectAnimator.ofFloat(imageCover, "rotation", imageCover.getRotation(), imageCover.getRotation() + 360);
        mCoverAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mCoverAnimator.setInterpolator(new LinearInterpolator());
        mCoverAnimator.setDuration(15000);
    }

    public void start() {
        if (mCoverAnimator == null) {
            initAnimator();
        }

        if (mCoverAnimator.isStarted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mCoverAnimator.resume();
            }else {
                mCoverAnimator.start();
            }
        } else {
            mCoverAnimator.start();
        }
    }

    public void pause() {
        if (mCoverAnimator == null) {
            initAnimator();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mCoverAnimator.pause();
        }else {
            mCoverAnimator.cancel();
        }
    }

    public void changeImage(String path) {
        ImageLoadUtils.loadCropCircleImage(context, path, imageCover);
    }
}
