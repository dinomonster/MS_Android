package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.OfficialEventDetailBean;
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

public class CoorganizerPresenter extends BasePresenter implements CoorganizerInfoContract.Presenter {
    private final CoorganizerInfoContract.View mView;

    ServiceRepository repository;

    public CoorganizerPresenter(CoorganizerInfoContract.View view,
                                ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getOfficialEventDetail(String officialEventInfoId) {
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        mView.showProgressDialog("获取详情...");
        Disposable disposable = repository.getOfficialEventDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<OfficialEventDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<OfficialEventDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showDetail(value.getData());
                                break;

                            default:
                                mView.showToast("获取详情失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("获取详情失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void deleteEventSponorInfo(String coorganizerInfoId) {
        Map<String, Object> map = new HashMap();
        map.put("coorganizerInfoId", coorganizerInfoId);
        mView.showProgressDialog("删除承办方...");
        Disposable disposable = repository.deleteEventCoorganizer(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.deleteSuccess();
                                break;

                            default:
                                mView.showToast("删除承办方失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("删除承办方失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
