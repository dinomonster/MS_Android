package com.hr.ms.ms_android.mvp.stagechoose;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.StageSelectListBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface StageChooseContract {
    interface View extends MvpView {
        void showList(List<StageSelectListBean.Lists> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getPersonalCityStageList(int pageNo, String stageName);
    }
}
