package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.IdentityBeanList
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.setagent.SetAgentActivity
import com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.setscholar.SetScholarActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_bar.*


class IdentityManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, IdentityManagerContract.View {
    private lateinit var presenter: IdentityManagerPresenter
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private lateinit var adapter: IdentityManagerAdapter
    private var pageNo = 1

    override fun setViewId(): Int {
        return R.layout.search_recycler_activity
    }

    override fun initData() {
        presenter = IdentityManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = IdentityManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var bean = adapter.data[position] as IdentityBeanList.Lists
            BottomMenuDialog().showBottomDialog(
                    context!!,
                    arrayOf(if (bean.isAgent == CodeStringUtils.isOrNoCode[0]) "开通代言人" else "取消代言人",
                            if (bean.isScholar == CodeStringUtils.isOrNoCode[0]) "开通学霸" else "取消学霸"),
                    object : BottomMenuDialog.BootomListener {
                        override fun onClick(which: Int) {
                            if (bean.isAgent == CodeStringUtils.isOrNoCode[0]) {
                                var intent = Intent()
                                intent.putExtra(CommonConstants.BEAN, bean)
                                intent.setClass(context, SetAgentActivity::class.java)
                                startActivity(intent)
                            } else {
                                presenter.cancelAgentOrScholar(bean?.accId, CodeStringUtils.roleTypeCode[0])
                            }
                        }
                    },
                    object : BottomMenuDialog.BootomListener {
                        override fun onClick(which: Int) {
                            if (bean.isScholar == CodeStringUtils.isOrNoCode[0]) {
                                var intent = Intent()
                                intent.putExtra(CommonConstants.BEAN, bean)
                                intent.setClass(context, SetScholarActivity::class.java)
                                startActivity(intent)
                            } else {
                                presenter.cancelAgentOrScholar(bean?.accId, CodeStringUtils.roleTypeCode[1])
                            }
                        }
                    }
            )
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

    private fun getData() {
        presenter.getIdentityManageList(pageNo, et_search_content.text.toString(), null)
    }

    override fun showList(list: MutableList<IdentityBeanList.Lists>?) {
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

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getData()
    }

    override fun editSucess() {
        onRefresh()
    }


}
