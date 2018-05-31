package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.edit;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SubjectEditContract {
    interface View extends MvpView {
        void uploadImgSucess(String url);

        void uploadImgFail();

        void editSucess();

        void showSetPermissionDialog(String s);

        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {
        void editTopic(int topicId, int anchorId, int topicType, String topicTitle, String topicImg, String topicDesc, String beginTime, String seriesPrice, int isLessonExtend, String gradePrice, int tagId);

        void addTopic(int topicFrom, int roomId, int anchorId, int topicType, String topicTitle, String topicImg, String topicDesc, String beginTime, String seriesPrice, int isLessonExtend, String gradePrice, int tagId);

        void checkExternalStoragePermission(Activity activity);

        void uploadImg(String path);
    }
}
