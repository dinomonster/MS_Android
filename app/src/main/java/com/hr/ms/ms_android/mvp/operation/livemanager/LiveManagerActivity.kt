package com.hr.ms.ms_android.mvp.operation.livemanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.R.id.back_ll
import com.hr.ms.ms_android.R.id.dropDownMenu
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.TeacherBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.operation.livemanager.detail.LiveDetailActivity
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.search_filter_add_recycler_activity.*
import java.util.*


class LiveManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener{
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: LiveManagerAdapter
    private var pageNo = 1

    private val popupViews = ArrayList<View>()
    private val headers = arrayListOf("开始时间","结束时间")

    override fun setViewId(): Int {
        return R.layout.search_filter_recycler_activity
    }

    override fun initData() {
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
//        toolbar.addOnMoreListener { nextActivity(UserDetailActivity::class.java) }
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = LiveManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, LiveDetailActivity::class.java)
            intent.putExtra("TeacherBean",adapter.data[position] as TeacherBean)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_DETAIL)
            startActivity(intent)
        }
//        fab.setOnClickListener {
//            var intent = Intent(this, LiveDetailActivity::class.java)
//            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
//            startActivity(intent)
//        }

        setData()
    }

    private fun setData(){
        val beans = ArrayList<TeacherBean>()
        for(i in 1 .. 6) {
            var bean = TeacherBean()
            bean.photo = R.mipmap.ic_launcher
            bean.name = "肖南方"
            bean.intro = "简介"
            bean.type = "专家/顾问"
            bean.mobile = "13400000000"
            bean.territory = "战略咨询"
            bean.talkType = "待恰谈"
            bean.fee = "¥50,000"
            bean.talkDate = "2018-01-20 17:38"
            beans.add(bean)
        }
        adapter.showSinglePage(beans)
    }

    private fun setFilterMenu(){


        popupViews.add(TextView(this))
        popupViews.add(TextView(this))
        dropDownMenu.setDropDownMenu(headers,popupViews, View.inflate(context,R.layout.recyclerview_layout,null))
//        dropDownMenu.tabClick(0,{view ->
//            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
//                (view as TextView).text = TimeUtils.milliseconds2String2(millseconds)
//            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
//        })
//        dropDownMenu.tabClick(2,{view ->
//            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
//                (view as TextView).text = TimeUtils.milliseconds2String2(millseconds)
//            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
//        })
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = false
    }


}
