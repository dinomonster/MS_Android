package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.CourseListBean;
import com.hr.ms.ms_android.bean.SubjectDetailBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SubjectDetailContract {
    interface View extends MvpView {
        void showDetail(SubjectDetailBean bean);
        void showList(List<CourseListBean.Lists> bean);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getTopicDetail(int topicId);
        void getLessonList(int pageNo, int topicId, String title, int auditStatus,int isBroadcast, String startTime, String endTime);
    }
}
