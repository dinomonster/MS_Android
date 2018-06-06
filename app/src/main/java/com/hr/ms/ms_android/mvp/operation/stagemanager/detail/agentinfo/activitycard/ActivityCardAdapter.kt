package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo.activitycard

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.CardActiveDetailBean


class ActivityCardAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<CardActiveDetailBean.Lists>(recyclerView, R.layout.activitycard_item, null) {
    private var position:Int = -1
    override fun convert(helper: BaseViewHolder, item: CardActiveDetailBean.Lists) {
//        ImageLoadUtils.loadCropCircleImage(mContext, item?.im, helper.getView(R.id.iv_image),R.drawable.default_head)
        helper.setText(R.id.name_tv,item.activeUserName )
        helper.setText(R.id.date_tv,"激活时间："+TimeUtils.milliseconds2String2(item.activeTime) )
    }
}

