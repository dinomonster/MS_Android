package com.hr.ms.ms_android.mvp.operation.withdrawmanager.detail

import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.WithdrawApplyBean
import com.hr.ms.ms_android.bean.WithdrawCashCheckBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.utils.CodeStringUtils
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.withdrawcheck_activity.*


class WithdrawCheckActivity : BaseActivity(), WithdrawDetailContract.View, View.OnClickListener {
    private lateinit var orderNo:String;
    private lateinit var presenter: WithdrawDetailPresenter

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.withdrawcheck_activity
    }

    override fun initData() {
        presenter = WithdrawDetailPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("提现审核")
        orderNo = intent.getStringExtra(CommonConstants.ID)
    }

    override fun onClick(v: View?) {
        when (v) {
            refuse_tv -> {
                presenter.checkWithdrawCashApply(orderNo,2,checkAmount_tv.text.toString(),checkUser_tv.text.toString(),refuseReason_tv.text.toString())
            }
            agree_tv -> {
                presenter.checkWithdrawCashApply(orderNo,1,checkAmount_tv.text.toString(),checkUser_tv.text.toString(),refuseReason_tv.text.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getWithdrawCashApplyDetails(orderNo)
    }

    override fun showWithdrawApplyBean(bean: WithdrawApplyBean?) {
        name_tv.text = bean?.userName
        applyamount_tv.text = "¥"+bean?.applyAmount?.toString()
        mode_tv.text = CodeStringUtils.getWithdrawCashModeString(bean?.withdrawCashMode)
        applytime_tv.text = TimeUtils.milliseconds2String(bean?.applyTime)
        payableamount_tv.text = "¥"+bean?.payableAmount?.toString()
        poundageAmount_tv.text = "¥"+bean?.poundageAmount?.toString()
        orderNo_tv.text = bean?.orderNo
        checkStatus_tv.text = bean?.checkStatus
    }

    override fun showWithdrawCashCheckBean(bean: WithdrawCashCheckBean?) {
        checkAmount_tv.setText(bean?.checkAmount?.toString())
        checkUser_tv.setText(bean?.checkUser)
        refuseReason_tv.setText(bean?.refuseReason)
    }

}
