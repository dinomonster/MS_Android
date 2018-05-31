package com.hr.ms.ms_android.mvp.permission.usermanager.rolecheck

import android.support.v7.widget.RecyclerView
import android.widget.TextView

import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.RoleBean
import com.hr.ms.ms_android.bean.UserManagerBean


class RoleCheckAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<RoleBean>(recyclerView, R.layout.role_select_item, null) {

    override fun convert(helper: BaseViewHolder, item: RoleBean) {
        helper.setText(R.id.title_tv,item.name)
        helper.getView<TextView>(R.id.title_tv).isSelected = item.isSelect

    }
}

