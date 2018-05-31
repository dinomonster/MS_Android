package com.hr.ms.ms_android.mvp.operation.withdrawmanager

import android.support.v7.widget.RecyclerView
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.better.appbase.utils.TimeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.WithdrawCashBean


class WithdrawManagerAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<WithdrawCashBean>(recyclerView, R.layout.withdraw_item, null) {

    override fun convert(helper: BaseViewHolder, item: WithdrawCashBean) {
        helper.setText(R.id.tv_name,item.userName)
        helper.setText(R.id.tv_status,item.checkStatus)
        helper.setText(R.id.tv_mode,item.withdrawCashMode)
        helper.setText(R.id.tv_fee,"¥"+item.applyAmount)
        helper.setText(R.id.tv_date,TimeUtils.milliseconds2String(item.applyTime))
    }
}

