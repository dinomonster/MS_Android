package com.hr.ms.ms_android.mvp.collegeroomchoose;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.CollegeRoomListBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface CollegeRoomListContract {
    interface View extends MvpView {
        void showList(List<CollegeRoomListBean.Lists> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getCollegeroomList(int pageNo, String name,String startTime,String endTime);
    }
}
