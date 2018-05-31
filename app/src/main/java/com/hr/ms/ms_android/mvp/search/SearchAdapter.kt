package com.hr.ms.ms_android.mvp.search

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R


class SearchAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<String>(recyclerView, R.layout.search_item, null) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.key_tv,item)
        helper.addOnClickListener(R.id.x_ll)
    }
}

