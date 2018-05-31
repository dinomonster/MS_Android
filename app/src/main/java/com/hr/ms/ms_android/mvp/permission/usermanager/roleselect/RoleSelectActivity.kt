package com.hr.ms.ms_android.mvp.permission.usermanager.roleselect

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.RoleBean
import kotlinx.android.synthetic.main.roleselect_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class RoleSelectActivity : BaseActivity() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapterRole: RoleSelectAdapter
    private lateinit var adapterPermission: RoleSelectAdapter
    private var selectData:RoleBean? = null
    override fun setViewId(): Int {
        return R.layout.roleselect_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("角色选择")
        toolbar.addOnMoreListener {
            var intent = Intent()
            intent.putExtra("RoleBean",selectData)
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
        }
        adapterRole = RoleSelectAdapter(recyclerView_role)
        adapterPermission = RoleSelectAdapter(recyclerView_permission)
        recyclerView_role.adapter = adapterRole
        recyclerView_permission.adapter = adapterPermission

        val role = java.util.ArrayList<RoleBean>()
        var roleBean = RoleBean()
        roleBean.name = "超级管理员"
        roleBean.isSelect = false
        role.add(roleBean)
        var roleBean1 = RoleBean()
        roleBean1.name = "系统管理员"
        roleBean1.isSelect = false
        role.add(roleBean1)
        var roleBean2 = RoleBean()
        roleBean2.name = "运营管理员"
        roleBean2.isSelect = false
        role.add(roleBean2)
        adapterRole.showSinglePage(role)
        adapterRole.setOnItemClickListener { adapter, view, position ->
            view.findViewById<TextView>(R.id.title_tv).isSelected = true
            (adapter.data[position] as RoleBean).isSelect = true
            for (i in 0 until adapter.data.size){
                if(i!=position){
                    (adapter.data[i] as RoleBean).isSelect = false
                }
            }
            adapter.notifyDataSetChanged()

            selectData = adapter.data[position] as RoleBean
            when (position) {
                0 -> {
                    val permission = java.util.ArrayList<RoleBean>()
                    for (i in 1..20) {
                        var roleBean = RoleBean()
                        roleBean.name = "系统设置"
                        permission.add(roleBean)
                    }
                    adapterPermission.showSinglePage(permission)
                }
                1 -> {
                    val permission = java.util.ArrayList<RoleBean>()
                    for (i in 1..20) {
                        var roleBean = RoleBean()
                        roleBean.name = "资源管理"
                        permission.add(roleBean)
                    }
                    adapterPermission.showSinglePage(permission)
                }
                2 -> {
                    val permission = java.util.ArrayList<RoleBean>()
                    for (i in 1..20) {
                        var roleBean = RoleBean()
                        roleBean.name = "运营管理"
                        permission.add(roleBean)
                    }
                    adapterPermission.showSinglePage(permission)
                }
            }
        }


        val permission = java.util.ArrayList<RoleBean>()
        adapterPermission.showSinglePage(permission)
    }


}
