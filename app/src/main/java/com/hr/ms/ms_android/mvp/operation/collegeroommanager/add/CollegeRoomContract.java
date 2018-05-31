package com.hr.ms.ms_android.mvp.operation.collegeroommanager.add;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.CollegeRoomDetailBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface CollegeRoomContract {
    interface View extends MvpView {
        void showCollegeRoomDetailBean(CollegeRoomDetailBean bean);

        void uploadImgSucess(String url);

        void success();

        void showSetPermissionDialog(String s);

        void startSelectPic();

        void uploadImgFail();
    }

    interface Presenter extends MvpPresenter {
        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);

        void getCollegeroomDetail(Integer collegeRoomId);

        void updateCollegeroom(Integer collegeRoomId, String name, String address, String img, String vipPrice, String vipDesc, String roomDesc);

        void addCollegeroom(String name, Integer accId, String address, String img, String vipPrice, String vipDesc, String roomDesc);
    }
}
