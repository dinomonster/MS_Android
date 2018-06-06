package com.hr.ms.ms_android.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.better.appbase.utils.ToastTools;
import com.better.appbase.view.CommonStatusBar;
import com.hr.ms.ms_android.R;
import com.hr.ms.ms_android.widget.dialog.DialogHelper;
import com.sxzx.swideback.SwipeBackActivityBase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity extends BaseSwipeBackActivity implements SwipeBackActivityBase, MvpView {

    @TransitionType
    int transitionType = TransitionType.DEFAULT;

    protected DialogHelper dialogHelper;

    protected abstract MvpPresenter getPresenter();

    //初始化布局
    public abstract int setViewId();

    // 初始化数据
    public void initData(){
        dialogHelper = new DialogHelper(this);
    }

    public void initRecyclerView() {

    }

    public int getTransitionType() {
        return transitionType;
    }

    public void setTransitionType(@TransitionType int transitionType) {
        this.transitionType = transitionType;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //init Transition
        switch (getTransitionType()) {
            case TransitionType.DEFAULT:
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;

            case TransitionType.SPLASH:
                //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }

        super.onCreate(savedInstanceState);

        CommonStatusBar.setStatusBarLight(this);

        //兼容svg
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        AppManager.getAppManager().addActivity(this);

        View view = getView();
        if (view != null) {
            setContentView(view);
        }
        ButterKnife.bind(this, view);
        initData();

        initRecyclerView();
    }

    protected View getView() {
        if (setViewId() != 0) {
            View rootView = getLayoutInflater().inflate(setViewId(), null);
            return rootView;
        } else {
            return null;
        }
    }

    protected Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        //取消注册
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().removeActivity(this);

        super.onDestroy();
    }


    @Override
    public void finish() {
        super.finish();

        //init Transition
        switch (transitionType) {
            case TransitionType.DEFAULT:
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;

            case TransitionType.SPLASH:
                //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getPresenter() != null) {
            getPresenter().subscribe();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (getPresenter() != null) {
            getPresenter().unSubscribe();
        }
    }

    @Override
    public void showToast(String toast) {
        ToastTools.showToast(toast);
    }

    @Override
    public void showProgressDialog(String msg) {
        if(dialogHelper==null){
            dialogHelper = new DialogHelper(this);
        }
        if(dialogHelper!=null){
            dialogHelper.showProgressDialog(TextUtils.isEmpty(msg)?"加载数据中...":msg);
        }
    }

    @Override
    public void dismissProgressDialog() {
        if(dialogHelper!=null){
            dialogHelper.dismissProgressDialog();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventNull eventNull) {
    }

    public void nextActivity(Class<?> firstCls, Class<?> SecondCls) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(this, SecondCls));
        intents[1] = new Intent(this, firstCls);
        startActivities(intents);
    }

    public void nextActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void nextActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    public void nextActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void nextActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
