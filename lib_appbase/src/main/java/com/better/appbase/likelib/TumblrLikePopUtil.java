package com.better.appbase.likelib;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.better.appbase.R;

/**
 * Created by 小李
 */
public class TumblrLikePopUtil {

    public static final int LIKE_ADD = 0;
    public static final int LIKE_CANCEL = 1;
    private static TumblrLikePopUtil mInstance;
    private static PopupWindow popupWindow;

    private Handler handler = new Handler();
    private int[] location = new int[2];

    private LinearLayout layout_heartbroken;
    private ImageView like_animlike;
    private ImageView dislike_heart_broke_left;
    private ImageView dislike_heart_broke_right;
    private View mCacheView;
    private Context mContext;

    private Animation likeanim;
    private Animation dislike_heartbroken_animLeft, dislike_hearebroken_animright;
    private TumblrLikeAnimListener mAnimListener;


    public static TumblrLikePopUtil getInstance() {
        if (mInstance == null) {
            mInstance = new TumblrLikePopUtil();
        }
        return mInstance;
    }

    public static void destory() {
        mInstance = null;
        popupWindow = null;
    }


    private void createPopWindow(View rootview) {
        if (mCacheView == null) {
            inflatePopView(rootview);
        }
        mContext = rootview.getContext();
        popupWindow = new PopupWindow(mCacheView, Helper.getPxFromDip(mContext, width_pop), Helper.getPxFromDip(mContext, width_pop));
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private int width_pop = 120;


    private void inflatePopView(View rootview) {
        mCacheView = LayoutInflater.from(rootview.getContext()).inflate(R.layout.like_anim_pop, null);
        like_animlike = mCacheView.findViewById(R.id.like_btn_animlike);
        dislike_heart_broke_left = mCacheView.findViewById(R.id.heart_broke_left);
        dislike_heart_broke_right = mCacheView.findViewById(R.id.heart_broke_right);
        layout_heartbroken = mCacheView.findViewById(R.id.layout_heartbroken);
        createAnim(rootview);
    }

    private void createAnim(View rootview) {
        likeanim = AnimationUtils.loadAnimation(rootview.getContext(), R.anim.like_anim);
        likeanim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismissPop();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        dislike_heartbroken_animLeft = AnimationUtils.loadAnimation(rootview.getContext(), R.anim.dislike_leftheart_anim);
        dislike_hearebroken_animright = AnimationUtils.loadAnimation(rootview.getContext(), R.anim.dislike_rightheart_anim);
        dislike_hearebroken_animright.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismissPop();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void showPop(View mView) {
        if (popupWindow == null) {
            createPopWindow(mView);
        }
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        mView.getLocationOnScreen(location);
        popupWindow.showAtLocation(mView, Gravity.NO_GRAVITY,
                location[0] + mView.getWidth() / 2 - Helper.getPxFromDip(mContext, width_pop / 2),
                location[1] - Helper.getPxFromDip(mContext, width_pop)+Helper.getPxFromDip(mContext,20));
    }


    private void dismissPop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
                if (mAnimListener != null) {
                    mAnimListener.onAnimFinished();
                }
            }
        }, 10);
    }


    public void excuteAnim(View mView, int status, TumblrLikeAnimListener mAnimListener) {
        this.mAnimListener = mAnimListener;
        showPop(mView);
        if (status == LIKE_ADD) {
            like_animlike.setVisibility(View.VISIBLE);
            layout_heartbroken.setVisibility(View.GONE);
            like_animlike.startAnimation(likeanim);
        } else {
            like_animlike.setVisibility(View.GONE);
            layout_heartbroken.setVisibility(View.VISIBLE);
            dislike_heart_broke_left.startAnimation(dislike_heartbroken_animLeft);
            dislike_heart_broke_right.startAnimation(dislike_hearebroken_animright);
        }
    }


}
