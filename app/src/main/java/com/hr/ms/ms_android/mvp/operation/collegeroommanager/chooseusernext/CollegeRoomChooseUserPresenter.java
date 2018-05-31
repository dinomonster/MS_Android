package com.hr.ms.ms_android.mvp.operation.collegeroommanager.chooseusernext;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.SeniorListBean;
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

public class CollegeRoomChooseUserPresenter extends BasePresenter implements CollegeRoomChooseUserContract.Presenter {
    private final CollegeRoomChooseUserContract.View mView;

    ServiceRepository repository;

    public CollegeRoomChooseUserPresenter(CollegeRoomChooseUserContract.View view,
                                          ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getSeniorList(int pageNo, Integer userIdentityType, String userName, String mobile) {
        Map<String, Object> map = new HashMap();
//        map.put("orderNo", orderNo);
        ApiQueryMapUtils.addQueryMap("userIdentityType", userIdentityType,map);
        ApiQueryMapUtils.addQueryMap("userName", userName,map);
        ApiQueryMapUtils.addQueryMap("mobile", mobile,map);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getSeniorList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<SeniorListBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<SeniorListBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showList(value.getData());
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
