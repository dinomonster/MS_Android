package com.hr.ms.ms_android.mvp.operation.stagemanager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.text.TextUtils
import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.base.BaseViewpagerAdapter
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.main.MainContract
import com.hr.ms.ms_android.mvp.search.SearchActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import kotlinx.android.synthetic.main.search_bar2.*
import kotlinx.android.synthetic.main.stagemanager_activity.*

class StageManagerActivity : BaseActivity(), MainContract.View {
    private lateinit var cityStageFtagment: StageFragment
    private lateinit var personalStageFragment: StageFragment
    private lateinit var adapter: BaseViewpagerAdapter

    private var searchKey: String? = ""

    override fun getPresenter(): MvpPresenter? {
        return null
    }

    override fun setViewId(): Int {
        return R.layout.stagemanager_activity
    }

    override fun initData() {
        initBar()
        cityStageFtagment = StageFragment()
        var cityBundle = Bundle()
        cityBundle.putInt(CommonConstants.TYPE, CodeStringUtils.stageCodeArry[1])
        cityStageFtagment.arguments = cityBundle
        cityStageFtagment.title = "城市驿站"

        personalStageFragment = StageFragment()
        var personalBundle = Bundle()
        personalBundle.putInt(CommonConstants.TYPE, CodeStringUtils.stageCodeArry[0])
        personalStageFragment.arguments = personalBundle
        personalStageFragment.title = "个人驿站"

        adapter = BaseViewpagerAdapter(supportFragmentManager)
        adapter.addFragment(cityStageFtagment)
        adapter.addFragment(personalStageFragment)
        viewPager.adapter = adapter
        viewPager.setSmoothScrollEnabled(true)
        viewPager.setPagingEnabled(true)
        tabLayout.setupWithViewPager(viewPager)

    }

    fun setTotal(total: String?) {
        totle_tv.text = "共" + total + "个"
    }

    private fun initBar() {
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        search_status_bar.setStatusBarDark(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) search_tv.transitionName = "search"
        search_tv.setOnClickListener { turnWithAnimation() }
        tv_cancel.setOnClickListener {
            search_bar.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
            searchKey = ""
            searchFresh()
        }
        search_ll.setOnClickListener { searchFresh() }
        tv_search_content.setOnClickListener { turnWithAnimation() }
    }

    @SuppressLint("RestrictedApi")
    private fun turnWithAnimation() {
        var intent = Intent(this, SearchActivity::class.java)
        intent.putExtra(CommonConstants.TYPE, CommonConstants.WITHDRAWMANAGER_SEARCH)
        intent.putExtra(CommonConstants.MSG, "搜索用户名")
        intent.putExtra(CommonConstants.KEY, searchKey)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, search_tv, "search")
            startActivityForResult(intent, 1000, options.toBundle())
        } else {
            startActivityForResult(intent, 1000)
        }
        search_bar.visibility = View.VISIBLE
        toolbar.visibility = View.GONE
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == 1000) {
            searchKey = data?.getStringExtra(CommonConstants.ID)
            if (TextUtils.isEmpty(searchKey)) {
                search_bar.visibility = View.GONE
                toolbar.visibility = View.VISIBLE
            }
            tv_search_content.text = searchKey
            searchFresh()
        }
    }

    private fun searchFresh(){
        if (viewPager.currentItem == 0) {
            cityStageFtagment.setkey(searchKey)
        }else{
            personalStageFragment.setkey(searchKey)
        }
    }
}
