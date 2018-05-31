package com.hr.ms.ms_android.mvp.operation.stagemanager.detail;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.StageDetailBean;
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

public class StageDetailPresenter extends BasePresenter implements StageDetailContract.Presenter {
    private final StageDetailContract.View mView;
    ServiceRepository repository;

    public StageDetailPresenter(StageDetailContract.View view,
                                ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getStageDetail(Integer stageId) {
        Map<String, Object> map = new HashMap();
        map.put("stageId", stageId);
        mView.showProgressDialog("获取驿站详情...");
        Disposable disposable = repository.getStageDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<StageDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<StageDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showDetail(value.getData());
                                break;

                            default:
                                mView.showToast("获取驿站详情失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("网络错误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

}
