package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.stageedit;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface StageEditContract {
    interface View extends MvpView {
        void uploadImgSucess(String url);

        void uploadImgFail();

        void editSucess();

        void createECardSucess();

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {
        void editStage(Integer stageId, String stageName, String stageLogo, String stageDesc, String stageAddress);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);

        void createECard(Integer stageId, String familyNum);
    }
}
