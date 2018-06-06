package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo

import android.content.Intent
import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.AppManager
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ChooseBean
import com.hr.ms.ms_android.bean.EventFlow
import com.hr.ms.ms_android.bean.OfficialEventDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.baseinfo.OECreateBaseInfoActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.add.AddFlowActivity
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.flowinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class FlowInfoActivity : BaseActivity(), View.OnClickListener, FlowInfoContract.View {
    private var presenter: FlowInfoPresenter? = null
    private var adapter: FlowInfoAdapter? = null

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.flowinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("活动流程节点")
        presenter = FlowInfoPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, next_tv)
        title_tv.text = intent.getStringExtra(CommonConstants.MSG)
        adapter = FlowInfoAdapter(null)
        adapter?.setOnItemClickListener { adapter, view, position ->
            if ((adapter.data[position] as EventFlow).itemType == 1) {
                var intent = Intent(this, AddFlowActivity::class.java)
                intent.putExtra(CommonConstants.ID, this@FlowInfoActivity.intent.getStringExtra(CommonConstants.ID))
                intent.putExtra(CommonConstants.DATE, this@FlowInfoActivity.intent.getLongExtra(CommonConstants.DATE,0))
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter
        FlowInfoManager.addEventFlow(EventFlow(flowItemType = 1,flowOrderNum = 1000))
        adapter?.showMultiPage(FlowInfoManager.eventFlowList, 1)
    }

    override fun onResume() {
        super.onResume()
        FlowInfoManager.sort()
        adapter?.showMultiPage(FlowInfoManager.eventFlowList,1)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            next_tv -> {
                if (FlowInfoManager.eventFlowList.size == 1){
                    showToast("至少创建一个流程节点")
                    return
                }
                success()
            }
        }
    }

    fun success() {
        var cb = intent.getParcelableExtra<ChooseBean>(CommonConstants.BEAN)
        if(cb!=null){
            var intent = Intent(this, cb.cls)
            intent.putExtra(CommonConstants.ID, this@FlowInfoActivity.intent.getStringExtra(CommonConstants.ID))
            startActivity(intent)
            onBackPressed()
            AppManager.getAppManager().finishActivity(OECreateBaseInfoActivity::class.java)
        }else{
            onBackPressed()
        }
    }

    override fun deleteSuccess() {
    }

    override fun showDetail(bean: OfficialEventDetailBean?) {
    }
}
