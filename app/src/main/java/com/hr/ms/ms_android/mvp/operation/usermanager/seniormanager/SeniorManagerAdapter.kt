package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.SeniorListBean


class SeniorManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<SeniorListBean.Lists>(recyclerView, R.layout.senior_item, null) {

    override fun convert(helper: BaseViewHolder, item: SeniorListBean.Lists) {
        helper.setText(R.id.title_tv, item.userName)
        helper.setText(R.id.identity_tv, item.userIdentityType)
        helper.setText(R.id.tags_tv, "" + item.seniorIdentity + "　|　" + item.fieldName)
        helper.setText(R.id.date_tv, TimeUtils.milliseconds2String(item.createTime))
        if ("导师" == item.userIdentityType) {
            helper.setTextColor(R.id.identity_tv,mContext.resources.getColor(R.color.main_color))
        } else if ("客座嘉宾" == item.userIdentityType) {
            helper.setTextColor(R.id.identity_tv,mContext.resources.getColor(R.color.color_e77816))
        }
    }
}

