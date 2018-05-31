package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.courseedit

import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CourseListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.courseedit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CourseEditActivity : BaseActivity(), View.OnClickListener, CourseEditContract.View {
    private lateinit var presenter: CourseEditPresenter
    private lateinit var bean: CourseListBean.Lists
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.courseedit_activity
    }

    override fun initData() {
        presenter = CourseEditPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setTitleContent("课程编辑")
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        ViewUtils.setOnClickListeners(this, starttime_ll, submit_tv)

        desc_et.setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        showData(bean)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                    starttime_tv.text = TimeUtils.milliseconds2String(millseconds)
                }?.build()?.show(supportFragmentManager, "all")
            }
            R.id.submit_tv -> {
                presenter.editLesson(bean.lsnId, coursetitle_et.text.toString(), starttime_tv.text.toString(), bean.curstatus,
                        courseremind_et.text.toString(), bean.isBroadcast, bean.videoId, bean.hourLong,
                        mediaUrl_et.text.toString(), highUrl_et.text.toString(),
                        desc_et.text.toString())
            }
        }
    }


    fun showData(bean: CourseListBean.Lists?) {
        coursetitle_et.setText(bean?.title)
        mediaUrl_et.setText(bean?.mediaUrl)
        highUrl_et.setText(bean?.highUrl)
        starttime_tv.text = TimeUtils.milliseconds2String(bean?.beginDate!!)
        courseremind_et.setText(bean?.remindTime.toString())
        desc_et.setText(bean?.details)
    }

    override fun editSucess() {
        onBackPressed()
    }


}
