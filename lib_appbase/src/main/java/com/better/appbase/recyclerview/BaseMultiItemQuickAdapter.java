package com.better.appbase.recyclerview;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

/**
 * Created by 小李
 */

public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    private SparseIntArray layouts;
    private OnNetWorkErrorRefresh listener;
    private int pageSize = 10;

    protected int getDefItemViewType(int position) {
        Object item = this.mData.get(position);
        return item instanceof MultiItemEntity?((MultiItemEntity)item).getItemType():-255;
    }

    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return this.createBaseViewHolder(parent, this.getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return this.layouts.get(viewType, -404);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if(this.layouts == null) {
            this.layouts = new SparseIntArray();
        }

        this.layouts.put(type, layoutResId);
    }

    public void remove(@IntRange(from = 0L) int position) {

    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public BaseMultiItemQuickAdapter(List<T> data) {
        super(data);
    }



    public void addData(List<T> newData) {
        newData.clear();
        if (newData != null) {
            newData.addAll(newData);
        }
        this.notifyDataSetChanged();
    }


    public void showMultiPage(List<T> listData, int pageNo) {
        if (listData == null || listData.size() == 0) {
            if (pageNo == 1) {
                setNewData(null);
            } else {
                if (getData().size() < getPageSize()) {
                    loadMoreEnd(true);
                } else {
                    loadMoreEnd();
                }
            }
            return;
        }

        if (pageNo == 1) {
            setNewData(listData);
        } else {
            addData(listData);
            loadMoreComplete();
        }

        if (listData.size() < getPageSize()) {
            if (getData().size() < getPageSize()) {
                loadMoreEnd(true);
            } else {
                loadMoreEnd();
            }
        }
    }

    public void addOnRecyclerAdapterListener(OnNetWorkErrorRefresh listener) {
        this.listener = listener;
    }

    public interface OnNetWorkErrorRefresh {
        void onListRefresh();
    }
}
