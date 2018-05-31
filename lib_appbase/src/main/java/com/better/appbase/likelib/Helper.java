package com.better.appbase.likelib;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by 小李
 */

public class Helper {
    public static int getPxFromDip(Context context, float dip) {
        return (int) (0.5f + getDensity(context) * dip);
    }


    public static float getDensity(Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

}
