package com.yyydjk.library;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Orientation {
    Context mContext;
    private Drawable unSelectedDrawable;
    private Drawable selectedDrawable;
    private int orientation;

    public Orientation(Context context) {
        mContext = context;
    }

    public static final int left = 0;
    public static final int top = 1;
    public static final int right = 2;
    public static final int bottom = 3;

    public Drawable getLeft(boolean close) {
        return orientation == left ? (close ? unSelectedDrawable : selectedDrawable) : null;
    }

    public Drawable getTop(boolean close) {
        return orientation == top ? (close ? unSelectedDrawable : selectedDrawable) : null;
    }

    public Drawable getRight(boolean close) {
        return orientation == right ? (close ? unSelectedDrawable : selectedDrawable) : null;
    }

    public Drawable getBottom(boolean close) {
        return orientation == bottom ? (close ? unSelectedDrawable : selectedDrawable) : null;
    }

    /**
     * 初始化位置参数
     *
     * @param orientation
     * @param menuUnselectedIcon
     */
    public void init(int orientation, int menuSelectedIcon, int menuUnselectedIcon) {

        unSelectedDrawable = mContext.getResources().getDrawable(menuUnselectedIcon);
        selectedDrawable = mContext.getResources().getDrawable(menuSelectedIcon);
        this.orientation = orientation;
    }
}
