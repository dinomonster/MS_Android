package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo.activitycard;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.CardActiveDetailBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.hr.ms.ms_android.constants.CommonConstants.PAGESIZE;

/**
 * Created by Dino on 2018/4/3.
 */

public class ActivityCardPresenter extends BasePresenter implements ActivityCardContract.Presenter {
    private final ActivityCardContract.View mView;

    ServiceRepository repository;

    public ActivityCardPresenter(ActivityCardContract.View view,
                                 ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getCardActiveDetail(int pageNo, Integer stageId, Integer cardType) {
        Map<String, Object> map = new HashMap();
        map.put("stageId", stageId);
        map.put("cardType", cardType);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getCardActiveDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<CardActiveDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<CardActiveDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showList(value.getData());
                                break;

                            default:
                                mView.showNetWorkError();
//                                mView.showToast(value.getStatus());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showNetWorkError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }


}
