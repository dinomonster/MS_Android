package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.member

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.AgentOrScholarBean
import com.hr.ms.ms_android.utils.CodeStringUtils


class MemberAdapter(recyclerView: RecyclerView,type:Int?) : BaseRecyclerViewAdapter<AgentOrScholarBean.Lists>(recyclerView, R.layout.member_item, null) {
    private var ty:Int? = type
    override fun convert(helper: BaseViewHolder, item: AgentOrScholarBean.Lists) {
        ImageLoadUtils.loadCropCircleImage(mContext, item.userImg, helper.getView(R.id.image_iv) as ImageView, R.drawable.default_head)
        helper.setText(R.id.name_tv, item.userName)
        helper.setText(R.id.date_tv, TimeUtils.milliseconds2String(item.activeTime))
        if (ty == CodeStringUtils.roleTypeCode[1]) helper.setVisible(R.id.scholar_tv, true) else helper.setVisible(R.id.scholar_tv, false)
    }
}

