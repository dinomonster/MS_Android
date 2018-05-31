package com.hr.ms.ms_android.mvp.operation.collegeroommanager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CollegeRoomListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.collegeroommanager.chooseusernext.CollegeRoomChooseUserActivity
import com.hr.ms.ms_android.mvp.operation.collegeroommanager.detail.CollegeRoomDetailActivity
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.collegeroommanager_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CollegeRoomManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, CollegeRoomManagerContract.View {
    private lateinit var presenter: CollegeRoomManagerPresenter


    private lateinit var adapter: CollegeRoomManagerAdapter
    private var pageNo = 1

    private var starttime: Long? = null
    private var endtime: Long? = null
    private var searchKey: String? = ""

    override fun setViewId(): Int {
        return R.layout.collegeroommanager_activity
    }

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    @SuppressLint("RestrictedApi")
    override fun initData() {
        presenter = CollegeRoomManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("私塾管理")
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.setMoreTransitionName("search")
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = CollegeRoomManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, CollegeRoomDetailActivity::class.java)
            intent.putExtra(CommonConstants.BEAN, adapter.data[position] as CollegeRoomListBean.Lists)
            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

        fab_add.setOnClickListener { nextActivity(CollegeRoomChooseUserActivity::class.java) }

    }


    private fun setFilterMenu() {
        val start = layoutInflater.inflate(R.layout.tab_text, null) as TextView
        start.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        start.text = "开始时间"
        start.setOnClickListener {
            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                starttime = millseconds
                start.text = TimeUtils.milliseconds2String2(millseconds)
                onRefresh()
            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
        }

        val end = layoutInflater.inflate(R.layout.tab_text, null) as TextView
        end.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        end.text = "结束时间"
        end.setOnClickListener {
            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                endtime = millseconds
                end.text = TimeUtils.milliseconds2String2(millseconds)
                onRefresh()
            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
        }

        dropDownMenu.setDropDownMenu(emptyList(), emptyList(), View.inflate(context, R.layout.recyclerview_layout, null))
        dropDownMenu.addTab(start, 0)
        dropDownMenu.addTab(end, 0)
    }

    override fun showList(list: MutableList<CollegeRoomListBean.Lists>?) {
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
        presenter.getCollegeroomList(pageNo, searchKey, starttime, endtime)
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({onRefresh()})
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == 1000) {
            searchKey = data?.getStringExtra(CommonConstants.ID)
            onRefresh()
        }
    }
}
