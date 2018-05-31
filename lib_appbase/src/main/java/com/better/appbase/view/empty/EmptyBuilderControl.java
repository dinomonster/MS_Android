package com.better.appbase.view.empty;

import android.graphics.Color;

import com.better.appbase.R;

public class EmptyBuilderControl {
    @EmptyType
    private int emptyType;

    private EmptyBuilder loadingEmptyBuilder;
    private EmptyBuilder noDataEmptyBuilder;
    private EmptyBuilder netErrorEmptyBuilder;

    public EmptyBuilderControl() {
        loadingEmptyBuilder = new EmptyBuilder();
        loadingEmptyBuilder.setEmptyContentTextColor(Color.parseColor("#666666"))
                .setEmptyContent("正在加载");

        noDataEmptyBuilder = new EmptyBuilder();
        noDataEmptyBuilder.setEmptyContentTextColor(Color.parseColor("#b2b2b2"))
                .setEmptyImage(R.mipmap.base_recyclerview_empty)
                .setEmptyContent("暂无数据");

        netErrorEmptyBuilder = new EmptyBuilder();
        netErrorEmptyBuilder.setEmptyContentTextColor(Color.parseColor("#b2b2b2"))
                .setEmptyImage(R.mipmap.base_recyclerview_empty)
                .setEmptyContent("暂无数据");
    }

    public int getEmptyType() {
        return emptyType;
    }

    public EmptyBuilderControl setEmptyType(int emptyType) {
        this.emptyType = emptyType;
        return this;
    }

    public EmptyBuilder getLoadingEmptyBuilder() {
        return loadingEmptyBuilder;
    }

    public void setLoadingEmptyBuilder(EmptyBuilder loadingEmptyBuilder) {
        this.loadingEmptyBuilder = loadingEmptyBuilder;
    }

    public EmptyBuilder getNodataEmptyBuilder() {
        return noDataEmptyBuilder;
    }

    public void setNodataEmptyBuilder(EmptyBuilder noDataEmptyBuilder) {
        this.noDataEmptyBuilder = noDataEmptyBuilder;
    }

    public EmptyBuilder getNetErrorEmptyBuilder() {
        return netErrorEmptyBuilder;
    }

    public void setNetErrorEmptyBuilder(EmptyBuilder netErrorEmptyBuilder) {
        this.netErrorEmptyBuilder = netErrorEmptyBuilder;
    }
}
