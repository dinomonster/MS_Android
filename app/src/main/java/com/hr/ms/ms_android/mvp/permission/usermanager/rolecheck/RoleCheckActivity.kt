package com.hr.ms.ms_android.mvp.permission.usermanager.rolecheck

import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.RoleBean
import kotlinx.android.synthetic.main.rolecheck_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class RoleCheckActivity : BaseActivity() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: RoleCheckAdapter
    override fun setViewId(): Int {
        return R.layout.rolecheck_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("角色功能")
        adapter = RoleCheckAdapter(recyclerView)
        recyclerView.adapter = adapter

        val permission = java.util.ArrayList<RoleBean>()
        for (i in 1..20) {
            var roleBean = RoleBean()
            roleBean.name = "系统设置"
            permission.add(roleBean)
        }
        adapter.showSinglePage(permission)

    }


}
