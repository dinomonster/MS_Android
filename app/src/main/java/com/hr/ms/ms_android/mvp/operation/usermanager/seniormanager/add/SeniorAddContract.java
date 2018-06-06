package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager.add;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SeniorAddContract {
    interface View extends MvpView {
        void uploadImgSucess(String url);

        void uploadImgFail();

        void success();

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {

        void setSenior(Integer accId, Integer seniorType, Integer seniorIdentity, Integer seniorField, String seniorTitle, String seniorIntro,String seniorImg,String seniorName);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);
    }
}
