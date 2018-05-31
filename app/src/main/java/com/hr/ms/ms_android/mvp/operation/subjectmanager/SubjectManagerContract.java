package com.hr.ms.ms_android.mvp.operation.subjectmanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.SubjectListBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface SubjectManagerContract {
    interface View extends MvpView {
        void showList(List<SubjectListBean.Lists> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getTopicList(int pageNo, int topicType, int topicFrom, String topicTitle, String startTime, String endTime);
    }
}
