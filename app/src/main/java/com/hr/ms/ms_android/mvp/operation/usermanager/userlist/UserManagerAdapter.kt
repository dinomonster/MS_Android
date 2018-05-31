package com.hr.ms.ms_android.mvp.operation.usermanager.userlist

import android.support.v7.widget.RecyclerView
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.UserListBean


class UserManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<UserListBean.Lists>(recyclerView, R.layout.op_usermanager_item, null) {

    override fun convert(helper: BaseViewHolder, item: UserListBean.Lists) {
        helper.setText(R.id.tv_name, item.userName)
        helper.setText(R.id.tv_balance, "¥" + item.nowStore?.toString())
        helper.setText(R.id.tv_type, item.userIdentity)
        helper.setText(R.id.tv_date, TimeUtils.milliseconds2String(item.createTime))
        helper.getView<View>(R.id.role_ll).setOnClickListener {
            if (item.roleList == null || item.roleList!!.isEmpty()) {
                MaterialDialog.Builder(mContext)
                        .title("提示")
                        .content("该用户无角色信息")
                        .positiveText("确定")
                        .show()
            } else {
                MaterialDialog.Builder(mContext)
                        .title("角色信息")
                        .items(item.roleList!!)
                        .positiveText("确定")
                        .show()
            }
        }
    }
}

