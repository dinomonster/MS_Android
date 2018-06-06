package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.choosenior

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.SeniorListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import kotlinx.android.synthetic.main.choose_user_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class ChooseSeniorActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, ChooseSeniorContract.View {
    private lateinit var presenter: ChooseSeniorPresenter
    private var selectPos: Int = -1

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private lateinit var adapter: ChooseSeniorAdapter
    private var pageNo = 1


    override fun setViewId(): Int {
        return R.layout.choose_user_activity
    }

    @SuppressLint("RestrictedApi")
    override fun initData() {
        presenter = ChooseSeniorPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("导师列表")
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.setMoreTransitionName("search")
        toolbar.addOnBackListener { onBackPressed() }
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = ChooseSeniorAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adap, view, position ->
            selectPos = position
            adapter.setselected(position)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)


        next_tv.setOnClickListener {
            if (selectPos != -1) {
                var intent = Intent()
                intent.putExtra(CommonConstants.BEAN, adapter.data[selectPos])
                setResult(Activity.RESULT_OK,intent)
                onBackPressed()
            } else {
                showToast("请选择用户")
            }
        }
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
        presenter.getSeniorList(pageNo, 1, "", "")
    }

    override fun showList(list: SeniorListBean) {
        adapter.showMultiPage(list.lists, pageNo)
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
