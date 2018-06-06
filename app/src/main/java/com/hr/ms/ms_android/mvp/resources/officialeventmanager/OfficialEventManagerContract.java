package com.hr.ms.ms_android.mvp.resources.officialeventmanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.OfficialEventListBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface OfficialEventManagerContract {
    interface View extends MvpView {
        void showList(OfficialEventListBean list);

        void showNetWorkError();

        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getOfficialEventList(int pageNo, String eventTitle, Integer eventStatus, Integer eventType, Long eventBeginDateStart, Long eventBeginDateEnd, Integer orderRule);
    }
}
