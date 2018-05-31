package com.hr.ms.ms_android.mvp.operation.withdrawmanager.detail;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.WithdrawApplyBean;
import com.hr.ms.ms_android.bean.WithdrawCashCheckBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface WithdrawDetailContract {
    interface View extends MvpView {
        void showWithdrawApplyBean(WithdrawApplyBean bean);

        void showWithdrawCashCheckBean(WithdrawCashCheckBean bean);

    }

    interface Presenter extends MvpPresenter {
        void getWithdrawCashApplyDetails(String orderNo);

        void getWithdrawCashCheckDetails(String orderNo);

        void checkWithdrawCashApply(String orderNo,Integer checkStatus,String checkAmount,String checkUser,String reason);
    }
}
