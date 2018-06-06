package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.sponsor.add;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface AddSponsorContract {
    interface View extends MvpView {
        void success(String id);

        void uploadImgSucess(String url);

        void uploadImgFail();

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {
        void editEventSponorInfo(String sponsorInfoId, String sponsorName, String sponsorLogo, String sponsorUrl, String freeNum);

        void addEventSponor(String officialEventInfoId, String sponsorId, String sponsorName, String sponsorLogo, String sponsorUrl, String freeNum);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);
    }
}
