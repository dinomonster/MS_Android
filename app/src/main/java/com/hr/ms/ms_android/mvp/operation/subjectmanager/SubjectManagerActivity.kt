package com.hr.ms.ms_android.mvp.operation.subjectmanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ListView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.SubjectListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.SubjectDetailActivity
import com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.edit.SubjectAddActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.search_filter_add_recycler_activity.*
import java.util.*


class SubjectManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, SubjectManagerContract.View {
    private lateinit var presenter: SubjectManagerPresenter
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private lateinit var adapter: SubjectManagerAdapter
    private var pageNo = 1

    private val popupViews = ArrayList<View>()
    private val headers = arrayListOf("开始时间", "结束时间", "课题类型", "课题来源")
    private lateinit var typeAdapter: ListDropDownAdapter
    private lateinit var sourceAdapter: ListDropDownAdapter

    private var startTime: String = ""
    private var endTime: String = ""
    private var topicType: Int = CodeStringUtils.topicTypeCode[0]
    private var topicFrom: Int = CodeStringUtils.topicFromCode[0]

    override fun setViewId(): Int {
        return R.layout.search_filter_add_recycler_activity
    }

    override fun initData() {
        presenter = SubjectManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = SubjectManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, SubjectDetailActivity::class.java)
            intent.putExtra(CommonConstants.ID, (adapter.data[position] as SubjectListBean.Lists).topicId)
            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)


        fab.setOnClickListener { nextActivity(SubjectAddActivity::class.java) }

        tv_search.setOnClickListener{getData() }

    }


    private fun setFilterMenu() {
        val typeView = ListView(this)
        typeView.dividerHeight = 0
        typeAdapter = ListDropDownAdapter(this, CodeStringUtils.topicTypeString)
        typeView.adapter = typeAdapter

        val sourceView = ListView(this)
        sourceView.dividerHeight = 0
        sourceAdapter = ListDropDownAdapter(this, CodeStringUtils.topicFromString)
        sourceView.adapter = sourceAdapter

        typeView.setOnItemClickListener { adapterView, view, position, l ->
            typeAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[2] else CodeStringUtils.topicTypeString[position])
            topicType = CodeStringUtils.topicTypeCode[position]
            dropDownMenu.closeMenu()
            onRefresh()
        }

        sourceView.setOnItemClickListener { adapterView, view, position, l ->
            sourceAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[3] else CodeStringUtils.topicFromString[position])
            topicFrom = CodeStringUtils.topicFromCode[position]
            dropDownMenu.closeMenu()
            onRefresh()
        }

        popupViews.add(View(this))
        popupViews.add(View(this))
        popupViews.add(typeView)
        popupViews.add(sourceView)
        dropDownMenu.setDropDownMenu(headers, popupViews, View.inflate(context, R.layout.recyclerview_layout, null))
//        dropDownMenu.tabClick(0, { view ->
//            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
//                (view as TextView).text = TimeUtils.milliseconds2String2(millseconds)
//                startTime = TimeUtils.milliseconds2String2(millseconds)
//                onRefresh()
//            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
//        })
//        dropDownMenu.tabClick(2, { view ->
//            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
//                (view as TextView).text = TimeUtils.milliseconds2String2(millseconds)
//                endTime = TimeUtils.milliseconds2String2(millseconds)
//                onRefresh()
//            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
//        })
    }


    override fun showList(list: MutableList<SubjectListBean.Lists>?) {
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
        presenter.getTopicList(pageNo, topicType, topicFrom, et_search_content.text.toString(), startTime, endTime)
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
