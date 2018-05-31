package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CourseListBean
import com.hr.ms.ms_android.bean.SubjectDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.courseedit.CourseAddActivity
import com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.courseedit.CourseEditActivity
import com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.edit.SubjectEditActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.widget.ListDropDownAdapter
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.search_layout.*
import kotlinx.android.synthetic.main.subject_info_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

class SubjectDetailActivity : BaseActivity(), View.OnClickListener, SubjectDetailContract.View, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var presenter: SubjectDetailPresenter
    private var pageNo = 1

    private var bean: SubjectDetailBean? = null

    private var startTime: String = ""
    private var endTime: String = ""
    private var auditStatus: Int = CodeStringUtils.auditStatusCode[0]
    private var isBroadcast: Int = CodeStringUtils.isBroadcastCode[0]
    private lateinit var adapter: SubjectAdapter

    private val popupViews = ArrayList<View>()
    private val headers = arrayListOf("开始时间", "结束时间", "审核状态", "是否录播")
    private lateinit var statusAdapter: ListDropDownAdapter
    private lateinit var isBroadcastAdapter: ListDropDownAdapter

    override fun setViewId(): Int {
        return R.layout.subjectdetail_activity
    }

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun initData() {
        presenter = SubjectDetailPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("课题详情")

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener({ refreshLayout.isRefreshing = false })
        adapter = SubjectAdapter(recyclerView)
        adapter.addHeaderView(layoutInflater.inflate(R.layout.subject_info_layout, null))
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            //            var intent = Intent(this, SubjectDetailActivity::class.java)
//            intent.putExtra("ScheduleBean",adapter.data[position] as ScheduleBean)
//            startActivity(intent)
            var bean = adapter.data[position] as CourseListBean.Lists
            var view = layoutInflater.inflate(R.layout.course_detail_layout, null)
            view.findViewById<TextView>(R.id.coursetitle_tv).text = bean?.title
            view.findViewById<TextView>(R.id.courseaddress_tv).text = bean?.mediaUrl
            view.findViewById<TextView>(R.id.coursestarttime_tv).text = TimeUtils.milliseconds2String(bean?.beginDate)
            view.findViewById<TextView>(R.id.courseremind_tv).text = bean?.remindTime.toString()
            view.findViewById<TextView>(R.id.desc_tv).text = bean?.details
            MaterialDialog.Builder(this)
                    .title("课程详情")
                    .positiveText("编辑")
                    .negativeText("取消")
                    .customView(view, true)
                    .onPositive { dialog, which ->
                        var intent = Intent()
                        intent.putExtra(CommonConstants.BEAN, bean)
                        intent.setClass(context, CourseEditActivity::class.java)
                        startActivity(intent)
                    }
                    .show()
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getListData()
        }, recyclerView)
        adapter.setHeaderFooterEmpty(true, true)


    }

    private fun setFilterMenu() {
        val statusView = ListView(this)
        statusView.dividerHeight = 0
        statusAdapter = ListDropDownAdapter(this, CodeStringUtils.auditStatusString)
        statusView.adapter = statusAdapter

        val isBroadcastView = ListView(this)
        isBroadcastView.dividerHeight = 0
        isBroadcastAdapter = ListDropDownAdapter(this, CodeStringUtils.isBroadcastString)
        isBroadcastView.adapter = isBroadcastAdapter

        statusView.setOnItemClickListener { adapterView, view, position, l ->
            statusAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[2] else CodeStringUtils.auditStatusString[position])
            auditStatus = CodeStringUtils.auditStatusCode[position]
            dropDownMenu.closeMenu()
            onRefresh()
        }

        isBroadcastView.setOnItemClickListener { adapterView, view, position, l ->
            isBroadcastAdapter.setCheckItem(position)
            dropDownMenu.setTabText(if (position === 0) headers[3] else CodeStringUtils.isBroadcastString[position])
            isBroadcast = CodeStringUtils.isBroadcastCode[position]
            dropDownMenu.closeMenu()
            onRefresh()
        }

        popupViews.add(View(this))
        popupViews.add(View(this))
        popupViews.add(statusView)
        popupViews.add(isBroadcastView)
        dropDownMenu.setDropDownMenu(headers, popupViews, View(this))
//        dropDownMenu.setDropDownMenu(headers, popupViews, View.inflate(context, R.layout.recyclerview_layout, null))
//        dropDownMenu.tabClick(0, { view ->
//            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
//                (view as TextView).text = TimeUtils.milliseconds2String2(millseconds)
//                startTime = TimeUtils.milliseconds2String2(millseconds)
//                onRefresh()
//            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
//        })
//        dropDownMenu.tabClick(2, { view ->
//            AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
//                (view as TextView).text = TimeUtils.milliseconds2String2(millseconds)
//                endTime = TimeUtils.milliseconds2String2(millseconds)
//                onRefresh()
//            }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
//        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.edit_tv -> {
                if (bean == null) return
                var intent = Intent()
                intent.putExtra(CommonConstants.BEAN, bean)
                intent.setClass(context, SubjectEditActivity::class.java)
                startActivity(intent)
            }
            R.id.addcourse_tv -> {
                if (bean == null) return
                var intent = Intent()
                intent.putExtra(CommonConstants.BEAN, bean)
                intent.setClass(context, CourseAddActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_search ->{
                onRefresh()
            }
        }
    }


    override fun showDetail(bean: SubjectDetailBean?) {
        this.bean = bean
        ImageLoadUtils.loadImage(this, bean?.topicImg, cover_iv)
        title_tv.text = bean?.topicTitle
        type_tv.text = CodeStringUtils.getTopicTypeString(bean?.topicType)
        tag_tv.text = bean?.tagName
        source_tv.text = bean?.dataSource
        coursenum_tv.text = bean?.lsnCount.toString()
        if (bean?.beginTime != null) starttime_tv.text = TimeUtils.milliseconds2String(bean?.beginTime!!)
        teacher_tv.text = bean?.memberName
        price_tv.text = " ¥" + bean?.seriesPrice
        playnum_tv.text = bean?.watchNum.toString()
        desc_et.text = bean?.introduction + bean?.topicDesc
        desc_et.movementMethod = ScrollingMovementMethod()
        desc_et.setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
        edit_tv.setOnClickListener(this)
        addcourse_tv.setOnClickListener(this)
        tv_search.setOnClickListener(this)
        setFilterMenu()
    }

    override fun showList(list: MutableList<CourseListBean.Lists>?) {
        adapter.showMultiPage(list, pageNo)
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getListData()
    }

    override fun onResume() {
        super.onResume()
        presenter.getTopicDetail(intent.getIntExtra(CommonConstants.ID, -1))
        getListData()
    }

    private fun getListData() {
        presenter.getLessonList(pageNo, intent.getIntExtra(CommonConstants.ID, -1), "", auditStatus, isBroadcast, startTime, endTime)
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({onRefresh()})
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }

}
