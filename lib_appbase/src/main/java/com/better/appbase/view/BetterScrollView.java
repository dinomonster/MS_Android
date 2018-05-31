package com.better.appbase.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

import static com.better.appbase.view.BetterScrollView.OnScrollListener.SCROLL_STATE_FLING;
import static com.better.appbase.view.BetterScrollView.OnScrollListener.SCROLL_STATE_IDLE;
import static com.better.appbase.view.BetterScrollView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class BetterScrollView extends NestedScrollView {

    public interface Listener {

        /**
         * The view is not scrolling. Note navigating the list using the trackball counts as
         * being in the idle state since these transitions are not animated.
         */
        int SCROLL_STATE_IDLE = 0;

        /**
         * The user is scrolling using touch, and their finger is still on the screen
         */
        int SCROLL_STATE_TOUCH_SCROLL = 1;

        /**
         * The user had previously been scrolling using touch and had performed a fling. The
         * animation is now coasting to a stop
         */
        int SCROLL_STATE_FLING = 2;

        void onScrollStateChanged(BetterScrollView view, int scrollState);

        void onScroll(BetterScrollView view, boolean isTouchScroll, int l, int t, int oldl, int oldt);
    }

    public abstract static class OnScrollListener implements Listener {
        @Override
        public void onScroll(BetterScrollView view, boolean isTouchScroll, int l, int t, int oldl, int oldt) {

        }
    }

    private static final boolean DEBUG = false;

    private static final int CHECK_SCROLL_STOP_DELAY_MILLIS = 80;
    private static final int MSG_SCROLL = 1;

    private boolean mIsTouched = false;
    private int mScrollState = SCROLL_STATE_IDLE;

    private OnScrollListener mOnScrollListener;

    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {

        private int mLastY = Integer.MIN_VALUE;

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_SCROLL) {
                final int scrollY = getScrollY();
                log("handleMessage, lastY = " + mLastY + ", y = " + scrollY);
                if (!mIsTouched && mLastY == scrollY) {
                    mLastY = Integer.MIN_VALUE;
                    setScrollState(SCROLL_STATE_IDLE);
                } else {
                    mLastY = scrollY;
                    restartCheckStopTiming();
                }
                return true;
            }
            return false;
        }
    });

    private void restartCheckStopTiming() {
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, CHECK_SCROLL_STOP_DELAY_MILLIS);
    }

    public BetterScrollView(Context context) {
        super(context);
    }

    public BetterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        handleDownEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        handleUpEvent(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        log(String.format("onScrollChanged, isTouched = %s, l: %d --> %d, t: %d --> %d", mIsTouched, oldl, l, oldt, t));
        if (mIsTouched) {
            setScrollState(SCROLL_STATE_TOUCH_SCROLL);
        } else {
            setScrollState(SCROLL_STATE_FLING);
            restartCheckStopTiming();
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(this, mIsTouched, l, t, oldl, oldt);
        }
    }

    private void handleDownEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                log("handleEvent, action = " + ev.getAction());
                mIsTouched = true;
                break;
        }
    }

    private void handleUpEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                log("handleEvent, action = " + ev.getAction());
                mIsTouched = false;
                restartCheckStopTiming();
                break;
        }
    }

    private void setScrollState(int state) {
        if (mScrollState != state) {
            log(String.format("---- onScrollStateChanged, state: %d --> %d", mScrollState, state));
            mScrollState = state;
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(this, state);
            }
        }
    }

    private void log(String obj) {
        if (DEBUG) {
            Log.d(getClass().getSimpleName(), obj);
        }
    }
}