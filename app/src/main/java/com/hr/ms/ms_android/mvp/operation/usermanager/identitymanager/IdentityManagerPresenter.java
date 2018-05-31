package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.IdentityBeanList;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
import com.hr.ms.ms_android.network.ResponseCode;
import com.hr.ms.ms_android.utils.CodeStringUtils;
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

public class IdentityManagerPresenter extends BasePresenter implements IdentityManagerContract.Presenter {
    private final IdentityManagerContract.View mView;

    ServiceRepository repository;

    public IdentityManagerPresenter(IdentityManagerContract.View view,
                                    ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getIdentityManageList(int pageNo, String userName,String mobile) {
        Map<String,Object> map = new HashMap();
        ApiQueryMapUtils.addQueryMap("userName",userName,map);
        ApiQueryMapUtils.addQueryMap("mobile",mobile,map);
        map.put("size",PAGESIZE);
        map.put("page",pageNo);
        Disposable disposable = repository.getIdentityManageList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<IdentityBeanList>>(){
                    @Override
                    public void onNext(BaseResponseReturnValue<IdentityBeanList> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()){
                            case ResponseCode.SUCCESS_CODE:
                                mView.showList(value.getData().getLists());
                                break;

                            default:
                                mView.showNetWorkError();
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
    public void cancelAgentOrScholar(Integer accId, Integer roleType) {
        Map<String,Object> map = new HashMap();
        ApiQueryMapUtils.addQueryMap("accId",accId,map);
        ApiQueryMapUtils.addQueryMap("roleType",roleType,map);
        if(roleType == CodeStringUtils.INSTANCE.getRoleTypeCode().get(0)){
            mView.showProgressDialog("取消代言人中...");
        }else if(roleType == CodeStringUtils.INSTANCE.getRoleTypeCode().get(1)){
            mView.showProgressDialog("取消学霸中...");
        }
        Disposable disposable = repository.cancelAgentOrScholar(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>(){
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        mView.dismissProgressDialog();
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()){
                            case ResponseCode.SUCCESS_CODE:
                                mView.editSucess();
                                break;

                            default:
                                mView.showToast("设置失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showToast("网络错误");
                        mView.dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void editAccountStage(int accId, String stageCode) {
        Map<String,Object> map = new HashMap();
        map.put("accId",accId);
        map.put("stageCode",stageCode);
        Disposable disposable = repository.editAccountStage(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>(){
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()){
                            case ResponseCode.SUCCESS_CODE:
                                mView.editSucess();
                                break;

                            default:
                                mView.showNetWorkError();
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
