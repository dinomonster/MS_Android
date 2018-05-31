package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.courseedit

import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.SubjectDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.courseedit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CourseAddActivity : BaseActivity(), View.OnClickListener, CourseEditContract.View {
    private lateinit var presenter: CourseEditPresenter
    private lateinit var bean: SubjectDetailBean
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.courseedit_activity
    }

    override fun initData() {
        presenter = CourseEditPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setTitleContent("新增课程")
        submit_tv.text = "新增"
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)

        ViewUtils.setOnClickListeners(this, starttime_ll, submit_tv)

        desc_et.setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        bean = intent.getParcelableExtra(CommonConstants.BEAN)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                    starttime_tv.text = TimeUtils.milliseconds2String(millseconds)
                }?.build()?.show(supportFragmentManager, "all")
            }
            R.id.submit_tv -> {
                presenter.addLesson(bean.topicId, coursetitle_et.text.toString(), starttime_tv.text.toString(),
                        courseremind_et.text.toString(),
                        mediaUrl_et.text.toString(), highUrl_et.text.toString(),
                        desc_et.text.toString())
            }
        }
    }


    override fun editSucess() {
        onBackPressed()
    }


}
