package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.CourseListBean;
import com.hr.ms.ms_android.bean.SubjectDetailBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ResponseCode;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.hr.ms.ms_android.constants.CommonConstants.PAGESIZE;

/**
 * Created by Dino on 2018/4/3.
 */

public class SubjectDetailPresenter extends BasePresenter implements SubjectDetailContract.Presenter {
    private final SubjectDetailContract.View mView;

    ServiceRepository repository;

    public SubjectDetailPresenter(SubjectDetailContract.View view,
                                  ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getTopicDetail(int topicId) {
        Map<String, Object> map = new HashMap();
        map.put("topicId", topicId);
        Disposable disposable = repository.getTopicDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<SubjectDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<SubjectDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showDetail(value.getData());
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

    @Override
    public void getLessonList(int pageNo, int topicId, String title, int auditStatus,int isBroadcast, String startTime, String endTime) {
        Map<String, Object> map = new HashMap();
        map.put("topicId", topicId);
        map.put("title", title);
        map.put("auditStatus", auditStatus);
        map.put("isBroadcast", isBroadcast);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getLessonList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<CourseListBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<CourseListBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showList(value.getData().getLists());
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
