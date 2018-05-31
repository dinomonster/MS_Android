package com.better.appbase.recyclerview;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public abstract class BaseSelectAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private int selectPosition = 0;

    public BaseSelectAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseSelectAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseSelectAdapter(int layoutResId) {
        super(layoutResId);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }
}