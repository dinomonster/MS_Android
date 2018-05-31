package com.hr.ms.ms_android.mvp.main;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.AliOSSBean;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.data.AccountHelper;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dino on 2018/4/3.
 */

public class MainPresenter extends BasePresenter implements MainContract.Presenter {
    private final MainContract.View mView;

    ServiceRepository repository;

    public MainPresenter(MainContract.View view,
                         ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getUploadAuth() {
        Map<String,Object> map = new HashMap();
        Disposable disposable = repository.getUploadAuth(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<AliOSSBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<AliOSSBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                AccountHelper.setAliOSS(value.getData());
                                break;
                            default:
//                                mView.showToast(value.getStatus());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        disposables.add(disposable);
    }
}
