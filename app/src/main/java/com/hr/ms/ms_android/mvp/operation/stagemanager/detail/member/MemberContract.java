package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.member;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.AgentOrScholarBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface MemberContract {
    interface View extends MvpView {
        void showList(AgentOrScholarBean list);

        void showNetWorkError();

        void showNoData();

    }

    interface Presenter extends MvpPresenter {
        void getAgentOrScholarList(int pageNo,Integer stageId, Integer userRoleType, String userName, Integer orderRule);
    }
}
