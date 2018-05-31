package com.hr.ms.ms_android.mvp.operation.interviewmanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.R.id.*
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ScheduleBean
import com.hr.ms.ms_android.mvp.operation.interviewmanager.edit.InterviewEditActivity
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.search_filter_add_recycler_activity.*
import java.util.*


class InterviewManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener{
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: InterviewManagerAdapter
    private var pageNo = 1

    private val popupViews = ArrayList<View>()
    private val headers = arrayListOf("开始时间","结束时间")
    override fun setViewId(): Int {
        return R.layout.search_filter_add_recycler_activity
    }

    override fun initData() {
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = InterviewManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, InterviewEditActivity::class.java)
            intent.putExtra("ScheduleBean",adapter.data[position] as ScheduleBean)
            startActivity(intent)
        }

        fab.setOnClickListener { nextActivity(InterviewEditActivity::class.java) }

        setData()


    }

    private fun setData(){
        val beans = ArrayList<ScheduleBean>()
        for(i in 1 .. 30) {
            var bean = ScheduleBean()
            bean.cover = R.mipmap.ic_launcher
            bean.themeName = "2月5第四活动"
            bean.teacher = "李四"
            bean.address = "广东省深圳市宝安区七星创意工场右侧阁楼103室"
            bean.desc = "简介"
            bean.societyCount = 100
            bean.studentCount = 200
            bean.entryFee = "¥200"
            bean.activityDate = "2018-07-05 14:00"
            bean.signDate = "2018-07-05 14:00"
            bean.type = "待发布"
            beans.add(bean)
        }
        adapter.showSinglePage(beans)
    }

    private fun setFilterMenu(){
        popupViews.add(View(this))
        popupViews.add(View(this))
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
