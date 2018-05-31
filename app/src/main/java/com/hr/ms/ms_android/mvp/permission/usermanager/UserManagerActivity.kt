package com.hr.ms.ms_android.mvp.permission.usermanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.UserManagerBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.permission.usermanager.detail.UserDetailActivity
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class UserManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: UserManagerAdapter
    private var pageNo = 1
    override fun setViewId(): Int {
        return R.layout.recycler_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("用户管理")
        toolbar.setMoreResource(R.drawable.ic_add)
        toolbar.addOnMoreListener {
            var intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
            startActivity(intent)
        }

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = UserManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra(CommonConstants.BEAN, adapter.data[position] as UserManagerBean)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_DETAIL)
            startActivity(intent)
        }

        val userBeans = ArrayList<UserManagerBean>()
        for (i in 1..30) {
            var userBean = UserManagerBean()
            userBean.account = "admin"
            userBean.password = "123456"
            userBean.userName = "系统管理员"
            userBean.mobile = "13400000000"
            userBean.email = "admin@qq.com"
            userBean.isLock = false
            userBean.enterprise = ""
            userBean.loginCount = 335
            userBean.role = ""
            userBean.loginNearlyDate = "2018/04/08 13:38"
            userBean.registerType = "手工"
            userBean.createDate = "2018/04/08 13:38"
            userBean.updateDate = "2018/04/08 13:38"
            userBeans.add(userBean)
        }
        adapter.showSinglePage(userBeans)
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = false
    }


}
