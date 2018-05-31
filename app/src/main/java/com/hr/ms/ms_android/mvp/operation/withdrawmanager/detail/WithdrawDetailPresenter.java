package com.hr.ms.ms_android.mvp.operation.withdrawmanager.detail;

import android.text.TextUtils;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.WithdrawApplyBean;
import com.hr.ms.ms_android.bean.WithdrawCashCheckBean;
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

public class WithdrawDetailPresenter extends BasePresenter implements WithdrawDetailContract.Presenter {
    private final WithdrawDetailContract.View mView;

    ServiceRepository repository;

    public WithdrawDetailPresenter(WithdrawDetailContract.View view,
                                   ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getWithdrawCashApplyDetails(final String orderNo) {
        Map<String, Object> map = new HashMap();
        map.put("orderNo", orderNo);
        mView.showProgressDialog("获取详情中...");
        Disposable disposable = repository.getWithdrawCashApplyDetails(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<WithdrawApplyBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<WithdrawApplyBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showWithdrawApplyBean(value.getData());
                                getWithdrawCashCheckDetails(orderNo);
                                break;

                            default:
                                mView.dismissProgressDialog();
                                mView.showToast("获取申请详情失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.dismissProgressDialog();
                        mView.showToast("网络错误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void getWithdrawCashCheckDetails(String orderNo) {
        Map<String, Object> map = new HashMap();
        map.put("orderNo", orderNo);
        Disposable disposable = repository.getWithdrawCashCheckDetails(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<WithdrawCashCheckBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<WithdrawCashCheckBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showWithdrawCashCheckBean(value.getData());
                                break;

                            default:
                                mView.showToast("获取审核详情失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.dismissProgressDialog();
                        mView.showToast("网络错误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void checkWithdrawCashApply(String orderNo, Integer checkStatus, String checkAmount, String checkUser, String reason) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"审核金额", "审核人"},
                checkAmount, checkUser);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        if (checkStatus == 2 && TextUtils.isEmpty(reason)) {
            mView.showToast("拒绝原因不能为空");
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("orderNo", orderNo);
        map.put("checkStatus", checkStatus);
        map.put("checkAmount", checkAmount);
        map.put("checkUser", checkUser);
        map.put("reason", reason);
        mView.showProgressDialog("提交中...");
        Disposable disposable = repository.checkWithdrawCashApply(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("审核成功");
                                break;

                            default:
                                mView.showToast("审核失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
