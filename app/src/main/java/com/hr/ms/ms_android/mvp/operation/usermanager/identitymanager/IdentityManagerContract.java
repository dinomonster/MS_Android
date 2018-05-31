package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.IdentityBeanList;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface IdentityManagerContract {
    interface View extends MvpView {
        void showList(List<IdentityBeanList.Lists> list);
        void showNetWorkError();
        void showNoData();
        void editSucess();
    }

    interface Presenter extends MvpPresenter {
        void getIdentityManageList(int pageNo, String memberName,String mobile);
        void cancelAgentOrScholar(Integer accId, Integer roleType);
        void editAccountStage(int accId, String stageCode);
    }
}
