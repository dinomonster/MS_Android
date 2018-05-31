package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.setscholar;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.UserDetailBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
import com.hr.ms.ms_android.network.ResponseCode;
import com.hr.ms.ms_android.utils.PresenterUtils;
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

public class SetScholarPresenter extends BasePresenter implements SetScholarContract.Presenter {
    private final SetScholarContract.View mView;
    private UserDetailBean bean;
    ServiceRepository repository;

    public SetScholarPresenter(SetScholarContract.View view,
                               ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void setAgentOrScholar(Integer accId, Integer roleType,String dateType,Integer stageId) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"年限"},
                dateType);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String,Object> map = new HashMap();
        ApiQueryMapUtils.addQueryMap("accId",accId,map);
        ApiQueryMapUtils.addQueryMap("roleType",roleType,map);
        ApiQueryMapUtils.addQueryMap("dateType",dateType,map);
        ApiQueryMapUtils.addQueryMap("stageId",stageId,map);
        mView.showProgressDialog("设置学霸...");
        Disposable disposable = repository.setAgentOrScholar(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>(){
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("设置学霸成功");
                                mView.success();
                                break;

                            default:
                                mView.showToast("设置学霸失败");
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
