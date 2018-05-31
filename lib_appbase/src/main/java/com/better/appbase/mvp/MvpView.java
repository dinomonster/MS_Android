package com.better.appbase.mvp;

public interface MvpView {

    void showToast(String toast);

    void showProgressDialog(String msg);
    void dismissProgressDialog();
}
