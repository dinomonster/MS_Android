package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo

import com.better.appbase.recyclerview.BaseMultiItemQuickAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.EventFlow
import com.hr.ms.ms_android.utils.CodeStringUtils


class FlowInfoAdapter : BaseMultiItemQuickAdapter<EventFlow, BaseViewHolder> {

    constructor(data: List<EventFlow>?) : super(data) {
        addItemType(0, R.layout.flowinfo_item)
        addItemType(1, R.layout.flowinfo_add_item)
    }

    override fun convert(helper: BaseViewHolder, item: EventFlow) {
        if (item.itemType == 0) {
            if(item.flowPointType == CodeStringUtils.nodeTypeCode[1]){
                helper.setGone(R.id.subtitle_tv,true)
            }else{
                helper.setGone(R.id.subtitle_tv,false)
            }
            helper.setText(R.id.time_tv, TimeUtils.milliseconds2String6(item.flowBeginDate) + "-" + TimeUtils.milliseconds2String6(item.flowEndDate))
            helper.setText(R.id.title_tv, item.flowPointName)
            helper.setText(R.id.subtitle_tv, item.extra2)
            helper.addOnClickListener(R.id.more_iv)
        }
    }
}

