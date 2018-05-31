package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.member

import android.os.Bundle
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.base.BaseViewpagerAdapter
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.main.MainContract
import com.hr.ms.ms_android.utils.CodeStringUtils
import kotlinx.android.synthetic.main.member_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MemberActivity : BaseActivity(), MainContract.View {
    private lateinit var agentFragment: MemberFragment
    private lateinit var scholarFragment: MemberFragment
    private lateinit var studentFragment: MemberFragment
    private lateinit var adapter: BaseViewpagerAdapter

    override fun getPresenter(): MvpPresenter? {
        return null
    }

    override fun setViewId(): Int {
        return R.layout.member_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("驿站成员")

        agentFragment = MemberFragment()
        var agentBundle = Bundle()
        agentBundle.putInt(CommonConstants.TYPE, CodeStringUtils.roleTypeCode[0])
        agentBundle.putInt(CommonConstants.ID, intent.getIntExtra(CommonConstants.ID,-1))
        agentFragment.arguments = agentBundle
        agentFragment.title = "代言"

        scholarFragment = MemberFragment()
        var scholarBundle = Bundle()
        scholarBundle.putInt(CommonConstants.TYPE, CodeStringUtils.roleTypeCode[1])
        scholarBundle.putInt(CommonConstants.ID, intent.getIntExtra(CommonConstants.ID,-1))
        scholarFragment.arguments = scholarBundle
        scholarFragment.title = "学霸"

        studentFragment = MemberFragment()
        var studentBundle = Bundle()
        studentBundle.putInt(CommonConstants.TYPE, CodeStringUtils.roleTypeCode[2])
        studentBundle.putInt(CommonConstants.ID, intent.getIntExtra(CommonConstants.ID,-1))
        studentFragment.arguments = studentBundle
        studentFragment.title = "全部"

        adapter = BaseViewpagerAdapter(supportFragmentManager)
        adapter.addFragment(agentFragment)
        adapter.addFragment(scholarFragment)
        adapter.addFragment(studentFragment)
        viewPager.adapter = adapter
        viewPager.setSmoothScrollEnabled(true)
        viewPager.setPagingEnabled(true)
        tabLayout.setupWithViewPager(viewPager)
    }

}
