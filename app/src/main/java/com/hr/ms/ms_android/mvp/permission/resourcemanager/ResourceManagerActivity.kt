package com.hr.ms.ms_android.mvp.permission.resourcemanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ResourceBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.permission.resourcemanager.detail.ResourceDetailActivity
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class ResourceManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener{
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: ResourceManagerAdapter
    private var pageNo = 1
    override fun setViewId(): Int {
        return R.layout.recycler_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.addOnMoreListener {
            var intent = Intent(this, ResourceDetailActivity::class.java)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
            startActivity(intent)
        }
        toolbar.setTitleContent("菜单管理")
        toolbar.setMoreResource(R.drawable.ic_add)

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = ResourceManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this,ResourceDetailActivity::class.java)
            intent.putExtra(CommonConstants.BEAN,adapter.data[position] as ResourceBean)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_DETAIL)
            startActivity(intent)
        }

        val resourceBeans = ArrayList<ResourceBean>()
        for(i in 1 .. 30) {
            var resourceBean = ResourceBean()
            resourceBean.name = "系统设置"
            resourceBean.type = "系统"
            resourceBean.url = "sysuser/list.do"
            resourceBean.tag = "assist"
            resourceBean.isActivate = "是"
            resourceBeans.add(resourceBean)
        }
        adapter.showSinglePage(resourceBeans)
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = false
    }


}
