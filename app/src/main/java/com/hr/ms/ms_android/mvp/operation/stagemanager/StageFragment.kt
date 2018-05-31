package com.hr.ms.ms_android.mvp.operation.stagemanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.EditText
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseAppCompatFragment
import com.hr.ms.ms_android.bean.StageListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.AccountHelper
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.stagemanager.chooseusernext.StageChooseUserActivity
import com.hr.ms.ms_android.mvp.operation.stagemanager.detail.StageDetailActivity
import kotlinx.android.synthetic.main.stage_fragment.*


class StageFragment : BaseAppCompatFragment(), SwipeRefreshLayout.OnRefreshListener, StageContract.View {
    private var presenter: StagePresenter? = null
    private lateinit var adapter: StageAdapter
    private var pageNo = 1

    private var starttime: Long? = null
    private var endtime: Long? = null
    private var searchKey: String? = ""
    private var orderRule: Int? = 1

    override fun getLayoutId(): Int {
        return R.layout.stage_fragment
    }

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun init() {
        super.init()
        stopLoadingAnima()
        presenter = StagePresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = StageAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
                        var intent = Intent(context, StageDetailActivity::class.java)
            intent.putExtra(CommonConstants.BEAN, adapter.data[position] as StageListBean.Lists)
            startActivity(intent)
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            var bean = adapter.data[position] as StageListBean.Lists
            when (view.id) {
                R.id.allocation_tv -> {
                    dialogHelper?.showCreateCard({},{
                        presenter?.createECard(bean?.stageId,dialogHelper.dialog?.findViewById<EditText>(R.id.num_et)?.text?.toString())
                    })
                }
            }
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

        fab_add.setOnClickListener {
            var intent = Intent(context, StageChooseUserActivity::class.java)
            intent.putExtra(CommonConstants.PARAMS,arguments?.getInt(CommonConstants.TYPE) )
            intent.putExtra(CommonConstants.TYPE,arguments?.getInt(CommonConstants.TYPE) )
            startActivity(intent)
        }
        onRefresh()
    }

    override fun createECardSucess() {
        onRefresh()
    }

    override fun showList(bean: StageListBean) {
        (activity as StageManagerActivity).setTotal(bean.total?.toString())
        adapter.showMultiPage(bean.lists, pageNo)
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getData()
    }

    private fun getData() {
        presenter?.getStageIndexList(pageNo, arguments?.getInt(CommonConstants.TYPE), searchKey, starttime, endtime, orderRule)
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({onRefresh()})
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }

    fun setkey(key:String?){
        searchKey = key
        onRefresh()
    }

}
