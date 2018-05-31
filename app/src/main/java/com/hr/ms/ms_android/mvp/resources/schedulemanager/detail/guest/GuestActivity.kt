package com.hr.ms.ms_android.mvp.resources.schedulemanager.detail.guest

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.GuestBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.resources.schedulemanager.detail.guest.detail.GuestDetailActivity
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class GuestActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener{
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: GuestAdapter
    private var pageNo = 1

    private lateinit var typeAdapter:ListDropDownAdapter
    override fun setViewId(): Int {
        return R.layout.recycler_activity
    }

    override fun initData() {
        toolbar.setOnClickListener { onBackPressed() }
        toolbar.addOnMoreListener {
            var intent = Intent(this, GuestDetailActivity::class.java)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
            startActivity(intent)
        }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("嘉宾")
        toolbar.setMoreResource(R.drawable.ic_add)
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = GuestAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, GuestDetailActivity::class.java)
            intent.putExtra(CommonConstants.BEAN,adapter.data[position] as GuestBean)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_DETAIL)
            startActivity(intent)
        }

        setData()


    }

    private fun setData(){
        val beans = ArrayList<GuestBean>()
        for(i in 1 .. 30) {
            var bean = GuestBean()
            bean.name = "黄明华"
            bean.type = "企业家/高管 "
            bean.territory = "其它"
            bean.shareNo = "1"
            bean.shareTime = "2018-04-29 10:00-16:00"
            bean.shareTheme = "分享主题"
            bean.shareContent = "分享内容分享内容分享内容分享内容分享内容分享内容分享内容分享内容分享内容"
            beans.add(bean)
        }
        adapter.showSinglePage(beans)
    }



    override fun onRefresh() {
        refreshLayout.isRefreshing = false
    }


}
