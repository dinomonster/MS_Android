package com.hr.ms.ms_android.base;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;


import java.util.Iterator;
import java.util.Stack;

/**
 * Created by 小李 on 2018/1/9.
 */
public class AppManager {
    private static Stack<BaseActivity> activityStack;
    private static AppManager instance;

    private AppManager() {

    }

    /**
     * App Manager one time create Object
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * addActivity(baseActivity)
     */
    public void addActivity(BaseActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * findActivity(null)
     */
    public BaseActivity findActivity(Class<?> cls) {
        BaseActivity activity = null;
        for (BaseActivity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    // finishActivity
    public void finishActivity(Class<?> cls) {
        // 1 使用Iterator提供的remove方法，用于删除当前元素
        for (Iterator<BaseActivity> iterator = activityStack.iterator(); iterator.hasNext(); ) {
            BaseActivity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * finishAllActivity()
     */
    public void finishAllActivity() {
        // 1 使用Iterator提供的remove方法，删除元素
        for (Iterator<BaseActivity> iterator = activityStack.iterator(); iterator.hasNext(); ) {
            BaseActivity activity = iterator.next();
            activity.finish();
            iterator.remove();
        }
        activityStack.clear();
    }

//    //保存首页和另一个Activity
//    public void saveMainAndToAnotherActivity(Class<?> cls) {
//        // 1 使用Iterator提供的remove方法，删除元素
//        for (Iterator<BaseActivity> iterator = activityStack.iterator(); iterator.hasNext(); ) {
//            BaseActivity activity = iterator.next();
//            if (!activity.getClass().equals(SplashActivity.class)
//                    && !activity.getClass().equals(HomepageActivity.class)
//                    && !activity.getClass().equals(cls)) {
//
//                activity.finish();
//                iterator.remove();
//            }
//        }
//    }
//
//    //保存首页和另一个Activity
//    public void saveMainActivity() {
//        // 1 使用Iterator提供的remove方法，删除元素
//        for (Iterator<BaseActivity> iterator = activityStack.iterator(); iterator.hasNext(); ) {
//            BaseActivity activity = iterator.next();
//            if (!activity.getClass().equals(HomepageActivity.class)) {
//                activity.finish();
//                iterator.remove();
//            }
//        }
//    }

    /**
     * AppExit
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}

