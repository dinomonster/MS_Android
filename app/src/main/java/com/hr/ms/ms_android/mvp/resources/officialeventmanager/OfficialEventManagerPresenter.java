package com.hr.ms.ms_android.mvp.resources.officialeventmanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.OfficialEventListBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
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

public class OfficialEventManagerPresenter extends BasePresenter implements OfficialEventManagerContract.Presenter {
    private final OfficialEventManagerContract.View mView;

    ServiceRepository repository;

    public OfficialEventManagerPresenter(OfficialEventManagerContract.View view,
                                         ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getOfficialEventList(int pageNo, String eventTitle, Integer eventStatus, Integer eventType, Long eventBeginDateStart, Long eventBeginDateEnd, Integer orderRule) {
        Map<String, Object> map = new HashMap();
//        map.put("orderNo", orderNo);
        ApiQueryMapUtils.addQueryMap("eventTitle", eventTitle, map);
        ApiQueryMapUtils.addQueryMap("eventStatus", eventStatus, map);
        ApiQueryMapUtils.addQueryMap("eventType", eventType, map);
        ApiQueryMapUtils.addQueryMap("eventBeginDateStart", eventBeginDateStart, map);
        ApiQueryMapUtils.addQueryMap("eventBeginDateEnd", eventBeginDateEnd, map);
        ApiQueryMapUtils.addQueryMap("orderRule", orderRule, map);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getOfficialEventList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<OfficialEventListBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<OfficialEventListBean> value) {
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
