package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.OfficialEventDetailBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface OfficialEventDetailContract {
    interface View extends MvpView {
        void showDetail(OfficialEventDetailBean bean);
    }

    interface Presenter extends MvpPresenter {
        void getOfficialEventDetail(String officialEventInfoId);
    }
}
