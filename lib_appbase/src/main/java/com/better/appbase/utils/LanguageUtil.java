package com.better.appbase.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.Locale;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2017-2018, by Better, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * File: LanguageUtil.java
 * Author: Better
 * Create: 2018/1/20 10:49
 * <p>
 * Changes (from 2018/1/20)
 * -----------------------------------------------------------------
 * 2018/1/20 : Create LanguageUtil.java (梁惠涌);
 * -----------------------------------------------------------------
 */

public class LanguageUtil {
    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale TURKISH = new Locale("tr", "");

    public static void changeLanguage(Context context, Locale language) {
        Locale.setDefault(language);
        final Resources resources = context.getResources();
        final Configuration config = resources.getConfiguration();

        config.setLocale(language);

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认简体中文
     */
    public static String onLanguage(Context context, @NonNull IOnLanguage iOnLanguage) {
        switch (context.getResources().getConfiguration().locale.toString()) {
            //中文
            case "zh_CN":
                return iOnLanguage.onCN();

            //繁体中文
            case "zh_TW":
                return iOnLanguage.onTW();

            //英语
            case "en":
                return iOnLanguage.onEnglish();

            //默认中文
            default:
                return iOnLanguage.onCN();
        }
    }

    public interface IOnLanguage {
        /**
         * 简体中文
         * */
        String onCN();

        /**
         * 繁体中文
         * */
        String onTW();

        /**
         * 繁体中文
         * */
        String onEnglish();
    }
}
