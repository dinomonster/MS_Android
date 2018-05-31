package com.hr.ms.ms_android.mvp.operation.livemanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.TeacherBean


class LiveManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<TeacherBean>(recyclerView, R.layout.livemanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: TeacherBean) {
//        helper.setText(R.id.tv_name,item.name)
//        helper.setText(R.id.tv_mobile,item.mobile)
//        helper.setText(R.id.tv_territory,item.territory)
//        helper.setText(R.id.tv_type,item.type)
//        helper.setText(R.id.tv_date,item.talkDate)

    }
}

