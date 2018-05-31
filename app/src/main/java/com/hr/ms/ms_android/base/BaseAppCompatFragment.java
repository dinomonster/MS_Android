package com.hr.ms.ms_android.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.better.appbase.utils.ToastTools;
import com.hr.ms.ms_android.widget.dialog.DialogHelper;

/**
 * Created by lianghuiyong on 2017/7/11.
 */

public abstract class BaseAppCompatFragment extends BaseLaiLoadingFragment implements MvpView {


    private CharSequence title;

    protected DialogHelper dialogHelper;

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    protected abstract MvpPresenter getPresenter();

    public void onEvent(String string) {

    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public void init() {
        dialogHelper = new DialogHelper(getActivity());
        if (getPresenter() != null) {
            getPresenter().subscribe();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
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
            dialogHelper = new DialogHelper(getActivity());
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

    public void nextActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

    public void nextActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
