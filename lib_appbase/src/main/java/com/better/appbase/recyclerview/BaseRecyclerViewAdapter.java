package com.better.appbase.recyclerview;

import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.better.appbase.R;
import com.better.appbase.utils.NetworkUtils;
import com.better.appbase.view.empty.BetterEmptyView;
import com.better.appbase.view.empty.EmptyType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lianghuiyong on 2016/9/20.
 * Description: RecyclerView Adapter
 */
public abstract class BaseRecyclerViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private RecyclerView recyclerView;
    private volatile BetterEmptyView emptyView;
    private volatile View emptyViewLayout;
    private boolean isFirstRefresh = true;
    private int showLoadMoreEndNum = 7;
    private BaseCustomLoadMoreView customLoadMoreView;

    public int getShowLoadMoreEndNum() {
        return showLoadMoreEndNum;
    }

    public void setShowLoadMoreEndNum(int showLoadMoreEndNum) {
        this.showLoadMoreEndNum = showLoadMoreEndNum;
    }

    public BaseRecyclerViewAdapter(RecyclerView recyclerView, int layoutIdRes, List<T> data) {
        super(layoutIdRes, data);
        this.recyclerView = recyclerView;
        setBaseView();
    }

    public BaseRecyclerViewAdapter(RecyclerView recyclerView, int layoutIdRes) {
        super(layoutIdRes);
        this.recyclerView = recyclerView;
        setBaseView();
    }

    private void setBaseView() {
        setHeaderFooterEmpty(true, true);

        customLoadMoreView = new BaseCustomLoadMoreView();
        customLoadMoreView.setLoadMoreEndGone(false);
        setLoadMoreView(customLoadMoreView);
        setPreLoadNumber(5);

        emptyViewLayout = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.base_recycler_empty, (ViewGroup) recyclerView.getParent(), false);
        emptyView = emptyViewLayout.findViewById(R.id.list_empty_layout);
        setEmptyView(emptyViewLayout);
    }

    public void setLoadMoreStr(String loadMoreStr) {
        if (customLoadMoreView != null) {
            customLoadMoreView.setLoadMoreStr(loadMoreStr);
        }
    }

    public void setNoDataLayout(int layoutId) {
        emptyViewLayout = LayoutInflater.from(recyclerView.getContext())
                .inflate(layoutId, (ViewGroup) recyclerView.getParent(), false);
    }

    public void setNoDataView(@DrawableRes int image, String emptyContent) {
        if (emptyView != null) {
            emptyView.getEmptyBuilderControl().getNodataEmptyBuilder()
                    .setEmptyImage(image)
                    .setEmptyContent(emptyContent)
                    .setBtnText(null)
                    .setBtnListener(null);
        }
    }

    public void setNoDataView(@DrawableRes int image, String emptyContent, String btnText, View.OnClickListener listener) {
        if (emptyView != null) {
            emptyView.getEmptyBuilderControl().getNodataEmptyBuilder()
                    .setEmptyImage(image)
                    .setEmptyContent(emptyContent)
                    .setBtnText(btnText)
                    .setBtnListener(listener);
        }
    }

    public void showNoDataView() {
        if (emptyView != null) {
            emptyView.getEmptyBuilderControl().setEmptyType(EmptyType.NODATA);

            emptyView.show();
        }
    }

    public void showNetWorkErrorView(View.OnClickListener listener) {
        if (emptyView != null) {

            emptyView.getEmptyBuilderControl().getNetErrorEmptyBuilder()
                    .setEmptyContent(NetworkUtils.isConnected(recyclerView.getContext()) ? "加载失败" : "无网络连接，请检查网络！")
                    .setBtnText("重新加载")
                    .setBtnListener(listener);

            emptyView.getEmptyBuilderControl().setEmptyType(EmptyType.NET_ERROR);

            emptyView.show();
        }
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        if (data != null && data.size() != 0 && isFirstRefresh) {
            openLoadAnimation();
            isFirstRefresh = false;
        } else {
            closeLoadAnimation();
        }
        super.setNewData(data);
    }

    public void addData(List<T> newData) {
        if (newData != null) {

            //Android 7.0 及以上开启动画效果
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                openLoadAnimation();
            }
            super.addData(newData);
        }
    }

    // 单页面数据
    public void showSinglePage(List<T> listData) {
        if (listData == null || listData.size() == 0) {
            showNoDataView();
        } else {
            setNewData(listData);
            loadMoreEnd();
        }
    }

    //多页面数据
    public void showMultiPage(List<T> listData, int pageNo) {

        //加载数据
        switch (pageNo) {
            case 1:
                setNewData(listData);

                if (getData().size() == 0) {
                    showNoDataView();
                }
                break;

            default:
                addData(listData);
                break;
        }

        //是否加载完成判断
        if (getData().size() % 10 == 0 && listData != null && listData.size() != 0) {
            loadMoreComplete();
        } else {
            if (getData().size() >= getShowLoadMoreEndNum()) {
                loadMoreEnd(false);
            } else {
                loadMoreEnd(true);
            }
        }
    }
}
