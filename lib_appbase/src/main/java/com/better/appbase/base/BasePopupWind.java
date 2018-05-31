package com.better.appbase.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

import com.better.appbase.utils.ScreenUtils;
import com.socks.library.KLog;

public class BasePopupWind extends PopupWindow {

    private Context mContext;
    protected View contentView;

    public BasePopupWind(Context context) {
        this.mContext = context;
    }

    public BasePopupWind(Context context, View contentView, int width, int height) {
        super(contentView, width, height, true);

        this.mContext = context;
        this.contentView = contentView;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);

            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
            BasePopupWind.super.showAsDropDown(anchor);
        } else {
            BasePopupWind.super.showAsDropDown(anchor);
        }
    }


    //此方法为了兼容键盘显示布局改变时popwind显示区改变问题
    public void showAsKeyChangeView(View keyeAnchor, View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);

            Rect keyeAnchorFrame = new Rect();
            keyeAnchor.getLocalVisibleRect(keyeAnchorFrame);

            float height = anchor.getResources().getDisplayMetrics().heightPixels * ((float) visibleFrame.bottom / (visibleFrame.bottom + keyeAnchorFrame.bottom));

            setHeight((int) (visibleFrame.bottom + keyeAnchorFrame.bottom - height));
            BasePopupWind.super.showAsDropDown(anchor);
        } else {
            BasePopupWind.super.showAsDropDown(anchor);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }
}

