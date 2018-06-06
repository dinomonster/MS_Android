package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo.activitycard;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.CardActiveDetailBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface ActivityCardContract {
    interface View extends MvpView {
        void showList(CardActiveDetailBean list);

        void showNetWorkError();

        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getCardActiveDetail(int pageNo, Integer stageId, Integer cardType);
    }
}
