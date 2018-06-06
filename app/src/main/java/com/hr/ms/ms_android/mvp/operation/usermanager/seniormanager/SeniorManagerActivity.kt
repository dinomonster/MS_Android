package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ListView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.SeniorListBean
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager.chooseusernext.SeniorChooseUserActivity
import com.hr.ms.ms_android.utils.CodeStringUtils.userIdentityTypeCode
import com.hr.ms.ms_android.utils.CodeStringUtils.userIdentityTypeString
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.seniormanager_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class SeniorManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, SeniorManagerContract.View {
    private lateinit var presenter: SeniorManagerPresenter

    private var pageNo = 1
    private var userIdentityType: Int? = null
//    private var sortRule: Int? = CodeStringUtils.sortRuleCode[1]

    private lateinit var adapter: SeniorManagerAdapter

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.seniormanager_activity
    }

    override fun initData() {
        presenter = SeniorManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setTitleContent("师兄管理")
        toolbar.setStatusBarFontDark(this)
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.addOnBackListener { onBackPressed() }
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = SeniorManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
//            var bean = adapter.data[position] as WithdrawCashBean
//            var intent = Intent(this, if (bean.needCheckStatus == 1) WithdrawCheckActivity::class.java else WithdrawDetailActivity::class.java)
//            intent.putExtra(CommonConstants.ID, bean.orderNo)
//            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

        fab_add.setOnClickListener {
            nextActivity(SeniorChooseUserActivity::class.java)
        }
    }

    override fun showList(list: SeniorListBean?) {
        adapter.showMultiPage(list?.lists, pageNo)
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
        presenter.getSeniorList(pageNo, userIdentityType, null, null)
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
        val headers = arrayListOf("导师类型")
        val typeView = ListView(this)
        typeView.dividerHeight = 0
        var typeAdapter = ListDropDownAdapter(this, userIdentityTypeString)
        typeView.adapter = typeAdapter

        typeView.setOnItemClickListener { adapterView, view, position, l ->
            typeAdapter.setCheckItem(position)
            userIdentityType = userIdentityTypeCode[position]
            dropDownMenu.setTabText(if (position === 0) headers[0] else userIdentityTypeString[position])
            dropDownMenu.closeMenu()
            onRefresh()
        }
//        val view = layoutInflater.inflate(R.layout.tab_sort_item, null)
//        view.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
//        view.setOnClickListener {
//            if (sortRule == sortRuleCode[0]) {
//                sortRule = sortRuleCode[1]
//                view.findViewById<TextView>(R.id.text_tv).text = sortRuleString[1]
//            } else if (sortRule == sortRuleCode[1]) {
//                sortRule = sortRuleCode[0]
//                view.findViewById<TextView>(R.id.text_tv).text = sortRuleString[0]
//            }
//            onRefresh()
//        }

        popupViews.add(typeView)
        dropDownMenu.setDropDownMenu(headers, popupViews, View.inflate(context, R.layout.recyclerview_layout, null))
//        dropDownMenu.addTab(view, 1)
    }

}
