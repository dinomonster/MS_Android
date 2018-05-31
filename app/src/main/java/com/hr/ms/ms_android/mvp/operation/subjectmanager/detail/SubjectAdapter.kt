package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.CourseListBean
import com.hr.ms.ms_android.utils.CodeStringUtils


class SubjectAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<CourseListBean.Lists>(recyclerView, R.layout.subject_item, null) {

    override fun convert(helper: BaseViewHolder, item: CourseListBean.Lists) {
        helper.setText(R.id.name_tv, item.title)
        helper.setText(R.id.type_tv, CodeStringUtils.getisBroadcastString(item.isBroadcast))
        helper.setText(R.id.status_tv, CodeStringUtils.getauditStatusString(item.auditStatus))
        if (item.updateTime != null) {
            helper.setText(R.id.date_tv, TimeUtils.milliseconds2String(item.updateTime!!))
        }
//        helper.setText(R.id.teacher_tv,item.teacher)
//        helper.setText(R.id.address_tv,item.address)
//        helper.setText(R.id.date_tv,item.activityDate)
//        helper.setText(R.id.type_tv,item.type)


    }
}

