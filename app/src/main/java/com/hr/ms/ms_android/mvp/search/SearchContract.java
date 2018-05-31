package com.hr.ms.ms_android.mvp.search;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;

import java.util.List;

/**
 * 公共模块
 * Created by Dino on 2018/4/3.
 */

public interface SearchContract {
    interface View extends MvpView {
        /**
         * 显示所有搜索历史
         *
         * @param list
         */
        void showSearchHistory(List<String> list);

        void refresh();
    }

    interface Presenter extends MvpPresenter {
        /**
         * 根据模块类型来查找搜索历史
         *
         * @param type
         */
        void getSearchHistory(String type);

        /**
         * 根据模块类型来保存搜索历史
         *
         * @param type
         */
        void saveSearchHistory(String type, String key);

        /**
         * 根据模块类型来删除单个搜索历史
         *
         * @param type
         */
        void removeSearchHistory(String type, String key);

        /**
         * 根据模块类型来删除所有搜索历史
         *
         * @param type
         */
        void removeAllSearchHistory(String type);
    }
}
