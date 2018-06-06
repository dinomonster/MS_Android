package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.add

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.EventFlow
import com.hr.ms.ms_android.bean.SeniorListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.OrderManager
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.choosenior.ChooseSeniorActivity
import com.hr.ms.ms_android.utils.CodeStringUtils.nodeTypeCode
import com.hr.ms.ms_android.utils.CodeStringUtils.nodeTypeString
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.TextChangeListener
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.addflow_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class AddFlowActivity : BaseActivity(), View.OnClickListener, AddFlowContract.View {
    private var presenter: AddFlowPresenter? = null
    private val CHOOSESENIOR_CODE: Int = 1000
    private var bean: EventFlow? = null
    private var type: Int? = null
    private var guestId: Int? = null
    private var startTime: Long? = null
    private var endTime: Long? = null
    private var justSave: Boolean = true

    private var calendar: Calendar? = null

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.addflow_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("添加活动流程节点")
        presenter = AddFlowPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        setSpan(order_title_tv, noedetype_title_tv, shareguest_title_tv, sharetitle_title_tv, starttime_title_tv, endtime_title_tv)
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        calendar = Calendar.getInstance()
        if (bean != null) {
            calendar?.timeInMillis = if (bean?.flowBeginDate != null) bean?.flowBeginDate!! else 0
            ViewUtils.setIsVisibility(View.GONE, save_tv, saveandadd_tv, noedetype_arrow)
            ViewUtils.setOnClickListeners(this, starttime_ll, endtime_ll, shareguest_ll, save_tv, saveandadd_tv, edit_tv)
            changeNodeType(if (bean?.flowPointType != null) bean?.flowPointType!! - 1 else 0)
            order_et.setText(bean?.flowOrderNum?.toString())
            type = bean?.flowPointType
            shareguest_tv.text = bean?.officialEventGuestVo?.seniorName
            sharetitle_et.setText(bean?.extra2)
            starttime_tv.text = TimeUtils.milliseconds2String6(bean?.flowBeginDate)
            startTime = bean?.flowBeginDate
            endtime_tv.text = TimeUtils.milliseconds2String6(bean?.flowEndDate)
            endTime = bean?.flowEndDate
            guestId = bean?.extra1
            edit(false)
            order_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.flowOrderNum?.toString()) })
            sharetitle_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.extra2) })
        } else {
            calendar?.timeInMillis = intent.getLongExtra(CommonConstants.DATE, 0)
            ViewUtils.setIsVisibility(View.GONE, edit_tv)
            ViewUtils.setOnClickListeners(this, starttime_ll, endtime_ll, noedetype_ll, shareguest_ll, save_tv, saveandadd_tv, edit_tv)
            ViewUtils.setIsVisibility(View.GONE, shareguest_ll, sharetitle_ll)

        }

    }

    fun edit(status: Boolean) {
        edit_tv.isEnabled = status
    }

    fun setSpan(vararg view: TextView) {
        for (v in view) {
            var span = SpannableString(v.text)
            span.setSpan(ForegroundColorSpan(resources.getColor(R.color.focus_red_text)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            v.text = span
        }
    }

    fun changeNodeType(witch: Int) {
        type = nodeTypeCode[witch]
        noedetype_tv.text = nodeTypeString[witch]
        if (witch == 1) {
            ViewUtils.setIsVisibility(View.VISIBLE, shareguest_ll, sharetitle_ll)
        } else {
            ViewUtils.setIsVisibility(View.GONE, shareguest_ll, sharetitle_ll)
        }

    }

    override fun onClick(p0: View?) {
        when (p0) {
            noedetype_ll -> {
                BottomMenuDialog().showBottomDialog(
                        this,
                        nodeTypeString, BottomClickListener().click {
                    changeNodeType(it - 1)
                }, BottomClickListener().click {
                    changeNodeType(it - 1)
                }, BottomClickListener().click {
                    changeNodeType(it - 1)
                }, BottomClickListener().click {
                    changeNodeType(it - 1)
                }, BottomClickListener().click {
                    changeNodeType(it - 1)
                }, BottomClickListener().click {
                    changeNodeType(it - 1)
                })
            }
            shareguest_ll -> {
                nextActivityForResult(ChooseSeniorActivity::class.java, CHOOSESENIOR_CODE)
            }
            starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.HOURS_MINS)?.setCallBack { timePickerView, millseconds ->
                    var startCalendar = Calendar.getInstance()
                    startCalendar.timeInMillis = millseconds
                    calendar?.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY))
                    calendar?.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE))
                    startTime = calendar?.timeInMillis
                    starttime_tv.text = TimeUtils.milliseconds2String6(startTime)
                    edit(startTime != bean?.flowBeginDate)
                }?.build()?.show(supportFragmentManager, "All")
            }
            endtime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.HOURS_MINS)?.setCallBack { timePickerView, millseconds ->
                    var endCalendar = Calendar.getInstance()
                    endCalendar.timeInMillis = millseconds
                    calendar?.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY))
                    calendar?.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE))
                    endTime = calendar?.timeInMillis
                    endtime_tv.text = TimeUtils.milliseconds2String6(endTime)
                    edit(endTime != bean?.flowEndDate)
                }?.build()?.show(supportFragmentManager, "All")
            }
            save_tv -> {
                justSave = true
                presenter?.addEventFlowPoints(intent.getStringExtra(CommonConstants.ID), type, order_et.text.toString(), guestId, sharetitle_et.text.toString(), startTime, endTime)
            }
            saveandadd_tv -> {
                justSave = false
                presenter?.addEventFlowPoints(intent.getStringExtra(CommonConstants.ID), type, order_et.text.toString(), guestId, sharetitle_et.text.toString(), startTime, endTime)
            }
            edit_tv -> {
                presenter?.editEventFlowPoints(bean?.flowInfoId, type, order_et.text.toString(), guestId, sharetitle_et.text.toString(), startTime, endTime)
            }
        }
    }

    override fun success(id: String) {
        if (!justSave) {
            type = null
            guestId = null
            startTime = null
            endTime = null
            ViewUtils.setIsVisibility(View.GONE, shareguest_ll, sharetitle_ll)
            noedetype_tv.text = "请选择"
            shareguest_tv.text = "请选择"
            starttime_tv.text = "请选择"
            endtime_tv.text = "请选择"
            sharetitle_et.setText("")
            order_et.setText(OrderManager.generateOrder())
        } else {
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            CHOOSESENIOR_CODE -> {
                var bean = data?.getParcelableExtra<SeniorListBean.Lists>(CommonConstants.BEAN)
                shareguest_tv.text = bean?.userName
                guestId = bean?.accId
                edit(guestId != this@AddFlowActivity.bean?.extra1)
            }
        }
    }


}
