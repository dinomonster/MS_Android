package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.ImManager
import com.hr.ms.ms_android.utils.CodeStringUtils


class LiveInfoAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<ImManager>(recyclerView, R.layout.liveinfo_item, null) {
    override fun convert(helper: BaseViewHolder, item: ImManager) {
        ImageLoadUtils.loadCropCircleImage(mContext, item?.imManagerUserImg, helper.getView(R.id.image_iv),R.drawable.default_head)
        helper.setText(R.id.name_tv,item.imManagerUserName)
        helper.setText(R.id.type_tv,CodeStringUtils.getImManagerTypeString(item.imManagerType))
        helper.addOnClickListener(R.id.more_iv)
    }
}

