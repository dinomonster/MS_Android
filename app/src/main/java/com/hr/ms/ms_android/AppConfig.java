package com.hr.ms.ms_android;

import android.support.multidex.MultiDexApplication;

import com.better.appbase.utils.SPUtils;
import com.better.appbase.utils.ToastTools;
import com.hr.ms.ms_android.data.AccountHelper;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.socks.library.KLog;


//import com.tyky.fda.data.DaggerTestRepositoryComponent;

/**
 * Created by Dino on 4/17 0017.
 */

public class AppConfig extends MultiDexApplication {
    public static boolean isShow = false;
    private TimePickerDialog.Builder tpb;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AccountHelper.init();
        ToastTools.init(this);
        SPUtils.init(this);
        KLog.init(BuildConfig.DEBUG);
        initTimePicker();
    }

    private void initTimePicker() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        //时间选择器
        tpb = new TimePickerDialog.Builder()
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("时间选择")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - tenYears)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.color_9f9f9f))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.main_color))
                .setWheelItemTextSize(12);
    }

    public TimePickerDialog.Builder getimePicker() {
        return tpb;
    }


    private static AppConfig instance;

    public static AppConfig getInstance() {
        return instance;
    }

}
