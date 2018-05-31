package com.better.appbase.recyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by Better, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: LoopRecyclerViewAdapter.java
 * Author: lianghuiyong@outlook.com
 * Create: 2017/12/20 下午3:51
 * Info: 无限循环的RecyclerViewAdapter
 *
 * Changes (from 2017/12/20)
 * -----------------------------------------------------------------
 * 2017/12/20 : Create LoopRecyclerViewAdapter.java (梁惠涌);
 * -----------------------------------------------------------------
 */
public abstract class LoopRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {
    public LoopRecyclerViewAdapter(RecyclerView recyclerView, int layoutResId) {
        super(recyclerView, layoutResId, new LoopArray<T>());
    }

    //重写此方法，使recyclerView可以一直滑动
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    //重写getItem以免出现无限滑动时当position大于data的size时获得对象为空
    @Nullable
    @Override
    public T getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    //重写此方法，因为BaseQuickAdapter里绘制view时会调用此方法判断，position减去getHeaderLayoutCount小于data.size()时才会调用调用cover方法绘制我们自定义的view
    @Override
    public int getItemViewType(int position) {
        int count = getHeaderLayoutCount() + getData().size();
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

}
