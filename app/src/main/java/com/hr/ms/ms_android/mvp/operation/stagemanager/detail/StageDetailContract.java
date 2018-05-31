package com.hr.ms.ms_android.mvp.operation.stagemanager.detail;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.StageDetailBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface StageDetailContract {
    interface View extends MvpView {
        void showDetail(StageDetailBean bean);
    }

    interface Presenter extends MvpPresenter {
        void getStageDetail(Integer stageId);
    }
}
