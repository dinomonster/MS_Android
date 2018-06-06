package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.agentinfo.activitycard

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CardActiveDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class ActivityCardActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, ActivityCardContract.View {
    private lateinit var presenter: ActivityCardPresenter
    private var selectPos: Int = -1

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private lateinit var adapter: ActivityCardAdapter
    private var pageNo = 1


    override fun setViewId(): Int {
        return R.layout.recycler_activity
    }

    @SuppressLint("RestrictedApi")
    override fun initData() {
        presenter = ActivityCardPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("已激活电子卡")
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.setMoreTransitionName("search")
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = ActivityCardAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getData()
    }

    private fun getData() {
        presenter.getCardActiveDetail(pageNo, intent.getIntExtra(CommonConstants.ID, -1), 2)
    }

    override fun showList(list: CardActiveDetailBean?) {
        adapter.showMultiPage(list?.lists, pageNo)
        refreshLayout.isRefreshing = false
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({ onRefresh() })
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }

}
