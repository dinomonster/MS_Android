package com.hr.ms.ms_android.mvp.collegeroomchoose

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.CollegeRoomListBean


class CollegeRoomListAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<CollegeRoomListBean.Lists>(recyclerView, R.layout.teacherchoose_item, null) {

    override fun convert(helper: BaseViewHolder, item: CollegeRoomListBean.Lists) {
        helper.setText(R.id.name_tv,item.collegeRoomName)
        helper.setText(R.id.introduction_tv,item.collegeRoomDesc)
        ImageLoadUtils.loadCropCircleImage(mContext, item?.collegeRoomImg, helper.getView(R.id.iv_image))
    }
}

