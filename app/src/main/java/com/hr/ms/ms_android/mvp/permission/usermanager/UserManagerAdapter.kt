package com.hr.ms.ms_android.mvp.permission.usermanager

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog

import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.UserManagerBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.constants.CommonConstants.ACTIVITYSTATUS
import com.hr.ms.ms_android.constants.CommonConstants.STATUS_EDIT
import com.hr.ms.ms_android.mvp.permission.usermanager.detail.UserDetailActivity
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog


class UserManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<UserManagerBean>(recyclerView, R.layout.user_manager_layout_item, null) {

    override fun convert(helper: BaseViewHolder, item: UserManagerBean) {
        helper.setText(R.id.tv_account,item.userName)
        helper.setText(R.id.tv_date,item.loginNearlyDate)

        helper.getView<View>(R.id.more).setOnClickListener {
//            BottomMenuDialog().showBottomDialog(
//                    mContext!!,
//                    arrayOf("删除","编辑","重置密码"),
//                    object : BottomMenuDialog.BootomListener {
//                        override fun onClick(which: Int) {
//                            MaterialDialog.Builder(mContext)
//                                    .title("提示")
//                                    .content("确认删除？")
//                                    .positiveColor(mContext.resources.getColor(R.color.success_status))
//                                    .negativeColor(mContext.resources.getColor(R.color.tip_text))
//                                    .positiveText("确认")
//                                    .negativeText("取消")
//                                    .onPositive { _, _ ->
//                                        notifyItemRemoved(helper.adapterPosition)
//                                    }
//                                    .show()
//                        }
//                    },
//                    object : BottomMenuDialog.BootomListener {
//                        override fun onClick(which: Int) {
//                            var intent = Intent()
//                            intent.setClass(mContext,UserDetailActivity::class.java)
//                            intent.putExtra(ACTIVITYSTATUS, STATUS_EDIT)
//                            intent.putExtra(CommonConstants.BEAN,item)
//                            mContext.startActivity(intent)
//                        }
//                    },
//                    object : BottomMenuDialog.BootomListener {
//                        override fun onClick(which: Int) {
//                        }
//                    }
//            )
        }
    }
}

