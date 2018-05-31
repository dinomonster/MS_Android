package com.hr.ms.ms_android.mvp.resources.overview

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.YearPlanBean


class OverViewAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<YearPlanBean>(recyclerView, R.layout.overview_plan_item, null) {

    override fun convert(helper: BaseViewHolder, item: YearPlanBean) {
        helper.setText(R.id.month_tv,item.month)
        helper.setText(R.id.location_tv,item.location)
        helper.setText(R.id.name_tv,item.name)
        helper.setText(R.id.date_tv,item.date)
        helper.setText(R.id.pesons_tv,item.persons)

    }
}

