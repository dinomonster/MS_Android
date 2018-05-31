package com.hr.ms.ms_android.mvp.operation.withdrawmanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.SearchBaseActivity
import com.hr.ms.ms_android.bean.WithdrawCashBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.withdrawmanager.detail.WithdrawCheckActivity
import com.hr.ms.ms_android.mvp.operation.withdrawmanager.detail.WithdrawDetailActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.CodeStringUtils.checkStatusCode
import com.hr.ms.ms_android.utils.CodeStringUtils.checkStatusString
import com.hr.ms.ms_android.utils.CodeStringUtils.sortRuleCode
import com.hr.ms.ms_android.utils.CodeStringUtils.sortRuleString
import com.hr.ms.ms_android.utils.CodeStringUtils.withdrawCashModeCode
import com.hr.ms.ms_android.utils.CodeStringUtils.withdrawCashModeString
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.filter_recycler_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class WithdrawManagerActivity : SearchBaseActivity(), SwipeRefreshLayout.OnRefreshListener, WithdrawManagerContract.View {
    private lateinit var presenter: WithdrawManagerPresenter

    private var pageNo = 1
    private var withdrawCashMode: Int? = CodeStringUtils.withdrawCashModeCode[0]
    private var checkStatus: Int? = CodeStringUtils.checkStatusCode[0]
    private var sortRule: Int? = CodeStringUtils.sortRuleCode[1]

    private lateinit var adapter: WithdrawManagerAdapter

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.filter_recycler_activity
    }

    override fun initData() {
        super.initData()
        presenter = WithdrawManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setTitleContent("提现管理")
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = WithdrawManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var bean = adapter.data[position] as WithdrawCashBean
            var intent = Intent(this, if (bean.needCheckStatus == 1) WithdrawCheckActivity::class.java else WithdrawDetailActivity::class.java)
            intent.putExtra(CommonConstants.ID, bean.orderNo)
            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

    }

    override fun showList(list: MutableList<WithdrawCashBean>?) {
        adapter.showMultiPage(list, pageNo)
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getData()
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun getData() {
        presenter.getWithdrawCashList(pageNo, "", searchKey, withdrawCashMode, checkStatus, null, null, sortRule)
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({onRefresh()})
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }



    private fun setFilterMenu() {
        val popupViews = ArrayList<View>()
        val headers = arrayListOf("提现方式", "状态")
        val typeView = ListView(this)
        typeView.dividerHeight = 0
        var typeAdapter = ListDropDownAdapter(this, withdrawCashModeString)
        typeView.adapter = typeAdapter
        val statusView = ListView(this)
        statusView.dividerHeight = 0
        var statusAdapter = ListDropDownAdapter(this, checkStatusString)
        statusView.adapter = statusAdapter

        typeView.setOnItemClickListener { adapterView, view, position, l ->
            typeAdapter.setCheckItem(position)
            withdrawCashMode = withdrawCashModeCode[position]
            dropDownMenu.setTabText(if (position === 0) headers[0] else withdrawCashModeString[position])
            dropDownMenu.closeMenu()
            onRefresh()
        }
        statusView.setOnItemClickListener { adapterView, view, position, l ->
            statusAdapter.setCheckItem(position)
            checkStatus = checkStatusCode[position]
            dropDownMenu.setTabText(if (position === 0) headers[1] else checkStatusString[position])
            dropDownMenu.closeMenu()
            onRefresh()
        }
        val view = layoutInflater.inflate(R.layout.tab_sort_item, null)
        view.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        view.setOnClickListener {
            dropDownMenu.closeMenu()
            if (sortRule == sortRuleCode[0]) {
                sortRule = sortRuleCode[1]
                view.findViewById<TextView>(R.id.text_tv).text = sortRuleString[1]
            } else if (sortRule == sortRuleCode[1]) {
                sortRule = sortRuleCode[0]
                view.findViewById<TextView>(R.id.text_tv).text = sortRuleString[0]
            }
            onRefresh()
        }

        popupViews.add(typeView)
        popupViews.add(statusView)
//        popupViews.add(view)
        dropDownMenu.setDropDownMenu(headers, popupViews, View.inflate(context, R.layout.recyclerview_layout, null))
        dropDownMenu.addTab(view, 2)
    }

    override fun searchFresh() {
        onRefresh()
    }

}
