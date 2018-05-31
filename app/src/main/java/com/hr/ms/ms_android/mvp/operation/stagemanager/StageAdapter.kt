package com.hr.ms.ms_android.mvp.operation.stagemanager

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.StageListBean


class StageAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<StageListBean.Lists>(recyclerView, R.layout.citystage_item, null) {

    override fun convert(helper: BaseViewHolder, item: StageListBean.Lists) {
        ImageLoadUtils.loadCropCircleImage(mContext, item.stageImg, helper.getView(R.id.image_iv) as ImageView, R.drawable.default_head)
        helper.setText(R.id.title_tv, item.stageName)
        helper.setText(R.id.code_tv, "编码：" + item.stageId)
        helper.setText(R.id.nums_tv, item.agentNum?.toString() + "位代言人　|　" + item.scholarNum?.toString() + "位学霸　|　" + item.userNum?.toString() + "位学员")
        helper.setText(R.id.cardnum_tv, "电子卡数量："+item.familyEcardNum)
        helper.addOnClickListener(R.id.allocation_tv)
    }
}

