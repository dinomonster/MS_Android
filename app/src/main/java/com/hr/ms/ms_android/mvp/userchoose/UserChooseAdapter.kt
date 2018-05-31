package com.hr.ms.ms_android.mvp.userchoose

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.UserSelectBean


class UserChooseAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<UserSelectBean.Lists>(recyclerView, R.layout.teacherchoose_item, null) {

    override fun convert(helper: BaseViewHolder, item: UserSelectBean.Lists) {
        helper.setText(R.id.name_tv,item.userName)
        helper.setText(R.id.introduction_tv,item.userIntro)
        ImageLoadUtils.loadCropCircleImage(mContext, item?.userImg, helper.getView(R.id.iv_image),R.drawable.default_head)
    }
}

