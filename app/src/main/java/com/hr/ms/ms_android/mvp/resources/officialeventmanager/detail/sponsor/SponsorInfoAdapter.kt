package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.sponsor

import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.Sponsor


class SponsorInfoAdapter : BaseMultiItemQuickAdapter<Sponsor, BaseViewHolder> {

    constructor(data: List<Sponsor>?) : super(data) {
        addItemType(0, R.layout.sponsorinfo_item)
        addItemType(1, R.layout.flowinfo_add_item)
    }

    override fun convert(helper: BaseViewHolder, item: Sponsor) {
        if (item.itemType == 0) {
            ImageLoadUtils.loadImage(mContext,item.sponsorLogo,helper.getView(R.id.image_iv))
            helper.setText(R.id.title_tv, item.sponsorName)
            helper.setText(R.id.subtitle_tv, item.freeNum?.toString()+"个免费名额")
            helper.addOnClickListener(R.id.more_iv)
        }else{
            helper.setText(R.id.add_tv, "+ 添加赞助商")
        }
    }
}

