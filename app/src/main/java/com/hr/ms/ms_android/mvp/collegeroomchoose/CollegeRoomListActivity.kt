package com.hr.ms.ms_android.mvp.collegeroomchoose

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CollegeRoomListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_bar.*


class CollegeRoomListActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, CollegeRoomListContract.View {
    private lateinit var presenter: CollegeRoomListPresenter
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private lateinit var adapter: CollegeRoomListAdapter
    private var pageNo = 1

    override fun setViewId(): Int {
        return R.layout.search_recycler_activity
    }

    override fun initData() {
        presenter = CollegeRoomListPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = CollegeRoomListAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent()
            intent.putExtra(CommonConstants.BEAN,adapter.data[position] as CollegeRoomListBean.Lists)
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
        }
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
        presenter.getCollegeroomList(pageNo, et_search_content.text.toString(),"","")
    }

    override fun showList(list: MutableList<CollegeRoomListBean.Lists>?) {
        adapter.showMultiPage(list, pageNo)
        refreshLayout.isRefreshing = false
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
