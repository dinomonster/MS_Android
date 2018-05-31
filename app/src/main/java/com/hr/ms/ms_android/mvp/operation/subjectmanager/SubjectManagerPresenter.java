package com.hr.ms.ms_android.mvp.operation.subjectmanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.SubjectListBean;
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

public class SubjectManagerPresenter extends BasePresenter implements SubjectManagerContract.Presenter {
    private final SubjectManagerContract.View mView;

    ServiceRepository repository;

    public SubjectManagerPresenter(SubjectManagerContract.View view,
                                   ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getTopicList(int pageNo, int topicType, int topicFrom, String topicTitle, String startTime, String endTime) {
        Map<String,Object> map = new HashMap();
        map.put("topicType", topicType);
        map.put("topicFrom", topicFrom);
        map.put("topicTitle", topicTitle);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getTopicList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<SubjectListBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<SubjectListBean> value) {
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
