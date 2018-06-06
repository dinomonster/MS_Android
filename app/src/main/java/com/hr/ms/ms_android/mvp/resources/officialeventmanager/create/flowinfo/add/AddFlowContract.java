package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.add;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface AddFlowContract {
    interface View extends MvpView {
        void success(String id);
    }

    interface Presenter extends MvpPresenter {

        void addEventFlowPoints(String officialEventInfoId, Integer flowPointType, String flowOrderNum, Integer extra1, String extra2, Long flowBeginDate, Long flowEndDate);

        void editEventFlowPoints(String flowInfoId, Integer flowPointType, String flowOrderNum, Integer extra1, String extra2, Long flowBeginDate, Long flowEndDate);
    }
}
