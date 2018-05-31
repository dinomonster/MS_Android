package com.better.appbase.recyclerview;

import com.better.appbase.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

public final class BaseCustomLoadMoreView extends LoadMoreView {

    private String loadMoreStr = "已经到底啦";
    @Override
    public int getLayoutId() {
        return R.layout.base_recycler_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_end;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        super.convert(holder);
        holder.setText(R.id.load_more_tv_info, loadMoreStr);
    }

    public void setLoadMoreStr(String loadMoreStr) {
        this.loadMoreStr = loadMoreStr;
    }
}
