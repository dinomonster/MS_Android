package com.hr.ms.ms_android.mvp.search;

import android.text.TextUtils;

import com.better.appbase.mvp.BasePresenter;
import com.better.appbase.utils.SPUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dino on 2018/4/3.
 */

public class SearchPresenter extends BasePresenter implements SearchContract.Presenter {
    private final SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void getSearchHistory(String type) {
        mView.showSearchHistory(getHistoryList(type));
    }

    @Override
    public void saveSearchHistory(String type, String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        List historyList = getHistoryList(type);
        if (historyList.size() > 0) {
            //移除之前重复添加的元素
            removeHistory(historyList, key);
            historyList.add(0, key);
            if (historyList.size() > 10) {
                historyList.remove(historyList.size() - 1);//最多保存10条搜索记录 删除最早搜索的那一项
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }
            SPUtils.putString(type, sb.toString());
        } else {
            SPUtils.putString(type, key + ",");
        }
        mView.refresh();
    }

    @Override
    public void removeSearchHistory(String type, String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        List historyList = getHistoryList(type);
        removeHistory(historyList, key);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < historyList.size(); i++) {
            sb.append(historyList.get(i) + ",");
        }
        SPUtils.putString(type, sb.toString());
        mView.refresh();
    }

    @Override
    public void removeAllSearchHistory(String type) {
        SPUtils.putString(type, "");
        mView.refresh();
    }

    private List<String> getHistoryList(String type) {
        String longHistory = SPUtils.getString(type);
        if(longHistory!=null&&!"".equals(longHistory)) {
            String[] tmpHistory = longHistory.split(",");
            List historyList = new ArrayList(Arrays.asList(tmpHistory));
            return historyList;
        }else{
            return new ArrayList<>();
        }
    }

    private void removeHistory(List historyList, String key) {
        for (int i = 0; i < historyList.size(); i++) {
            if (key.equals(historyList.get(i))) {
                historyList.remove(i);
                break;
            }
        }
    }
}
