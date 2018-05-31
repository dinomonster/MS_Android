package com.better.appbase.likelib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.better.appbase.R;

/**
 * Created by 小李
 */

public class TumblrLikeView extends ImageView implements View.OnClickListener {

    private int mCurrentLikeStatus = 0;
    private static int STATUS_LIKE = 0;
    private static int STATUS_DISLIKE = 1;
    private TumblrLikeAnimListener mAnimListener;

    public TumblrLikeView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TumblrLikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public TumblrLikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }


    public void setLikeStatus(int likeStatus) {
        mCurrentLikeStatus = likeStatus;
        if (mCurrentLikeStatus == STATUS_LIKE) {
            setImageResource(R.drawable.img_likestatus_like);
        } else {
            setImageResource(R.drawable.img_likestatus_dislike);
        }
    }


    public void setLikeStatus(int likeStatus, TumblrLikeAnimListener mAnimListener) {
        setLikeStatus(likeStatus);
        this.mAnimListener = mAnimListener;
    }

    private void changeLikeStatus() {
        if (mCurrentLikeStatus == STATUS_LIKE) {
            mCurrentLikeStatus = STATUS_DISLIKE;
        } else {
            mCurrentLikeStatus = STATUS_LIKE;
        }
        setLikeStatus(mCurrentLikeStatus);
    }


    @Override
    public void onClick(View v) {
        if (mCurrentLikeStatus == STATUS_LIKE) {
            TumblrLikePopUtil.getInstance().excuteAnim(this, TumblrLikePopUtil.LIKE_CANCEL, mAnimListener);
        } else {
            TumblrLikePopUtil.getInstance().excuteAnim(this, TumblrLikePopUtil.LIKE_ADD, mAnimListener);
        }
        changeLikeStatus();
    }
}
