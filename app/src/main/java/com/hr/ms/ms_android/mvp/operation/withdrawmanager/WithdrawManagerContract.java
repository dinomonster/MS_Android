package com.hr.ms.ms_android.mvp.operation.withdrawmanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.WithdrawCashBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface WithdrawManagerContract {
    interface View extends MvpView {
        void showList(List<WithdrawCashBean> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getWithdrawCashList(int pageNo, String orderNo, String userName, Integer withdrawCashMode, Integer checkStatus, Long ApplyTimeStart, Long ApplyTimeEnd, Integer sortRule);
    }
}
