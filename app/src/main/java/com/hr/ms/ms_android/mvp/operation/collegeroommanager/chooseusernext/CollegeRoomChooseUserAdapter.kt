package com.hr.ms.ms_android.mvp.operation.collegeroommanager.chooseusernext

import android.support.v7.widget.RecyclerView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.SeniorListBean


class CollegeRoomChooseUserAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<SeniorListBean.Lists>(recyclerView, R.layout.chooseuser_item, null) {
    private var position:Int = -1
    override fun convert(helper: BaseViewHolder, item: SeniorListBean.Lists) {
        ImageLoadUtils.loadCropCircleImage(mContext, item?.userImg, helper.getView(R.id.iv_image),R.drawable.default_head)
        helper.setText(R.id.name_tv,item.userName)
        if(position == helper.layoutPosition) {
            helper.setChecked(R.id.checl_rb, true)
        }else{
            helper.setChecked(R.id.checl_rb, false)
        }
    }
    fun setselected(position:Int){
        this.position = position
        notifyDataSetChanged()
    }
}

