package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer.add;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface AddCoorganizerContract {
    interface View extends MvpView {
        void success(String id);

        void uploadImgSucess(String url);

        void uploadImgFail();

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {
        void addEventCoorganizer(String officialEventInfoId, String coorganizerId, String coorganizerName, String coorganizerLogo, String coorganizerUrl, String freeNum);

        void editEventCoorganizer(String coorganizerInfoId, String coorganizerName, String coorganizerLogo, String coorganizerUrl, String freeNum);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);
    }
}
