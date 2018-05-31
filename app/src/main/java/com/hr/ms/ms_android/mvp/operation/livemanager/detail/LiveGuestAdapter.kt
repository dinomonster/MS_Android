package com.hr.ms.ms_android.mvp.operation.livemanager.detail

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.TeacherBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.constants.CommonConstants.ACTIVITYSTATUS
import com.hr.ms.ms_android.constants.CommonConstants.STATUS_EDIT
import com.hr.ms.ms_android.mvp.operation.activitymanager.detail.guestdetail.ActivityGuestDetailActivity
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog


class LiveGuestAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<TeacherBean>(recyclerView, R.layout.liveguest_item, null) {

    override fun convert(helper: BaseViewHolder, item: TeacherBean) {
//        helper.setText(R.id.tv_name,item.name)
//        helper.setText(R.id.tv_mobile,item.mobile)
//        helper.setText(R.id.tv_territory,item.territory)
//        helper.setText(R.id.tv_type,item.type)
//        helper.setText(R.id.tv_date,item.talkDate)


        helper.getView<View>(R.id.more).setOnClickListener {
            BottomMenuDialog().showBottomDialog(
                    mContext!!,
                    arrayOf("删除","编辑"),
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
                            var intent = Intent()
                            intent.setClass(mContext, ActivityGuestDetailActivity::class.java)
                            intent.putExtra(ACTIVITYSTATUS, STATUS_EDIT)
                            intent.putExtra(CommonConstants.BEAN,item)
                            mContext.startActivity(intent)
                        }
                    }
            )
        }
    }
}

