package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail

import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.OfficialEventDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.baseinfo.OEEditBaseInfoActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.FlowInfoEditActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer.CoorganizerInfoActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo.LiveInfoActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.signupinfo.SignupInfoActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.sponsor.SponsorInfoActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.CodeStringUtils.getActivityTypeString
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.officialeventdetail_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class OfficialEventDetailActivity : BaseActivity(), View.OnClickListener, OfficialEventDetailContract.View {
    private var presenter: OfficialEventDetailPresenter? = null
    private var officialEventDetailBean: OfficialEventDetailBean? = null
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.officialeventdetail_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("活动详情")
        toolbar.setMoreTitleContent("发布活动")
        presenter = OfficialEventDetailPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, title_ll, flow_ll, signup_ll, live_ll, sponsor_ll, undertaker_ll)

    }

    override fun onClick(p0: View?) {
        when (p0) {
            title_ll -> {
                var intent = Intent(this, OEEditBaseInfoActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, officialEventDetailBean?.officialEvent)
                startActivity(intent)
            }
            flow_ll -> {
                var intent = Intent(this, FlowInfoEditActivity::class.java)
                intent.putExtra(CommonConstants.ID, this@OfficialEventDetailActivity.intent.getStringExtra(CommonConstants.ID))
                startActivity(intent)
            }
            signup_ll -> {
                var intent = Intent(this, SignupInfoActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, officialEventDetailBean?.offlineinfo)
                intent.putExtra(CommonConstants.ID, officialEventDetailBean?.officialEvent?.officialEventInfoId)
                intent.putExtra(CommonConstants.MSG, officialEventDetailBean?.officialEvent?.officialEventTitle)
                startActivity(intent)
            }
            live_ll -> {
                var intent = Intent(this, LiveInfoActivity::class.java)
                intent.putExtra(CommonConstants.ID, this@OfficialEventDetailActivity.intent.getStringExtra(CommonConstants.ID))
                startActivity(intent)
            }
            sponsor_ll -> {
                var intent = Intent(this, SponsorInfoActivity::class.java)
                intent.putExtra(CommonConstants.ID, this@OfficialEventDetailActivity.intent.getStringExtra(CommonConstants.ID))
                startActivity(intent)
            }
            undertaker_ll -> {
                var intent = Intent(this, CoorganizerInfoActivity::class.java)
                intent.putExtra(CommonConstants.ID, this@OfficialEventDetailActivity.intent.getStringExtra(CommonConstants.ID))
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }

    override fun showDetail(bean: OfficialEventDetailBean?) {
        officialEventDetailBean = bean
        name_tv.text = getActivityTypeString(bean?.officialEvent?.officialEventType)
        ImageLoadUtils.loadImage(this, bean?.officialEvent?.officialEventImg, image_iv, R.drawable.default_back)
        title_tv.text = bean?.officialEvent?.officialEventTitle
        starttime_tv.text = TimeUtils.milliseconds2String(bean?.officialEvent?.officialEventBeginDate)
        endtiem_tv.text = TimeUtils.milliseconds2String(bean?.officialEvent?.officialEventEndDate)
        tip_tv.text = bean?.officialEvent?.officialEventNotice
        contetnt_tv.text = bean?.officialEvent?.officialEventIntro
        contetnt_tv.movementMethod = ScrollingMovementMethod()

        live_ll.visibility = if (bean?.officialEvent?.officialEventType == CodeStringUtils.activityTypeCode[1]) View.GONE else View.VISIBLE
        signup_ll.visibility = if (bean?.officialEvent?.officialEventType == CodeStringUtils.activityTypeCode[2]) View.GONE else View.VISIBLE

        flow_point.visibility = if (bean?.eventFlowList == null) View.VISIBLE else View.GONE
        signup_point.visibility = if (bean?.offlineinfo == null) View.VISIBLE else View.GONE
        live_point.visibility = if (bean?.liveinfo == null && bean?.imManagerList == null) View.VISIBLE else View.GONE
        sponsor_point.visibility = if (bean?.sponsorList == null) View.VISIBLE else View.GONE
        undertaker_point.visibility = if (bean?.coorganizerList == null) View.VISIBLE else View.GONE

        if(bean?.eventFlowList != null&&bean?.offlineinfo != null&&bean?.liveinfo != null&&bean?.imManagerList != null&&bean?.sponsorList != null&&bean?.coorganizerList != null){
            toolbar.setMoreColor(resources.getColor(R.color.title_text))
            toolbar.addOnMoreListener {  }
        }else{
            toolbar.setMoreColor(resources.getColor(R.color.content_text))
        }
    }

}
