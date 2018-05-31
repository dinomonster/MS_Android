package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.member

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseAppCompatFragment
import com.hr.ms.ms_android.bean.AgentOrScholarBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import kotlinx.android.synthetic.main.member_header_layout.*
import kotlinx.android.synthetic.main.recyclerview_layout.*


class MemberFragment : BaseAppCompatFragment(), SwipeRefreshLayout.OnRefreshListener, MemberContract.View {
    private var presenter: MemberPresenter? = null
    private lateinit var adapter: MemberAdapter
    private var pageNo = 1

    private var searchKey: String? = ""
    private var orderRule: Int? = 1

    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun init() {
        stopLoadingAnima()
        presenter = MemberPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = MemberAdapter(recyclerView, arguments?.getInt(CommonConstants.TYPE))
        adapter.addHeaderView(layoutInflater.inflate(R.layout.member_header_layout, null))
        recyclerView.adapter = adapter
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

        onRefresh()
    }


    override fun showList(bean: AgentOrScholarBean) {
        totle_tv.text = "共" + bean.total + "个"
        adapter.showMultiPage(bean.lists, pageNo)
        refreshLayout.isRefreshing = false
        rule_ll.setOnClickListener {
            if (orderRule == 1) {
                orderRule = 2
                role_tv.text = "|　时间升序"
            } else if (orderRule == 2) {
                orderRule = 1
                role_tv.text = "|　时间降序"
            }
            onRefresh()
        }
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getData()
    }

    private fun getData() {
        presenter?.getAgentOrScholarList(pageNo, arguments?.getInt(CommonConstants.ID), arguments?.getInt(CommonConstants.TYPE), searchKey, orderRule)
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({onRefresh()})
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }

}
