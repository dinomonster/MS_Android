package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo.immanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
import com.hr.ms.ms_android.utils.PresenterUtils;
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

public class ImManagerPresenter extends BasePresenter implements ImManagerContract.Presenter {
    private final ImManagerContract.View mView;
    ServiceRepository repository;

    public ImManagerPresenter(ImManagerContract.View view,
                              ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void addEventImManager(String officialEventInfoId, String imManagerId, Integer imManagerType, String freeNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"辅导员类型", "免费报名限制人数"},
                imManagerType, freeNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        map.put("imManagerId", imManagerId);
        map.put("imManagerType", imManagerType);
        map.put("freeNum", freeNum);
        mView.showProgressDialog("添加辅导员...");
        Disposable disposable = repository.addEventImManager(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("添加辅导员成功");
                                mView.success();
                                break;

                            default:
                                mView.showToast("添加辅导员失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("添加辅导员失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void editEventImManager(String imManagerInfoId, Integer imManagerType, String freeNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"辅导员类型", "免费报名限制人数"},
                imManagerType, freeNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("imManagerInfoId", imManagerInfoId);
        map.put("imManagerType", imManagerType);
        map.put("freeNum", freeNum);
        mView.showProgressDialog("修改辅导员...");
        Disposable disposable = repository.editEventImManager(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("修改辅导员成功");
                                mView.success();
                                break;

                            default:
                                mView.showToast("修改辅导员失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("修改辅导员失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
