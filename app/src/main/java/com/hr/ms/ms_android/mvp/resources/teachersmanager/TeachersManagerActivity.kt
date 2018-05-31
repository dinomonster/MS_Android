package com.hr.ms.ms_android.mvp.resources.teachersmanager

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ListView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.R.id.*
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.TeacherBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.constants.CommonConstants.BEAN
import com.hr.ms.ms_android.mvp.resources.teachersmanager.detail.TeachersDetailActivity
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.search_filter_add_recycler_activity.*
import java.util.*


class TeachersManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener{
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter: TeachersManagerAdapter
    private var pageNo = 1

    private val popupViews = ArrayList<View>()
    private val headers = arrayListOf("是否入库","洽谈状态","导师类型")
    private val isStorage = arrayListOf("全部", "已入库", "未入库")
    private val talkType = arrayListOf("全部", "待洽谈", "洽谈成功","洽谈失败")
    private val teacherType = arrayListOf("全部", "企业家/高管", "专家/顾问","教授/学者")

    private lateinit var storageAdapter:ListDropDownAdapter
    private lateinit var talkTypeAdapter:ListDropDownAdapter
    private lateinit var teacherTypeAdapter:ListDropDownAdapter
    override fun setViewId(): Int {
        return R.layout.search_filter_add_recycler_activity
    }

    override fun initData() {
        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        setFilterMenu()
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        adapter = TeachersManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent(this, TeachersDetailActivity::class.java)
            intent.putExtra(BEAN,adapter.data[position] as TeacherBean)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_DETAIL)
            startActivity(intent)
        }

        fab.setOnClickListener {
            var intent = Intent(this, TeachersDetailActivity::class.java)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
            startActivity(intent)
        }

        setData()
    }

    private fun setData(){
        val beans = ArrayList<TeacherBean>()
        for(i in 1 .. 30) {
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
        val storageView = ListView(this)
        storageView.dividerHeight = 0
        storageAdapter = ListDropDownAdapter(this, isStorage)
        storageView.adapter = storageAdapter
        val talkTypeView = ListView(this)
        talkTypeView.dividerHeight = 0
        talkTypeAdapter = ListDropDownAdapter(this, talkType)
        talkTypeView.adapter = talkTypeAdapter
        val teacherTypeView = ListView(this)
        teacherTypeView.dividerHeight = 0
        teacherTypeAdapter = ListDropDownAdapter(this, teacherType)
        teacherTypeView.adapter = teacherTypeAdapter

        storageView.setOnItemClickListener { adapterView, view, position, l ->
            storageAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[0] else isStorage[position])
            dropDownMenu.closeMenu()
        }
        talkTypeView.setOnItemClickListener { adapterView, view, position, l ->
            talkTypeAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[1] else talkType[position])
            dropDownMenu.closeMenu()
        }
        teacherTypeView.setOnItemClickListener { adapterView, view, position, l ->
            teacherTypeAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[2] else teacherType[position])
            dropDownMenu.closeMenu()
        }


        popupViews.add(storageView)
        popupViews.add(talkTypeView)
        popupViews.add(teacherTypeView)
        dropDownMenu.setDropDownMenu(headers,popupViews, View.inflate(context,R.layout.recyclerview_layout,null))
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = false
    }


}
