package com.hr.ms.ms_android.mvp.operation.withdrawmanager.detail

import android.text.method.ScrollingMovementMethod
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
import com.hr.ms.ms_android.utils.StringReplaceUtil
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.withdrawdetail_activity.*


class WithdrawDetailActivity : BaseActivity(), WithdrawDetailContract.View, View.OnClickListener {
    private lateinit var orderNo: String
    private lateinit var presenter: WithdrawDetailPresenter
    private var isShowBankCard: Boolean = false
    private var isShowCheck: Boolean = false
    private var isShowWechat: Boolean = false
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.withdrawdetail_activity
    }

    override fun initData() {
        presenter = WithdrawDetailPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("提现详情")
        orderNo = intent.getStringExtra(CommonConstants.ID)
        ViewUtils.setOnClickListeners(this, bankcard_title_ll, check_title_ll, wechat_title_ll)
        ViewUtils.setIsVisibility(View.GONE, bankcard_content_ll, check_content_ll, wechat_content_ll)
        refuseReason_tv.movementMethod = ScrollingMovementMethod()
    }

    override fun onResume() {
        super.onResume()
        presenter.getWithdrawCashApplyDetails(orderNo)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            bankcard_title_ll -> {
                if (isShowBankCard) {
                    isShowBankCard = false
                    bankcard_content_ll.visibility = View.GONE
                    bankcard_arrow.isSelected = false
                } else {
                    isShowBankCard = true
                    bankcard_content_ll.visibility = View.VISIBLE
                    bankcard_arrow.isSelected = true
                }
            }
            check_title_ll -> {
                if (isShowCheck) {
                    isShowCheck = false
                    check_content_ll.visibility = View.GONE
                    check_arrow.isSelected = false
                } else {
                    isShowCheck = true
                    check_content_ll.visibility = View.VISIBLE
                    check_arrow.isSelected = true
                }
            }
            wechat_title_ll -> {
                if (isShowWechat) {
                    isShowWechat = false
                    wechat_content_ll.visibility = View.GONE
                    wechat_arrow.isSelected = false
                } else {
                    isShowWechat = true
                    wechat_content_ll.visibility = View.VISIBLE
                    wechat_arrow.isSelected = true
                }
            }
        }
    }


    override fun showWithdrawApplyBean(bean: WithdrawApplyBean?) {
        name_tv.text = bean?.userName
        applyamount_tv.text = "¥" + bean?.applyAmount?.toString()
        mode_tv.text = CodeStringUtils.getWithdrawCashModeString(bean?.withdrawCashMode)
        applytime_tv.text = TimeUtils.milliseconds2String(bean?.applyTime)
        payableamount_tv.text = "¥" + bean?.payableAmount?.toString()
        poundageAmount_tv.text = "¥" + bean?.poundageAmount?.toString()
        orderNo_tv.text = bean?.orderNo
        checkStatus_tv.text = bean?.checkStatus
        if (bean?.withdrawCashMode == CodeStringUtils.withdrawCashModeCode[2]) {
            bankcard_ll.visibility = View.VISIBLE
            wechat_ll.visibility = View.GONE
            accountName_tv.text = bean?.bankCardVo?.accountName
            bankCard_tv.text = StringReplaceUtil.bankCardReplaceWithStar(bean?.bankCardVo?.bankCardNum)
            bankName_tv.text = bean?.bankCardVo?.bankName
            identityCard_tv.text = StringReplaceUtil.idCardReplaceWithStar(bean?.bankCardVo?.identityCardNum)
            mobile_tv.text = StringReplaceUtil.mobileReplaceWithStar(bean?.bankCardVo?.mobile)
        } else {
            bankcard_ll.visibility = View.GONE
            wechat_ll.visibility = View.VISIBLE
        }

    }

    override fun showWithdrawCashCheckBean(bean: WithdrawCashCheckBean?) {
        checkAmount_tv.text = "¥" + bean?.checkAmount?.toString()
        checkUser_tv.text = bean?.checkUser
        refuseReason_tv.text = bean?.refuseReason
        handlestatus_tv.text = bean?.wechatHandleStatus
        handltime_tv.text = TimeUtils.milliseconds2String(bean?.wechatHandleTime)
        wechatrefusereason_tv.text = bean?.wechatRefuseReason
        wechatrefusereason_tv.movementMethod = ScrollingMovementMethod()
    }

}
