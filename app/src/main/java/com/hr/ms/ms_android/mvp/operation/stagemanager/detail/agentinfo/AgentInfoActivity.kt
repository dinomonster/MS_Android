package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo

import android.content.Intent
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.StageDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo.activitycard.ActivityCardActivity
import kotlinx.android.synthetic.main.agentinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class AgentInfoActivity : BaseActivity() {

    override fun getPresenter(): MvpPresenter? {
        return null
    }

    override fun setViewId(): Int {
        return R.layout.agentinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("代言卡详情")
        var detailbean: StageDetailBean? = intent.getParcelableExtra(CommonConstants.BEAN)
        cardnum_tv.text = detailbean?.familyEcardTotalNum?.toString() + "张"
        residuenum_tv.text = detailbean?.familyEcardLeftNum?.toString() + "张"
        activatednum_tv.text = if (detailbean?.familyEcardTotalNum != null && detailbean?.familyEcardLeftNum != null) (detailbean?.familyEcardTotalNum!! - detailbean?.familyEcardLeftNum!!).toString() + "张" else ""
        activitycard_ll.setOnClickListener {
            var intent = Intent(this, ActivityCardActivity::class.java)
            intent.putExtra(CommonConstants.ID, detailbean?.stageId)
            startActivity(intent)
        }
    }


}
