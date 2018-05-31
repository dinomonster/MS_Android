package com.better.appbase.mvp;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter implements MvpPresenter {

    @NonNull
    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (disposables != null && disposables.size() > 0){
            disposables.clear();
        }
    }


}
