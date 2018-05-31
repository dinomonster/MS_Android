package com.hr.ms.ms_android.mvp.tagmanager;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.TagListBean;

import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public interface TagManagerContract {
    interface View extends MvpView {
        void showList(List<TagListBean.Lists> list);
        void showNetWorkError();
        void showNoData();
    }

    interface Presenter extends MvpPresenter {
        void getTagList(int pageNo, int type, String tagName);
    }
}
