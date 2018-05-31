package com.hr.ms.ms_android.mvp.userchoose;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.UserSelectBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface UserChooseContract {
    interface View extends MvpView {
        void showList(List<UserSelectBean.Lists> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getUserList(int pageNo, String memberName,String mobile);
    }
}
