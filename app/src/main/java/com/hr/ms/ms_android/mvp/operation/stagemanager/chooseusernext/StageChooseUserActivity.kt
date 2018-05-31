package com.hr.ms.ms_android.mvp.operation.stagemanager.chooseusernext

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.UserSelectBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.stagemanager.add.StageAddActivity
import kotlinx.android.synthetic.main.choose_user_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * 开通类型 1：代言 2：学霸 3：导师 4：驿站站长
 */
class StageChooseUserActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, StageChooseUserContract.View {
    private lateinit var presenter: StageChooseUserPresenter
    private var selectPos: Int = -1

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private lateinit var adapter: StageChooseUserAdapter
    private var pageNo = 1


    override fun setViewId(): Int {
        return R.layout.choose_user_activity
    }

    @SuppressLint("RestrictedApi")
    override fun initData() {
        presenter = StageChooseUserPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("选择用户")
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.setMoreTransitionName("search")
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = StageChooseUserAdapter(recyclerView)
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
                //测试
                var intent = Intent(this, StageAddActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, adapter.data[selectPos])
                intent.putExtra(CommonConstants.TYPE, intent.getIntExtra(CommonConstants.TYPE, -1))
                startActivity(intent)
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
        presenter.getUserList(pageNo, 4,"", "")
    }

    override fun showList(list: MutableList<UserSelectBean.Lists>?) {
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
