package com.hr.ms.ms_android.mvp.tagmanager

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.TagListBean


class TagManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<TagListBean.Lists>(recyclerView, R.layout.taglist_item, null) {
    private var position:Int = -1
    override fun convert(helper: BaseViewHolder, item: TagListBean.Lists) {
        helper.setText(R.id.name_tv,item.tagName)
        if(position == helper.layoutPosition) {
            helper.setChecked(R.id.name_tv, true)
            helper.getView<TextView>(R.id.name_tv).isSelected = true
        }else{
            helper.getView<TextView>(R.id.name_tv).isSelected = false
        }
    }

    fun setselected(position:Int){
        this.position = position
        notifyDataSetChanged()
    }
}

