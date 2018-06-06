package com.hr.ms.ms_android.mvp.operation.collegeroommanager.add;

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
import com.hr.ms.ms_android.bean.CollegeRoomDetailBean;
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

public class CollegeRoomPresenter extends BasePresenter implements CollegeRoomContract.Presenter {
    private final CollegeRoomContract.View mView;

    ServiceRepository repository;

    public CollegeRoomPresenter(CollegeRoomContract.View view,
                                ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getCollegeroomDetail(Integer collegeRoomId) {
        Map<String, Object> map = new HashMap();
        map.put("collegeRoomId", collegeRoomId);
        mView.showProgressDialog("获取详情中...");
        Disposable disposable = repository.getCollegeroomDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<CollegeRoomDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<CollegeRoomDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showCollegeRoomDetailBean(value.getData());
                                break;

                            default:
                                mView.showToast("获取详情失败");
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
    public void updateCollegeroom(Integer collegeRoomId, String name, String address, String img, String vipPrice, String vipDesc, String roomDesc) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"私塾头像", "私塾名称", "所在城市","私塾介绍"},
                img, name, address,roomDesc);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("collegeRoomId", collegeRoomId);
        map.put("name", name);
        map.put("address", address);
        map.put("img", img);
        map.put("vipPrice", vipPrice);
        map.put("vipDesc", vipDesc);
        map.put("roomDesc", roomDesc);
        mView.showProgressDialog("更新私塾详情中...");
        Disposable disposable = repository.updateCollegeroom(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:

                                break;

                            default:
                                mView.showToast("更新私塾详情失败");
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
    public void addCollegeroom(String name, Integer accId, String address, String img, String vipPrice, String vipDesc, String roomDesc) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"私塾头像", "私塾名称", "所在城市","私塾介绍"},
                img, name, address,roomDesc);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("name", name);
        map.put("accId", accId);
        map.put("address", address);
        map.put("img", img);
        map.put("vipPrice", vipPrice);
        map.put("vipDesc", vipDesc);
        map.put("roomDesc", roomDesc);
        mView.showProgressDialog("新增私塾中...");
        Disposable disposable = repository.addCollegeroom(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
//                                mView.showToast("新增私塾成功");
                                mView.success();
                                break;

                            default:
                                mView.showToast("新增私塾失败");
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
//                mView.uploadImgFail();
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
