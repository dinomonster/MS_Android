package com.better.appbase.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.better.appbase.anima.BaseAnimatorListener;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: AnimatorUtils.java
 * Author: Better
 * Create: 2018/4/2 15:03
 * <p>
 * Changes (from 2018/4/2)
 * -----------------------------------------------------------------
 * 2018/4/2 : Create AnimatorUtils.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class AnimatorUtils {

    private static void changeHeight(final View view, final int statHeight, final int endHeight) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = statHeight + (int) (currentValue * (endHeight - statHeight));
                view.setLayoutParams(params);
            }
        });
        animator.setDuration(80).start();
    }

    //根据View宽度改变动画隐藏
    public static void dissmissOnWidth(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        final int tempWidth = view.getLayoutParams().width;

        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = (int) (currentValue * tempWidth);
                view.setLayoutParams(params);
            }
        });

        animator.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                view.setVisibility(View.GONE);
            }
        });

        animator.setDuration(800).start();
    }
}
