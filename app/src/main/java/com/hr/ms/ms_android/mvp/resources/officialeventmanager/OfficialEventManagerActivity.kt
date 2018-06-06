package com.hr.ms.ms_android.mvp.resources.officialeventmanager

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
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.OfficialEventListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.baseinfo.OECreateBaseInfoActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.OfficialEventDetailActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.CodeStringUtils.activityTypeCode
import com.hr.ms.ms_android.utils.CodeStringUtils.activityTypeString
import com.hr.ms.ms_android.utils.CodeStringUtils.eventStatusCode
import com.hr.ms.ms_android.utils.CodeStringUtils.eventStatusString
import com.hr.ms.ms_android.utils.CodeStringUtils.sortRuleCode2
import com.hr.ms.ms_android.utils.CodeStringUtils.sortRuleString2
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.officialeventmanager_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class OfficialEventManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, OfficialEventManagerContract.View, View.OnClickListener {
    private lateinit var presenter: OfficialEventManagerPresenter

    private var pageNo = 1
    private var activityType: Int? = CodeStringUtils.activityTypeCode[0]
    private var eventStatus: Int? = CodeStringUtils.eventStatusCode[0]
    private var sortRule: Int? = CodeStringUtils.sortRuleCode2[0]

    private lateinit var adapter: OfficialEventManagerAdapter

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.officialeventmanager_activity
    }

    override fun initData() {
        presenter = OfficialEventManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setTitleContent("官方活动列表")
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)

        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = OfficialEventManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, OfficialEventDetailActivity::class.java)
            intent.putExtra(CommonConstants.ID, (adapter.data[position] as OfficialEventListBean.Lists).eventId)
            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

        ViewUtils.setOnClickListeners(this, fab_add)

    }

    override fun onClick(p0: View?) {
        when (p0) {
            fab_add -> {
                nextActivity(OECreateBaseInfoActivity::class.java)
            }
        }
    }

    override fun showList(list: OfficialEventListBean?) {
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
        presenter.getOfficialEventList(pageNo, "", eventStatus, activityType, null, null, sortRule)
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({ onRefresh() })
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }


    private fun setFilterMenu() {
        val popupViews = ArrayList<View>()
        val headers = arrayListOf("活动类型", "状态")
        val typeView = ListView(this)
        typeView.dividerHeight = 0
        var typeAdapter = ListDropDownAdapter(this, activityTypeString)
        typeView.adapter = typeAdapter
        val statusView = ListView(this)
        statusView.dividerHeight = 0
        var statusAdapter = ListDropDownAdapter(this, eventStatusString)
        statusView.adapter = statusAdapter

        typeView.setOnItemClickListener { adapterView, view, position, l ->
            typeAdapter.setCheckItem(position)
            activityType = activityTypeCode[position]
            dropDownMenu.setTabText(if (position === 0) headers[0] else activityTypeString[position])
            dropDownMenu.closeMenu()
            onRefresh()
        }
        statusView.setOnItemClickListener { adapterView, view, position, l ->
            statusAdapter.setCheckItem(position)
            eventStatus = eventStatusCode[position]
            dropDownMenu.setTabText(if (position === 0) headers[1] else eventStatusString[position])
            dropDownMenu.closeMenu()
            onRefresh()
        }
        val view = layoutInflater.inflate(R.layout.tab_sort_item, null)
        var textview = view.findViewById<TextView>(R.id.text_tv)
        textview.text = "创建时间降序"
        view.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        view.setOnClickListener {
            dropDownMenu.closeMenu()
            if (sortRule == sortRuleCode2[0]) {
                sortRule = sortRuleCode2[1]
                textview.text = "创建" + sortRuleString2[1]
            } else if (sortRule == sortRuleCode2[1]) {
                sortRule = sortRuleCode2[0]
                textview.text = "创建" + sortRuleString2[0]
            }
            onRefresh()
        }

        popupViews.add(typeView)
        popupViews.add(statusView)
//        popupViews.add(view)
        dropDownMenu.setDropDownMenu(headers, popupViews, View.inflate(context, R.layout.recyclerview_layout, null))
        dropDownMenu.addTab(view, 2)
    }


}
