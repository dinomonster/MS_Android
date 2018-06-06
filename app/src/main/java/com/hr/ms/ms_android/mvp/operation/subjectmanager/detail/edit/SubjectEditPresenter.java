package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.edit;

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

public class SubjectEditPresenter extends BasePresenter implements SubjectEditContract.Presenter {
    private final SubjectEditContract.View mView;
    ServiceRepository repository;

    public SubjectEditPresenter(SubjectEditContract.View view,
                                ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void addTopic(int topicFrom, int roomId, int anchorId, int topicType, String topicTitle, String topicImg, String topicDesc, String beginTime, String seriesPrice, int isLessonExtend, String gradePrice, int tagId) {
        if(topicFrom == -1){
            mView.dismissProgressDialog();
            mView.showToast("请选择课题来源");
            return;
        }
        if(roomId == -1){
            mView.dismissProgressDialog();
            mView.showToast("请选择私塾");
            return;
        }
        if(anchorId == -1){
            mView.dismissProgressDialog();
            mView.showToast("请选择主讲人");
            return;
        }
        if(topicType == -1){
            mView.showToast("请选择课题类型");
            return;
        }
        if(TextUtils.isEmpty(topicTitle)){
            mView.showToast("请填写课题标题");
            return;
        }
        if(TextUtils.isEmpty(topicImg)){
            mView.showToast("请上传课题封面");
            return;
        }
        if(TextUtils.isEmpty(topicDesc)){
            mView.showToast("请填写课题描述");
            return;
        }
        if(TextUtils.isEmpty(beginTime)){
            mView.showToast("请选择开始时间");
            return;
        }
        if(TextUtils.isEmpty(seriesPrice)){
            mView.showToast("请填写课题价格");
            return;
        }
        if(isLessonExtend == -1){
            mView.showToast("请选择是否推至课程库");
            return;
        }
        if(isLessonExtend == 1){
            if(TextUtils.isEmpty(gradePrice)){
                mView.showToast("请填写课程库价格");
                return;
            }
        }
        if(tagId == -1){
            mView.showToast("请选择课题标签");
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("topicFrom", topicFrom);
        map.put("roomId", roomId);
        map.put("anchorId", anchorId);
        map.put("topicType", topicType);
        map.put("topicTitle", topicTitle);
        map.put("topicImg", topicImg);
        map.put("topicDesc", topicDesc);
        map.put("beginTime", beginTime);
        map.put("seriesPrice", seriesPrice);
        map.put("isLessonExtend", isLessonExtend);
        map.put("gradePrice", gradePrice);
        map.put("tagId", tagId);
        mView.showProgressDialog("新增课题详情...");
        Disposable disposable = repository.addTopic(map)
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
                                mView.showToast("新增课题成功");
                                break;

                            default:
                                mView.showToast("新增课题失败");
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
    public void editTopic(int topicId, int anchorId, int topicType, String topicTitle, String topicImg, String topicDesc, String beginTime, String seriesPrice, int isLessonExtend, String gradePrice, int tagId) {
        if(anchorId == -1){
            mView.dismissProgressDialog();
            mView.showToast("请选择主讲人");
            return;
        }
        if(topicType == -1){
            mView.showToast("请选择课题类型");
            return;
        }
        if(TextUtils.isEmpty(topicTitle)){
            mView.showToast("请填写课题标题");
            return;
        }
        if(TextUtils.isEmpty(topicImg)){
            mView.showToast("请上传课题封面");
            return;
        }
        if(TextUtils.isEmpty(topicDesc)){
            mView.showToast("请填写课题描述");
            return;
        }
        if(TextUtils.isEmpty(beginTime)){
            mView.showToast("请选择开始时间");
            return;
        }
        if(TextUtils.isEmpty(seriesPrice)){
            mView.showToast("请填写课题价格");
            return;
        }
        if(isLessonExtend == -1){
            mView.showToast("请选择是否推至课程库");
            return;
        }
        if(isLessonExtend == 1){
            if(TextUtils.isEmpty(gradePrice)){
                mView.showToast("请填写课程库价格");
                return;
            }
        }
        if(tagId == -1){
            mView.showToast("请选择课题标签");
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("topicId", topicId);
        map.put("anchorId", anchorId);
        map.put("topicType", topicType);
        map.put("topicTitle", topicTitle);
        map.put("topicImg", topicImg);
        map.put("topicDesc", topicDesc);
        map.put("beginTime", beginTime);
        map.put("seriesPrice", seriesPrice);
        map.put("isLessonExtend", isLessonExtend);
        map.put("gradePrice", gradePrice);
        map.put("tagId", tagId);
        mView.showProgressDialog("修改课题详情...");
        Disposable disposable = repository.editTopic(map)
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
                                mView.showToast("修改课题详情成功");
                                break;

                            default:
                                mView.showToast("修改课题详情失败");
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
