package com.hr.ms.ms_android.mvp.operation.usermanager.userlist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.UserListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.usermanager.edit.UserEditActivity
import com.hr.ms.ms_android.mvp.search.SearchActivity
import com.hr.ms.ms_android.utils.CodeStringUtils.identityArry
import com.hr.ms.ms_android.utils.CodeStringUtils.identityCodeArry
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_filter_add_recycler_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class UserManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, UserManagerContract.View {
    private lateinit var adapter: UserManagerAdapter
    private lateinit var presenter: UserManagerPresenter
    private var pageNo = 1

    private val popupViews = ArrayList<View>()
    private val headers = arrayListOf("身份")

    private var searchKey: String? = ""

    private var identityCode: Int? = identityCodeArry[0]

    private var startTime: Long? = null
    private var endTime: Long? = null

    private lateinit var identityAdapter: ListDropDownAdapter
    override fun setViewId(): Int {
        return R.layout.filter_recycler_activity
    }

    override fun getPresenter(): MvpPresenter {
        return presenter
    }

    @SuppressLint("RestrictedApi")
    override fun initData() {
        presenter = UserManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("用户列表")
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.setMoreTransitionName("search")
        toolbar.addOnMoreListener {
            var intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(CommonConstants.TYPE, CommonConstants.COLLEGEROOM_SEARCH)
            intent.putExtra(CommonConstants.MSG, "搜索用户名称")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, toolbar.moreView, "search")
                startActivityForResult(intent, 1000, options.toBundle())
            } else {
                startActivityForResult(intent, 1000)
            }
        }
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = UserManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, UserEditActivity::class.java)
            intent.putExtra(CommonConstants.ID, (adapter.data[position] as UserListBean.Lists).accId)
            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

    }

    private fun setFilterMenu() {
        val identityView = ListView(this)
        identityView.dividerHeight = 0
        identityAdapter = ListDropDownAdapter(this, identityArry)
        identityView.adapter = identityAdapter

        identityView.setOnItemClickListener { adapterView, view, position, l ->
            identityAdapter.setCheckItem(position)
            identityCode = identityCodeArry[position]
            dropDownMenu.setTabText(if (position === 0) headers[0] else identityArry[position])
            dropDownMenu.closeMenu()
            onRefresh()
        }
        val startview = layoutInflater.inflate(R.layout.tab_text_item, null) as TextView
        startview.text = "开始时间"
        startview.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        startview.setOnClickListener {
            AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                startTime = millseconds
                startview.text = TimeUtils.milliseconds2String2(millseconds)
                onRefresh()
            }?.build()?.show(supportFragmentManager, "ALL")
        }

        val endview = layoutInflater.inflate(R.layout.tab_text_item, null) as TextView
        endview.text = "结束时间"
        endview.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
        endview.setOnClickListener {
            AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                endTime = millseconds
                endview.text = TimeUtils.milliseconds2String2(millseconds)
                onRefresh()
            }?.build()?.show(supportFragmentManager, "ALL")
        }
        popupViews.add(identityView)
        dropDownMenu.setDropDownMenu(headers, popupViews, View.inflate(context, R.layout.recyclerview_layout, null))
        dropDownMenu.addTab(startview, 0)
        dropDownMenu.addTab(endview, 1)
    }

    override fun showList(listBean: MutableList<UserListBean.Lists>?) {
        adapter.showMultiPage(listBean, pageNo)
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
        presenter.getAccount(pageNo, identityCode, searchKey, null, startTime, endTime)
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
