package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.signupinfo;

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

public class SignupInfoPresenter extends BasePresenter implements SignupInfoContract.Presenter {
    private final SignupInfoContract.View mView;
    ServiceRepository repository;

    public SignupInfoPresenter(SignupInfoContract.View view,
                               ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }

    @Override
    public void editEventOfflineInfo(String offlineInfoId, Long attendanceDate, String activityAddress, String detailAddress, String contactName, String contactTel, String longitude, String latitude, String socialNum, String schoolNum, String price) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"签到时间", "活动地址", "详细地址", "联系人", "联系电话", "社会名额", "学生名额", "报名费用"},
                attendanceDate, activityAddress, detailAddress, contactName, contactTel, socialNum, schoolNum, price);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("offlineInfoId", offlineInfoId);
        map.put("attendanceDate", attendanceDate);
        map.put("activityAddress", activityAddress);
        map.put("detailAddress", detailAddress);
        map.put("contactName", contactName);
        map.put("contactTel", contactTel);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("socialNum", socialNum);
        map.put("schoolNum", schoolNum);
        map.put("price", price);
        mView.showProgressDialog("保存报名信息...");
        Disposable disposable = repository.editEventOfflineInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("报名信息保存成功");
                                mView.success();
                                break;

                            default:
                                mView.showToast("报名信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("报名信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void addEventOfflineInfo(String officialEventInfoId, Long attendanceDate, String activityAddress, String detailAddress, String contactName, String contactTel, String longitude, String latitude, String socialNum, String schoolNum, String price) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"签到时间", "活动地址", "详细地址", "联系人", "联系电话", "社会名额", "学生名额", "报名费用"},
                attendanceDate, activityAddress, detailAddress, contactName, contactTel, socialNum, schoolNum, price);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        map.put("attendanceDate", attendanceDate);
        map.put("activityAddress", activityAddress);
        map.put("detailAddress", detailAddress);
        map.put("contactName", contactName);
        map.put("contactTel", contactTel);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("socialNum", socialNum);
        map.put("schoolNum", schoolNum);
        map.put("price", price);
        mView.showProgressDialog("保存报名信息...");
        Disposable disposable = repository.addEventOfflineInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("报名信息保存成功");
                                mView.success();
                                break;

                            default:
                                mView.showToast("报名信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("报名信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
