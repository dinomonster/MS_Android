package com.hr.ms.ms_android.mvp.operation.collegeroommanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.CollegeRoomListBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
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

public class CollegeRoomManagerPresenter extends BasePresenter implements CollegeRoomManagerContract.Presenter {
    private final CollegeRoomManagerContract.View mView;

    ServiceRepository repository;

    public CollegeRoomManagerPresenter(CollegeRoomManagerContract.View view,
                                       ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getCollegeroomList(int pageNo, String collegeRoomName, Long createTimeStart, Long createTimeEnd) {
        Map<String, Object> map = new HashMap();
        ApiQueryMapUtils.addQueryMap("collegeRoomName", collegeRoomName, map);
        ApiQueryMapUtils.addQueryMap("createTimeStart", createTimeStart, map);
        ApiQueryMapUtils.addQueryMap("createTimeEnd", createTimeEnd, map);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getCollegeroomList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<CollegeRoomListBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<CollegeRoomListBean> value) {
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
