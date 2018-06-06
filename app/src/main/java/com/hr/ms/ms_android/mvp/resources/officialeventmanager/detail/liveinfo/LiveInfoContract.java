package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.OfficialEventDetailBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface LiveInfoContract {
    interface View extends MvpView {
        void showDetail(OfficialEventDetailBean bean);

        void saveSuccess();

        void deleteSuccess();
    }

    interface Presenter extends MvpPresenter {
        void getOfficialEventDetail(String officialEventInfoId);

        void editEventLiveInfo(String liveInfoId, String chatroomMsg, String liveBookNum, String liveWatchNum);

        void addEventLiveInfo(String officialEventInfoId, String chatroomMsg, String liveBookNum, String liveWatchNum);

        void deleteImManagerInfo(String imManagerInfoId);
    }
}
