package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.member;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.AgentOrScholarBean;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
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

public class MemberPresenter extends BasePresenter implements MemberContract.Presenter {
    private final MemberContract.View mView;

    ServiceRepository repository;

    public MemberPresenter(MemberContract.View view,
                           ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getAgentOrScholarList(int pageNo,Integer stageId, Integer userRoleType, String userName, Integer orderRule) {
        Map<String, Object> map = new HashMap();
        ApiQueryMapUtils.addQueryMap("stageId", stageId, map);
        ApiQueryMapUtils.addQueryMap("userRoleType", userRoleType, map);
        ApiQueryMapUtils.addQueryMap("userName", userName, map);
        ApiQueryMapUtils.addQueryMap("orderRule", orderRule, map);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getAgentOrScholarList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<AgentOrScholarBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<AgentOrScholarBean> value) {
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
