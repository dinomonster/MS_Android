package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo.immanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface ImManagerContract {
    interface View extends MvpView {

        void success();
    }

    interface Presenter extends MvpPresenter {

        void addEventImManager(String officialEventInfoId, String imManagerId, Integer imManagerType, String freeNum);

        void editEventImManager(String imManagerInfoId, Integer imManagerType, String freeNum);
    }
}
