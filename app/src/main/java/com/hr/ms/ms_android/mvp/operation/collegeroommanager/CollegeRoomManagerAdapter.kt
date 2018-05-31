package com.hr.ms.ms_android.mvp.operation.collegeroommanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.CollegeRoomListBean


class CollegeRoomManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<CollegeRoomListBean.Lists>(recyclerView, R.layout.collegeroommanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: CollegeRoomListBean.Lists) {
        ImageLoadUtils.loadImage(mContext, item.collegeRoomImg, helper.getView(R.id.image_iv), R.drawable.default_back)
        helper.setText(R.id.title_tv, item.collegeRoomName)
        helper.setText(R.id.nums_tv, item.collegeRoomCurNum?.toString() + "位成员 丨 " + item.collegeRoomTopicNum + "个课题")
        helper.setText(R.id.date_tv, "开通时间："+TimeUtils.milliseconds2String(item.collegeRoomCreateTime))
        helper.setText(R.id.price_tv, "¥" + item.collegeRoomVipPrice)
    }
}

