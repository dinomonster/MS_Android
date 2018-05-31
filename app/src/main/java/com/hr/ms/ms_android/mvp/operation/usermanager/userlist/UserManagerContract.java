package com.hr.ms.ms_android.mvp.operation.usermanager.userlist;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.UserListBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface UserManagerContract {
    interface View extends MvpView {
        void showList(List<UserListBean.Lists> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getAccount(int pageNo, Integer userIdentityType, String userName, String mobile, Long createTimeStart, Long createTimeEnd);
    }
}
