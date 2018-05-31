package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.SeniorListBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SeniorManagerContract {
    interface View extends MvpView {
        void showList(SeniorListBean list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getSeniorList(int pageNo, Integer userIdentityType, String userName, String mobile);
    }
}
