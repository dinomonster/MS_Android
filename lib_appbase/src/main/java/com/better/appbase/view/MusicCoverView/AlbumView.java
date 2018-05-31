package com.better.appbase.view.MusicCoverView;

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
 * 专辑封面
 * Created by wcy on 2015/11/30.
 */
public class AlbumView extends FrameLayout {
    private AppCompatImageView imageCover;
    private AppCompatImageView imageNeedle;
    private ObjectAnimator mPlayAnimator;
    private ObjectAnimator mPauseAnimator;
    private ObjectAnimator mCoverAnimator;

    public AlbumView(@NonNull Context context) {
        this(context, null);
    }

    public AlbumView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs, defStyleAttr);
    }

    /**
     * 确定图片起始坐标与旋转中心坐标
     */
    private void initCustomAttrs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.music_album_view, this);

        AppCompatImageView image_needle = layoutView.findViewById(R.id.image_needle);
        AppCompatImageView album_view1 = layoutView.findViewById(R.id.album_view1);
        AppCompatImageView image_cover = layoutView.findViewById(R.id.image_cover);
        AppCompatImageView album_view3 = layoutView.findViewById(R.id.album_view3);

        ImageLoadUtils.loadImage(context, R.mipmap.music_album_view0, image_needle, -1);
        ImageLoadUtils.loadImage(context, R.mipmap.music_album_view1, album_view1, -1);
        ImageLoadUtils.loadImage(context, R.mipmap.music_album_view4, image_cover, -1);
        ImageLoadUtils.loadImage(context, R.mipmap.music_album_view3, album_view3, -1);

        imageCover = layoutView.findViewById(R.id.image_cover);
        imageNeedle = layoutView.findViewById(R.id.image_needle);

        layoutView.isInEditMode();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        imageNeedle.setPivotX((float) 93 * imageNeedle.getWidth() / 132);
        imageNeedle.setPivotY((float) 56 * imageNeedle.getHeight() / 312);
        imageNeedle.setRotation(-25f);
    }

    public void initAnimator() {
        mPlayAnimator = ObjectAnimator.ofFloat(imageNeedle, "rotation", -25.0f, 0.0f);
        mPlayAnimator.setInterpolator(new LinearInterpolator());
        mPlayAnimator.setDuration(300);

        mPauseAnimator = ObjectAnimator.ofFloat(imageNeedle, "rotation", 0.0f, -25.0f);
        mPauseAnimator.setInterpolator(new LinearInterpolator());
        mPauseAnimator.setDuration(300);

        mCoverAnimator = ObjectAnimator.ofFloat(imageCover, "rotation", imageCover.getRotation(), imageCover.getRotation() + 360);
        mCoverAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mCoverAnimator.setInterpolator(new LinearInterpolator());
        mCoverAnimator.setDuration(15000);
    }

    public void setImageCover(Context context, String coverPath) {
        ImageLoadUtils.loadCropCircleImage(context, coverPath, imageCover, R.mipmap.music_album_view4);
    }

    public void start() {
        if (mPlayAnimator == null) {
            initAnimator();
        }

        if (!mPlayAnimator.isStarted()) {
            mPlayAnimator.start();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mCoverAnimator.isPaused()) {
                mCoverAnimator.resume();
            } else {
                if (!mCoverAnimator.isStarted()) {
                    mCoverAnimator.start();
                }
            }
        } else {
            if (!mCoverAnimator.isStarted()) {
                mCoverAnimator.start();
            }
        }


    }

    public void pause() {
        if (mPauseAnimator == null) {
            initAnimator();
        }

        if (!mPauseAnimator.isStarted()) {
            mPauseAnimator.start();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!mCoverAnimator.isPaused()) {
                mCoverAnimator.pause();
            }
        } else {
            mCoverAnimator.cancel();
        }
    }

    public void quickStart() {
        if (mPlayAnimator == null) {
            initAnimator();
        }

        //指针
        if (imageNeedle != null) {
            imageNeedle.setRotation(0f);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //圆盘
            if (mCoverAnimator.isPaused()) {
                mCoverAnimator.resume();
            } else {
                if (!mCoverAnimator.isStarted()) {
                    mCoverAnimator.start();
                }
            }
        } else {
            if (!mCoverAnimator.isStarted()) {
                mCoverAnimator.start();
            }
        }
    }
}
