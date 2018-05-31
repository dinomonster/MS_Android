package com.hr.ms.ms_android.mvp.main;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

/**
 * Created by Dino on 2018/4/3.
 */

public interface MainContract {
    interface View extends MvpView {
    }

    interface Presenter extends MvpPresenter {
        void getUploadAuth();
    }
}
