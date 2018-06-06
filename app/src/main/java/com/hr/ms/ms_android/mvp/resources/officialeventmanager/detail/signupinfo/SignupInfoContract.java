package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.signupinfo;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SignupInfoContract {
    interface View extends MvpView {

        void success();
    }

    interface Presenter extends MvpPresenter {

        void editEventOfflineInfo(String offlineInfoId, Long attendanceDate, String activityAddress, String detailAddress, String contactName, String contactTel, String longitude, String latitude, String socialNum, String schoolNum, String price);

        void addEventOfflineInfo(String offlineInfoId, Long attendanceDate, String activityAddress, String detailAddress, String contactName, String contactTel, String longitude, String latitude, String socialNum, String schoolNum, String price);
    }
}
