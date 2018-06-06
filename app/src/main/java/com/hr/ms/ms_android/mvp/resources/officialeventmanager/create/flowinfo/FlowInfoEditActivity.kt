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
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.flowinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class FlowInfoEditActivity : BaseActivity(), View.OnClickListener, FlowInfoContract.View {
    private var presenter: FlowInfoPresenter? = null
    private var adapter: FlowInfoAdapter? = null
    private var bean: OfficialEventDetailBean? = null
    private var cb: ChooseBean? = null
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.flowinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("活动流程")
        presenter = FlowInfoPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, next_tv)
        cb = intent.getParcelableExtra(CommonConstants.BEAN)
        next_tv.visibility = if (cb != null) View.VISIBLE else View.GONE
        adapter = FlowInfoAdapter(null)
        adapter?.setOnItemClickListener { adapter, view, position ->
            if ((adapter.data[position] as EventFlow).itemType == 1) {
                var intent = Intent(this, AddFlowActivity::class.java)
                intent.putExtra(CommonConstants.ID, bean?.officialEvent?.officialEventInfoId)
                intent.putExtra(CommonConstants.DATE, bean?.officialEvent?.officialEventBeginDate)
                startActivity(intent)
            }
        }
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            var data = adapter.data[position] as EventFlow
            when (view.id) {
                R.id.more_iv -> {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("编辑", "删除"), BottomClickListener().click {
                        var intent = Intent(this, AddFlowActivity::class.java)
                        intent.putExtra(CommonConstants.BEAN, data)
                        startActivity(intent)
                    }, BottomClickListener().click {
                        presenter?.deleteFlowPoints(data.flowInfoId)
                    })
                }
            }
        }
        recyclerView.adapter = adapter
    }

    override fun deleteSuccess() {
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }

    override fun onResume() {
        super.onResume()
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }

    override fun onClick(p0: View?) {
        when (p0) {
            next_tv -> {
                if (FlowInfoManager.eventFlowList.size == 1) {
                    showToast("至少创建一个流程节点")
                    return
                }
                success()
            }
        }
    }

    fun success() {
        var cb = intent.getParcelableExtra<ChooseBean>(CommonConstants.BEAN)
        if (cb != null) {
            var intent = Intent(this, cb.cls)
            intent.putExtra(CommonConstants.ID, this@FlowInfoEditActivity.intent.getStringExtra(CommonConstants.ID))
            startActivity(intent)
            onBackPressed()
            AppManager.getAppManager().finishActivity(OECreateBaseInfoActivity::class.java)
        } else {
            onBackPressed()
        }
    }

    override fun showDetail(bean: OfficialEventDetailBean?) {
        title_tv.text = bean?.officialEvent?.officialEventTitle
        OrderManager.clear()
        if (bean?.eventFlowList != null) {
            for (b in bean?.eventFlowList!!) {
                OrderManager.addOrder(b?.flowOrderNum?.toString())
            }
        } else {
            bean?.eventFlowList = ArrayList()
        }
        (bean?.eventFlowList as ArrayList).add(EventFlow(flowItemType = 1, flowOrderNum = 1000))
        this.bean = bean
        adapter?.showMultiPage(bean?.eventFlowList, 1)
    }

}
