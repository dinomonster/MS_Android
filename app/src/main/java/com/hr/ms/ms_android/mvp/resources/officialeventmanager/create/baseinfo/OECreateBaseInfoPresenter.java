package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.baseinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.data.AccountHelper;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
import com.hr.ms.ms_android.utils.AliOSSUtils;
import com.hr.ms.ms_android.utils.PresenterUtils;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dino on 2018/4/3.
 */

public class OECreateBaseInfoPresenter extends BasePresenter implements OECreateBaseInfoContract.Presenter {
    private final OECreateBaseInfoContract.View mView;
    ServiceRepository repository;

    public OECreateBaseInfoPresenter(OECreateBaseInfoContract.View view,
                                     ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }

    @Override
    public void addOfficialEvent(String officialEventTitle, String officialEventImg, String officialEventIntro, String officialEventNotice, Integer officialEventType, Long officialEventBeginDate, Long officialEventEndDate) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"活动名称", "活动图", "活动介绍", "温馨提示", "开始时间","结束时间"},
                officialEventTitle, officialEventImg, officialEventIntro, officialEventNotice,officialEventBeginDate,officialEventEndDate);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventTitle", officialEventTitle);
        map.put("officialEventImg", officialEventImg);
        map.put("officialEventIntro", officialEventIntro);
        map.put("officialEventNotice", officialEventNotice);
        map.put("officialEventType", officialEventType);
        map.put("officialEventBeginDate", officialEventBeginDate);
        map.put("officialEventEndDate", officialEventEndDate);
        mView.showProgressDialog("保存基本信息...");
        Disposable disposable = repository.addOfficialEvent(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("基本信息保存成功");
                                mView.success(""+value.getData());
                                break;

                            default:
                                mView.showToast("基本信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("基本信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void editOfficialEvent(String officialEventInfoId,String officialEventTitle, String officialEventImg, String officialEventIntro, String officialEventNotice, Long officialEventBeginDate, Long officialEventEndDate) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"活动名称", "活动图", "活动介绍", "温馨提示", "开始时间","结束时间"},
                officialEventTitle, officialEventImg, officialEventIntro, officialEventNotice,officialEventBeginDate,officialEventEndDate);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        map.put("officialEventTitle", officialEventTitle);
        map.put("officialEventImg", officialEventImg);
        map.put("officialEventIntro", officialEventIntro);
        map.put("officialEventNotice", officialEventNotice);
        map.put("officialEventBeginDate", officialEventBeginDate);
        map.put("officialEventEndDate", officialEventEndDate);
        mView.showProgressDialog("保存基本信息...");
        Disposable disposable = repository.editOfficialEvent(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("基本信息保存成功");
                                mView.success(""+value.getData());
                                break;

                            default:
                                mView.showToast("基本信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("基本信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }


    @Override
    public void uploadImg(String path) {
        final AliOSSUtils aliOSSUtils = new AliOSSUtils((Context) mView);
        final String url = AccountHelper.getUser().getUserId() + "/" + System.currentTimeMillis() + "userimg.jpg";
        aliOSSUtils.upLoadImageViewCallBack(url, path, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                mView.uploadImgSucess(aliOSSUtils.getBaseUrl()+url);
                KLog.e(new Gson().toJson(request));
                KLog.e(new Gson().toJson(result));
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                mView.dismissProgressDialog();
                mView.showToast("图片上传失败");
                mView.uploadImgFail();
            }
        });
    }

    @Override
    public void checkExternalStoragePermission(Activity activity) {
        String externalStoragePermissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(externalStoragePermissions)
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean value) {
                        if (value) {
                            mView.startSelectPic();
                        } else {
                            mView.showSetPermissionDialog("读写");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("检测限抛异常了：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
