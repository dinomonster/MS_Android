package com.hr.ms.ms_android.mvp.operation.subjectmanager

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.SubjectListBean
import com.hr.ms.ms_android.utils.CodeStringUtils


class SubjectManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<SubjectListBean.Lists>(recyclerView, R.layout.subjectmanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: SubjectListBean.Lists) {
        ImageLoadUtils.loadImage(mContext, item?.topicImg, helper.getView(R.id.covver_image) as ImageView)
        helper.setText(R.id.themename_tv, item.topicTitle)
        helper.setText(R.id.teacher_tv, item.memberName)
        helper.setText(R.id.source_tv, CodeStringUtils.getTopicTypeString(item.topicType))
        if(item.beginTime!=null) {
            helper.setText(R.id.date_tv, TimeUtils.milliseconds2String(item.beginTime!!))
        }
        helper.setText(R.id.fee_tv, "¥"+item.seriesPrice)


    }
}

