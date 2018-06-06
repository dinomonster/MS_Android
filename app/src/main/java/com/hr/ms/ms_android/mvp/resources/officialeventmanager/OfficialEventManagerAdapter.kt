package com.hr.ms.ms_android.mvp.resources.officialeventmanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.OfficialEventListBean
import com.hr.ms.ms_android.utils.CodeStringUtils


class OfficialEventManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<OfficialEventListBean.Lists>(recyclerView, R.layout.officialeventmanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: OfficialEventListBean.Lists) {
        ImageLoadUtils.loadImage(mContext, item.eventImge, helper.getView(R.id.image_iv),R.drawable.default_back)
        helper.setText(R.id.title_tv, item.eventTitle)
        helper.setText(R.id.type_tv, item.eventTypeStr)
        helper.setText(R.id.status_tv, CodeStringUtils.getEventStatusString(item.eventStatus))
        helper.setText(R.id.date_tv, "创建时间 "+TimeUtils.milliseconds2String2(item.eventCreateTime))
    }
}

