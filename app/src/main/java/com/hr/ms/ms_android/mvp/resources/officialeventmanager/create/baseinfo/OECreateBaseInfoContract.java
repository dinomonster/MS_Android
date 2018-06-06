package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.baseinfo;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface OECreateBaseInfoContract {
    interface View extends MvpView {
        void uploadImgSucess(String url);

        void uploadImgFail();

        void success(String id);

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {

        void addOfficialEvent(String officialEventTitle, String officialEventImg, String officialEventIntro, String officialEventNotice, Integer officialEventType, Long officialEventBeginDate, Long officialEventEndDate);

        void editOfficialEvent(String officialEventInfoId, String officialEventTitle, String officialEventImg, String officialEventIntro, String officialEventNotice, Long officialEventBeginDate, Long officialEventEndDate);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);
    }
}
