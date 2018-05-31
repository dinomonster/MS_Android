package com.hr.ms.ms_android.mvp.main

import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.base.BaseViewpagerAdapter
import com.hr.ms.ms_android.bean.LoginUserBean
import com.hr.ms.ms_android.data.AccountHelper
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.me.MeFragment
import com.hr.ms.ms_android.mvp.operation.OperationFragment
import com.hr.ms.ms_android.mvp.permission.PermissionFragment
import com.hr.ms.ms_android.mvp.resources.ResourcesFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : BaseActivity(), View.OnClickListener,MainContract.View {
    private lateinit var presenter: MainPresenter
    private val tabImages = intArrayOf(R.drawable.select_workbench, R.drawable.select_me)
    private lateinit var permissionFragment: PermissionFragment
    private lateinit var resourcesFragment: ResourcesFragment
    private lateinit var operationFragment: OperationFragment
    private lateinit var meFragment: MeFragment
    private lateinit var adapter: BaseViewpagerAdapter
    private var exitTime: Long = 0  // 双击返回键退出

    private lateinit var loginUserBean: LoginUserBean
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.main_activity
    }

    override fun initData() {
        presenter = MainPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        loginUserBean = AccountHelper.getUser()
        permissionFragment = PermissionFragment()
        permissionFragment.title = "权限管理"
        resourcesFragment = ResourcesFragment()
        resourcesFragment.title = "资源管理"
        operationFragment = OperationFragment()
        operationFragment.title = "运营管理"

        meFragment = MeFragment()
        meFragment.title = "我的"
        toolbar.setStatusBarFontDark(this)
        toolbar.addOnBackListener({ drawer_layout.openDrawer(GravityCompat.START) })
        setSwipeBackEnable(false)
        toolbar.setTitleContent("师兄在线")
        toolbar.setBackResource(R.mipmap.more)
        permission_manager.setOnClickListener(this)
        resources_manager.setOnClickListener(this)
        operation_manager.setOnClickListener(this)

        adapter = BaseViewpagerAdapter(supportFragmentManager)
        initMenuByAuth()
        viewPager.adapter = adapter

        for (i in 0..1) {
            val tab = tabLayout.newTab()
            tab.customView = getTabView(i, adapter)
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        presenter.getUploadAuth()
    }

    private fun getTabView(position: Int, adapter: BaseViewpagerAdapter): View {
        val view = LayoutInflater.from(this).inflate(R.layout.main_tab_layout, null)
        val textView = view.findViewById<TextView>(R.id.tv_title)
        val imageView = view.findViewById<ImageView>(R.id.iv_icon)
        textView.text = adapter.getItem(position).title
        imageView.setImageResource(tabImages[position])
        return view
    }

    private fun initMenuByAuth() {
        if(AccountHelper.getMenuAuth().isPermissionManagerEnable){
            permission_ll.visibility = View.VISIBLE
            adapter.addFragment(permissionFragment)
            adapter.addFragment(meFragment)
            menuSelect(permission_manager)
        }
        if(AccountHelper.getMenuAuth().isResourceManagerEnable){
            resources_ll.visibility = View.VISIBLE
            if(!AccountHelper.getMenuAuth().isPermissionManagerEnable){
                adapter.addFragment(resourcesFragment)
                adapter.addFragment(meFragment)
                menuSelect(resources_manager)
            }
        }
        if(AccountHelper.getMenuAuth().isOperationManagerEnable){
            operation_ll.visibility = View.VISIBLE
            if(!AccountHelper.getMenuAuth().isPermissionManagerEnable&&!AccountHelper.getMenuAuth().isResourceManagerEnable){
                adapter.addFragment(operationFragment)
                adapter.addFragment(meFragment)
                menuSelect(operation_manager)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onClick(v: View?) {
        drawer_layout.closeDrawer(GravityCompat.START)
        tabLayout.getTabAt(0)?.select()
        viewPager.currentItem = 0
        menuSelect(v!!)
    }

    private fun menuSelect(view: View) {
        when (view) {
            permission_manager -> {//权限管理
                permission_manager.isSelected = true
                resources_manager.isSelected = false
                operation_manager.isSelected = false
                adapter.replaceFragment(0, permissionFragment)
                tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_title)?.text = "权限管理"
            }
            resources_manager -> {//资源管理
                permission_manager.isSelected = false
                resources_manager.isSelected = true
                operation_manager.isSelected = false
                adapter.replaceFragment(0, resourcesFragment)
                tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_title)?.text = "资源管理"
            }
            operation_manager -> {//运营管理
                permission_manager.isSelected = false
                resources_manager.isSelected = false
                operation_manager.isSelected = true
                adapter.replaceFragment(0, operationFragment)
                tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_title)?.text = "运营管理"
            }
        }
    }


    /**
     * 双击返回桌面
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 1000) {
                showToast("再次点击退出程序！")
                exitTime = System.currentTimeMillis()
            } else {
                onBackPressed()
            }
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }


}
