package com.hr.ms.ms_android.mvp.resources.schedulemanager.detail.guest

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.GuestBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.resources.schedulemanager.detail.guest.detail.GuestDetailActivity
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog


class GuestAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<GuestBean>(recyclerView, R.layout.guest_item, null) {

    override fun convert(helper: BaseViewHolder, item: GuestBean) {
        helper.setText(R.id.tv_name, item.name)
        helper.setText(R.id.tv_title, item.shareTheme)
        helper.setText(R.id.tv_sub_title, item.shareContent)


        helper.getView<View>(R.id.more).setOnClickListener {
            BottomMenuDialog().showBottomDialog(
                    mContext!!,
                    arrayOf("删除", "编辑"),
                    object : BottomMenuDialog.BootomListener {
                        override fun onClick(which: Int) {
                            MaterialDialog.Builder(mContext)
                                    .title("提示")
                                    .content("确认删除？")
                                    .positiveColor(mContext.resources.getColor(R.color.success_status))
                                    .negativeColor(mContext.resources.getColor(R.color.tip_text))
                                    .positiveText("确认")
                                    .negativeText("取消")
                                    .onPositive { _, _ ->
                                        notifyItemRemoved(helper.adapterPosition)
                                    }
                                    .show()
                        }
                    },
                    object : BottomMenuDialog.BootomListener {
                        override fun onClick(which: Int) {
                            var intent = Intent(mContext, GuestDetailActivity::class.java)
                            intent.putExtra(CommonConstants.BEAN,item)
                            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_EDIT)
                            mContext.startActivity(intent)
                        }
                    }
            )
        }
    }
}

