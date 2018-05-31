package com.hr.ms.ms_android.mvp.login;

import android.text.TextUtils;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.LoginUserBean;
import com.hr.ms.ms_android.data.AccountHelper;
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

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;

    ServiceRepository repository;

    public LoginPresenter(LoginContract.View view,
                          ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void login(final String username, final String password) {
        if(TextUtils.isEmpty(username)){
            mView.accountError("账号不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mView.pwdError("密码不能为空");
            return;
        }
        Map map = new HashMap();
        map.put("username",username);
        map.put("password",password);
        map.put("rememberMe","");
        mView.showProgressDialog("登录中...");
        Disposable disposable = repository.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<LoginUserBean>>(){
                    @Override
                    public void onNext(BaseResponseReturnValue<LoginUserBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()){
                            case ResponseCode.SUCCESS_CODE:
                                AccountHelper.login(value.getData());
                                AccountHelper.setAccountAndPwd(username,password);
                                mView.loginSuccess();
                                break;

                            default:
                                mView.loginFail();
                                mView.showToast("登录失败，请重试！");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.dismissProgressDialog();
                        mView.showToast("登录失败，请重试！");
                        mView.loginFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
