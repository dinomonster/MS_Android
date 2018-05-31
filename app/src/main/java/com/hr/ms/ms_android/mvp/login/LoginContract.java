package com.hr.ms.ms_android.mvp.login;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface LoginContract {
    interface View extends MvpView {
        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         */
        void loginFail();

        /**
         * 账号输入错误
         * @param msg
         */
        void accountError(String msg);

        /**
         * 密码输入错误
         * @param msg
         */
        void pwdError(String msg);
    }

    interface Presenter extends MvpPresenter {
        /**
         * 登录
         * @param username
         * @param password
         */
        void login(String username,String password);
    }
}
