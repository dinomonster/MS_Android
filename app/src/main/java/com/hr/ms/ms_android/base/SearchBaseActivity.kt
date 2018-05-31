package com.hr.ms.ms_android.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.text.TextUtils
import android.view.View
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.search.SearchActivity
import kotlinx.android.synthetic.main.search_bar2.*
import kotlinx.android.synthetic.main.toolbar_layout.*


abstract class SearchBaseActivity : BaseActivity() {

    protected var searchKey: String? = ""

    override fun initData() {
        initBar()
    }

    private fun initBar() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setMoreResource(R.drawable.icon_search)
        toolbar.setMoreTransitionName("search")
        toolbar.addOnMoreListener { turnWithAnimation() }
        search_status_bar.setStatusBarDark(this)
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
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, toolbar.moreView, "search")
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
            if (TextUtils.isEmpty(searchKey)){
                search_bar.visibility = View.GONE
                toolbar.visibility = View.VISIBLE
            }
            tv_search_content.text = searchKey
            searchFresh()
        }
    }

    protected abstract fun searchFresh()

}
