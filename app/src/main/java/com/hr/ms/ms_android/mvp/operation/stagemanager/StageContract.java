package com.hr.ms.ms_android.mvp.operation.stagemanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.StageListBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface StageContract {
    interface View extends MvpView {
        void showList(StageListBean list);

        void showNetWorkError();

        void showNoData();

        void createECardSucess();
    }

    interface Presenter extends MvpPresenter {
        void getStageIndexList(int pageNo, Integer stageType, String stageName, Long createTimeStart, Long createTimeEnd, Integer orderRule);

        void createECard(Integer stageId, String familyNum);
    }
}
