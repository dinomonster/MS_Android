package com.hr.ms.ms_android.mvp.operation.stagemanager.chooseusernext;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.UserSelectBean;
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

public class StageChooseUserPresenter extends BasePresenter implements StageChooseUserContract.Presenter {
    private final StageChooseUserContract.View mView;

    ServiceRepository repository;

    public StageChooseUserPresenter(StageChooseUserContract.View view,
                                    ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getUserList(int pageNo, Integer userType, String userName, String mobile) {
        Map<String, Object> map = new HashMap();
        map.put("userType", userType);
        map.put("userName", userName);
        map.put("mobile", mobile);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getUserList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<UserSelectBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<UserSelectBean> value) {
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
