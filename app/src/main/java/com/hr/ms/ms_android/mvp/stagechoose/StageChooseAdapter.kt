package com.hr.ms.ms_android.mvp.stagechoose

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.StageSelectListBean


class StageChooseAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<StageSelectListBean.Lists>(recyclerView, R.layout.stage_item, null) {

    override fun convert(helper: BaseViewHolder, item: StageSelectListBean.Lists) {
        helper.setText(R.id.name_tv,item.stageName)
        helper.setText(R.id.code_tv,item.stageCode )
        ImageLoadUtils.loadCropCircleImage(mContext, item?.stageImg, helper.getView(R.id.iv_image),R.drawable.default_head)
    }
}

