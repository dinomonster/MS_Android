package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.courseedit;

import android.text.TextUtils;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
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

public class CourseEditPresenter extends BasePresenter implements CourseEditContract.Presenter {
    private final CourseEditContract.View mView;
    ServiceRepository repository;

    public CourseEditPresenter(CourseEditContract.View view,
                               ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void addLesson(int topicId, String title, String beginDate, String remindTime, String mediaUrl, String highUrl, String details) {
        if (TextUtils.isEmpty(title)) {
            mView.showToast("请填写课程标题");
            return;
        }
        if (TextUtils.isEmpty(beginDate)) {
            mView.showToast("请选择开始时间");
            return;
        }
        if (TextUtils.isEmpty(remindTime)) {
            mView.showToast("请填写课前提醒时间");
            return;
        }
        if (TextUtils.isEmpty(mediaUrl)) {
            mView.showToast("请填写标清播放地址");
            return;
        }
        if (TextUtils.isEmpty(highUrl)) {
            mView.showToast("请填写高清播放地址");
            return;
        }
        if (TextUtils.isEmpty(details)) {
            mView.showToast("请填写文本内容");
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("topicId", topicId);
        map.put("title", title);
        map.put("img", "");
        map.put("beginDate", beginDate);
        map.put("curStatus", 2);
        map.put("remindTime", remindTime);
        map.put("isBroadcast", 1);
        map.put("videoId", "");
        map.put("hourLong", "");
        map.put("mediaUrl", mediaUrl);
        map.put("highUrl", highUrl);
        map.put("details", details);
        map.put("sortNo", 0);
        mView.showProgressDialog("新增课程详情...");
        Disposable disposable = repository.addLesson(map)
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
                                mView.showToast("新增课程成功");
                                break;

                            default:
                                mView.showToast("新增课程失败");
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
    public void editLesson(int lsnId, String title, String beginDate, int curStatus, String remindTime, int isBroadcast, String videoId, String hourLong, String mediaUrl, String highUrl, String details) {
        if (TextUtils.isEmpty(title)) {
            mView.showToast("请填写课程标题");
            return;
        }
        if (TextUtils.isEmpty(beginDate)) {
            mView.showToast("请选择开始时间");
            return;
        }
        if (curStatus == -1) {
            mView.showToast("请选择课程状态");
            return;
        }
        if (TextUtils.isEmpty(remindTime)) {
            mView.showToast("请填写课前提醒时间");
            return;
        }
        if (isBroadcast == -1) {
            mView.showToast("请选择是否录播");
            return;
        }
        if (TextUtils.isEmpty(mediaUrl)) {
            mView.showToast("请填写标清播放地址");
            return;
        }
        if (TextUtils.isEmpty(highUrl)) {
            mView.showToast("请填写高清播放地址");
            return;
        }
        if (TextUtils.isEmpty(details)) {
            mView.showToast("请填写文本内容");
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("lsnId", lsnId);
        map.put("title", title);
        map.put("img", "");
        map.put("beginDate", beginDate);
        map.put("curStatus", curStatus);
        map.put("remindTime", remindTime);
        map.put("isBroadcast", isBroadcast);
        map.put("videoId", videoId);
        map.put("hourLong", hourLong);
        map.put("mediaUrl", mediaUrl);
        map.put("highUrl", highUrl);
        map.put("details", details);
        map.put("sortNo", 0);
        mView.showProgressDialog("修改课程详情...");
        Disposable disposable = repository.editLesson(map)
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
                                mView.showToast("修改课程详情成功");
                                break;

                            default:
                                mView.showToast("修改课程详情失败");
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

}
