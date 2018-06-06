package com.hr.ms.ms_android.mvp.permission.resourcemanager

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.ResourceBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.constants.CommonConstants.ACTIVITYSTATUS
import com.hr.ms.ms_android.constants.CommonConstants.STATUS_EDIT
import com.hr.ms.ms_android.mvp.permission.resourcemanager.detail.ResourceDetailActivity
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog


class ResourceManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<ResourceBean>(recyclerView, R.layout.resource_manager_item, null) {

    override fun convert(helper: BaseViewHolder, item: ResourceBean) {
        helper.setText(R.id.title_tv,item.name)
        helper.setText(R.id.url_tv,item.url)

        helper.getView<View>(R.id.more).setOnClickListener {
//            BottomMenuDialog().showBottomDialog(
//                    mContext!!,
//                    arrayOf("删除","编辑"),
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
//                            intent.setClass(mContext, ResourceDetailActivity::class.java)
//                            intent.putExtra(ACTIVITYSTATUS, STATUS_EDIT)
//                            intent.putExtra(CommonConstants.BEAN,item)
//                            mContext.startActivity(intent)
//                        }
//                    }
//            )
        }
    }
}

