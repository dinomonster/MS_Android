package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager.add;

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

public class SeniorAddPresenter extends BasePresenter implements SeniorAddContract.Presenter {
    private final SeniorAddContract.View mView;
    ServiceRepository repository;

    public SeniorAddPresenter(SeniorAddContract.View view,
                              ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }

    @Override
    public void setSenior(Integer accId, Integer seniorType, Integer seniorIdentity, Integer seniorField, String seniorTitle, String seniorIntro,String seniorImg,String seniorName) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"导师类型", "导师身份","研究领域", "个人头衔", "个人简介","头像","用户名"},
                seniorType, seniorIdentity,seniorField,seniorTitle,seniorIntro,seniorImg,seniorName);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("accId", accId);
        map.put("seniorType", seniorType);
        map.put("seniorIdentity", seniorIdentity);
        map.put("seniorField", seniorField);
        map.put("seniorTitle", seniorTitle);
        map.put("seniorIntro", seniorIntro);
        map.put("seniorImg", seniorImg);
        map.put("seniorName", seniorName);
        mView.showProgressDialog("开通导师...");
        Disposable disposable = repository.setSenior(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.success();
                                break;

                            default:
                                mView.showToast("开通导师失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("开通导师失败");
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
