package com.hr.ms.ms_android.mvp.operation.stagemanager.add;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.AddStageResponseBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface StageAddContract {
    interface View extends MvpView {
        void uploadImgSucess(String url);

        void uploadImgFail();

        void addSuccess(AddStageResponseBean bean);

        void editSucess();

        void createECardSucess();

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {

        void addStage(Integer accId, Integer stageType);

        void editStage(Integer stageId, String stageName, String stageLogo, String stageDesc, String stageAddress);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);

        void createECard(Integer accId, String stageCode, String familyNum);
    }
}
