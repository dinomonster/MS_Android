package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager.chooseusernext;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.UserSelectBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SeniorChooseUserContract {
    interface View extends MvpView {
        void showList(List<UserSelectBean.Lists> list);

        void showNetWorkError();

        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getUserList(int pageNo, Integer userType, String memberName, String mobile);
    }
}
