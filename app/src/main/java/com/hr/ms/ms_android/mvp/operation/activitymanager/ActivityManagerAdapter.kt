package com.hr.ms.ms_android.mvp.operation.activitymanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.ScheduleBean


class ActivityManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<ScheduleBean>(recyclerView, R.layout.activitymanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: ScheduleBean) {
//        helper.setText(R.id.themename_tv,item.themeName)
//        helper.setText(R.id.teacher_tv,item.teacher)
//        helper.setText(R.id.address_tv,item.address)
//        helper.setText(R.id.date_tv,item.activityDate)
//        helper.setText(R.id.type_tv,item.type)





    }
}

