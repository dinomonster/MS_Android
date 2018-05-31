package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.IdentityBeanList
import com.hr.ms.ms_android.utils.CodeStringUtils


class IdentityManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<IdentityBeanList.Lists>(recyclerView, R.layout.identifymanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: IdentityBeanList.Lists) {
        helper.setText(R.id.tv_name,item.userName )
        helper.setText(R.id.tv_mobile,item.mobile)
        if (item.isAgent  == CodeStringUtils.isOrNoCode[0]){
            helper.setText(R.id.agent_tv,"未开通代言人")
        }else if (item.isAgent  == CodeStringUtils.isOrNoCode[1]){
            helper.setText(R.id.agent_tv,"已开通代言人")
        }

        if (item.isScholar   == CodeStringUtils.isOrNoCode[0]){
            helper.setText(R.id.learning_tv,"未开通学霸")
        }else if (item.isScholar  == CodeStringUtils.isOrNoCode[1]){
            helper.setText(R.id.learning_tv,"已开通学霸")
        }

        if (item.isStageOwner  == CodeStringUtils.isOrNoCode[0]){
            helper.setText(R.id.station_tv,"未开通站长")
        }else if (item.isStageOwner  == CodeStringUtils.isOrNoCode[1]){
            helper.setText(R.id.station_tv,"已开通站长")
        }
    }
}

