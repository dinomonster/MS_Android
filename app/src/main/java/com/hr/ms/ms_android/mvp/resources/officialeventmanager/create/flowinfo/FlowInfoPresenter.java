package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo;

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

public class FlowInfoPresenter extends BasePresenter implements FlowInfoContract.Presenter {
    private final FlowInfoContract.View mView;

    ServiceRepository repository;

    public FlowInfoPresenter(FlowInfoContract.View view,
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
    public void deleteFlowPoints(String flowInfoId) {
        Map<String, Object> map = new HashMap();
        map.put("flowInfoId", flowInfoId);
        mView.showProgressDialog("删除流程节点...");
        Disposable disposable = repository.deleteFlowPoints(map)
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
                                mView.showToast("删除流程节点失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("删除流程节点失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
