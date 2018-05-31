package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.courseedit;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface CourseEditContract {
    interface View extends MvpView {
        void editSucess();
    }

    interface Presenter extends MvpPresenter {
        void addLesson(int topicId, String title, String beginDate, String remindTime, String mediaUrl, String highUrl, String details);

        void editLesson(int lsnId, String title, String beginDate, int curStatus, String remindTime, int isBroadcast, String videoId, String hourLong, String mediaUrl, String highUrl, String details);
    }
}
