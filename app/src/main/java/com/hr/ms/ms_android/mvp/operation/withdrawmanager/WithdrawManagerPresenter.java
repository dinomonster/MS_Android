package com.hr.ms.ms_android.mvp.operation.withdrawmanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.WithdrawCashBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
import com.hr.ms.ms_android.network.ResponseCode;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.hr.ms.ms_android.constants.CommonConstants.PAGESIZE;

/**
 * Created by Dino on 2018/4/3.
 */

public class WithdrawManagerPresenter extends BasePresenter implements WithdrawManagerContract.Presenter {
    private final WithdrawManagerContract.View mView;

    ServiceRepository repository;

    public WithdrawManagerPresenter(WithdrawManagerContract.View view,
                                    ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getWithdrawCashList(int pageNo, String orderNo, String userName, Integer withdrawCashMode, Integer checkStatus, Long ApplyTimeStart, Long ApplyTimeEnd, Integer sortRule) {
        Map<String, Object> map = new HashMap();
//        map.put("orderNo", orderNo);
        map.put("userName", userName);
        ApiQueryMapUtils.addQueryMap("withdrawCashMode", withdrawCashMode,map);
        ApiQueryMapUtils.addQueryMap("checkStatus", checkStatus,map);
        ApiQueryMapUtils.addQueryMap("ApplyTimeStart", ApplyTimeStart,map);
        ApiQueryMapUtils.addQueryMap("ApplyTimeEnd", ApplyTimeEnd,map);
        ApiQueryMapUtils.addQueryMap("sortRule", sortRule,map);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getWithdrawCashList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<List<WithdrawCashBean>>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<List<WithdrawCashBean>> value) {
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
