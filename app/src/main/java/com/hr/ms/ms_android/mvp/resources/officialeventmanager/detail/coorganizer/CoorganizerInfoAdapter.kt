package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer

import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.Coorganizer


class CoorganizerInfoAdapter : BaseMultiItemQuickAdapter<Coorganizer, BaseViewHolder> {

    constructor(data: List<Coorganizer>?) : super(data) {
        addItemType(0, R.layout.sponsorinfo_item)
        addItemType(1, R.layout.flowinfo_add_item)
    }

    override fun convert(helper: BaseViewHolder, item: Coorganizer) {
        if (item.itemType == 0) {
            ImageLoadUtils.loadImage(mContext,item.coorganizerLogo,helper.getView(R.id.image_iv))
            helper.setText(R.id.title_tv, item.coorganizerName)
            helper.setText(R.id.subtitle_tv, item.freeNum?.toString()+"个免费名额")
            helper.addOnClickListener(R.id.more_iv)
        }else{
            helper.setText(R.id.add_tv, "+ 添加承办方")
        }
    }
}

