package com.hr.ms.ms_android.mvp.operation.stagemanager.detail

import android.content.Intent
import android.view.View
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.StageDetailBean
import com.hr.ms.ms_android.bean.StageListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo.AgentInfoActivity
import com.hr.ms.ms_android.mvp.operation.stagemanager.detail.member.MemberActivity
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.stagedetail_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class StageDetailActivity : BaseActivity(), View.OnClickListener, StageDetailContract.View {
    private var presenter: StageDetailPresenter? = null
    private var detailbean: StageDetailBean? = null
    private var bean: StageListBean.Lists? = null
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.stagedetail_activity
    }

    override fun initData() {
        presenter = StageDetailPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setBackgroundColor(resources.getColor(R.color.bg))
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        toolbar.setTitleContent(bean?.stageName)
        showInfo()
        ViewUtils.setOnClickListeners(this, stage_info_ll, agent_ll, stage_user_ll)
        presenter?.getStageDetail(bean?.stageId)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            stage_info_ll -> {
//                var intent = Intent(this, StageEditActivity::class.java)
//                intent.putExtra(CommonConstants.BEAN, detailbean)
//                startActivity(intent)
            }
            agent_ll -> {
                var intent = Intent(this, AgentInfoActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, detailbean)
                startActivity(intent)
            }
            stage_user_ll -> {
                var intent = Intent(this, MemberActivity::class.java)
                intent.putExtra(CommonConstants.ID, bean?.stageId)
                startActivity(intent)
            }
        }
    }


    fun showInfo() {
        ImageLoadUtils.loadCropCircleImage(this, bean?.stageImg, image_iv, R.drawable.default_head)
        code_tv.text = "驿站编码：" + bean?.stageCode
    }

    override fun showDetail(bean: StageDetailBean?) {
        detailbean = bean
    }


}
