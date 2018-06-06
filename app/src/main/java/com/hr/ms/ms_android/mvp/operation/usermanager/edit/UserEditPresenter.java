package com.hr.ms.ms_android.mvp.operation.usermanager.edit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.UserDetailBean;
import com.hr.ms.ms_android.data.AccountHelper;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
import com.hr.ms.ms_android.utils.AliOSSUtils;
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

public class UserEditPresenter extends BasePresenter implements UserEditContract.Presenter {
    private final UserEditContract.View mView;
    private UserDetailBean bean;
    ServiceRepository repository;

    public UserEditPresenter(UserEditContract.View view,
                             ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getAccountVoInfo(int accId) {
        Map<String, Object> map = new HashMap();
        map.put("accId", accId);
        mView.showProgressDialog("获取用户详情...");
        Disposable disposable = repository.getAccountVoInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<UserDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<UserDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showData(bean = value.getData());
                                break;

                            default:
                                mView.showToast("获取用户详情失败");
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

    @Override
    public void editAccount(int role, int status, String memberName, String imageUri, String email, String introduction, int sex, String birth, String motto, int identityId, int fieldId, String company, String title) {
        if (bean == null) return;
        Map<String, Object> map = new HashMap();
        map.put("id", bean.getAccId());
        map.put("role", role);
        map.put("status", status);
        map.put("memberName", memberName);
        map.put("imageUri", imageUri);
        map.put("email", email);
        map.put("introduction", introduction);
        map.put("sex", sex);
        map.put("birth", birth);
        map.put("motto", motto);
        map.put("identityId", identityId);
        map.put("fieldId", fieldId);
        map.put("company", company);
        map.put("title", title);
//        mView.showProgressDialog("修改用户详情...");
        Disposable disposable = repository.editAccount(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.editSucess();
                                mView.showToast("修改用户详情成功");
                                break;

                            default:
                                mView.showToast("修改用户详情失败");
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

    @Override
    public void uploadImg(String path) {
        if (bean == null) return;
        mView.showProgressDialog("修改用户详情...");
        if (TextUtils.isEmpty(path)) {
            mView.uploadImgSucess(bean.getImageUri());
            return;
        }
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
