package com.hr.ms.ms_android.mvp.resources.schedulemanager

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.ScheduleBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.resources.schedulemanager.detail.ScheduleDetailActivity
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog


class ScheduleManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<ScheduleBean>(recyclerView, R.layout.schedule_manager_item, null) {

    override fun convert(helper: BaseViewHolder, item: ScheduleBean) {
        helper.setText(R.id.themename_tv, item.themeName)
        helper.setText(R.id.teacher_tv, item.teacher)
        helper.setText(R.id.address_tv, item.address)
        helper.setText(R.id.date_tv, item.activityDate)
        helper.setText(R.id.type_tv, item.type)


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
                            var intent = Intent(mContext, ScheduleDetailActivity::class.java)
                            intent.putExtra(CommonConstants.BEAN,item)
                            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_EDIT)
                            mContext.startActivity(intent)
                        }
                    }
            )
        }

    }
}

