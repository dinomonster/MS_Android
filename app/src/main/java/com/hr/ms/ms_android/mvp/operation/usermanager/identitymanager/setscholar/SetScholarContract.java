package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.setscholar;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SetScholarContract {
    interface View extends MvpView {
        void success();
    }

    interface Presenter extends MvpPresenter {
        void setAgentOrScholar(Integer accId, Integer roleType,String dateType,Integer stageId);
    }
}
