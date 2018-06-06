package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.sponsor.add;

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
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
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

public class AddSponsorPresenter extends BasePresenter implements AddSponsorContract.Presenter {
    private final AddSponsorContract.View mView;
    ServiceRepository repository;

    public AddSponsorPresenter(AddSponsorContract.View view,
                               ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }

    @Override
    public void addEventSponor(String officialEventInfoId, String sponsorId, String sponsorName, String sponsorLogo, String sponsorUrl, String freeNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"赞助商名称", "赞助商logo", "免费名额"},
                sponsorName, sponsorLogo, freeNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        map.put("sponsorId", sponsorId);
        map.put("sponsorName", sponsorName);
        map.put("sponsorLogo", sponsorLogo);
        ApiQueryMapUtils.addQueryMap("sponsorUrl", sponsorUrl, map);
        map.put("freeNum", freeNum);
        mView.showProgressDialog("保存赞助商信息...");
        Disposable disposable = repository.addEventSponor(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("赞助商信息保存成功");
                                mView.success("" + value.getData());
                                break;

                            default:
                                mView.showToast("赞助商信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("赞助商信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void editEventSponorInfo(String sponsorInfoId,String sponsorName, String sponsorLogo, String sponsorUrl, String freeNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"赞助商名称", "赞助商logo", "免费名额"},
                sponsorName, sponsorLogo, freeNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("sponsorInfoId", sponsorInfoId);
        map.put("sponsorName", sponsorName);
        map.put("sponsorLogo", sponsorLogo);
        ApiQueryMapUtils.addQueryMap("sponsorUrl", sponsorUrl, map);
        map.put("freeNum", freeNum);
        mView.showProgressDialog("保存赞助商信息...");
        Disposable disposable = repository.editEventSponorInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("赞助商信息保存成功");
                                mView.success("" + value.getData());
                                break;

                            default:
                                mView.showToast("赞助商信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("赞助商信息保存失败");
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
