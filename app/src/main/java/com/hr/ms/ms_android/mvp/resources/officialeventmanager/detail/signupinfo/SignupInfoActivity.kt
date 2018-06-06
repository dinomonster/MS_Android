package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.signupinfo

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.LocationBean
import com.hr.ms.ms_android.bean.Offlineinfo
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.map.MapSearchActivity
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.TextChangeListener
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.signupinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SignupInfoActivity : BaseActivity(), View.OnClickListener, SignupInfoContract.View {
    private val LOACTIONCODE: Int = 1000
    private var presenter: SignupInfoPresenter? = null
    private var bean: Offlineinfo? = null

    private var signTime: Long? = null
    private var latitude: String? = null
    private var longitude: String? = null

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.signupinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("报名信息")
        presenter = SignupInfoPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, signup_ll, address_ll, next_tv)
        setSpan(signup_title_tv, address_title_tv, addressdetail_title_tv, contacts_title_tv, phone_title_tv, society_title_tv, student_title_tv, price_title_tv)
        setData()
        edit(false)
    }

    fun setData() {
        title_tv.text = intent.getStringExtra(CommonConstants.MSG)
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        signuptime_tv.text = TimeUtils.milliseconds2String(bean?.attendanceDate)
        signTime = bean?.attendanceDate
        address_tv.text = bean?.activityAddress
        addressdetail_et.setText(bean?.detailAddress)
        contacts_et.setText(bean?.contactName)
        phone_et.setText(bean?.contactTel)
        society_et.setText(bean?.socialNum?.toString())
        student_et.setText(bean?.schoolNum?.toString())
        societyalreadysign_tv.text = bean?.socialEntErNum?.toString()
        studentalreadysign_tv.text = bean?.schoolEntErNum?.toString()
        price_et.setText(bean?.price?.toString())
        latitude = bean?.latitude
        longitude = bean?.longitude

        addressdetail_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.detailAddress) })
        contacts_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.contactName) })
        phone_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.contactTel) })
        society_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.socialNum?.toString()) })
        student_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.schoolNum?.toString()) })
        price_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.price?.toString()) })

    }

    fun edit(status: Boolean) {
        next_tv.isEnabled = status
    }

    fun setSpan(vararg view: TextView) {
        for (v in view) {
            var span = SpannableString(v.text)
            span.setSpan(ForegroundColorSpan(resources.getColor(R.color.focus_red_text)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            v.text = span
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            signup_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                    edit(millseconds != bean?.attendanceDate)
                    signTime = millseconds
                    signuptime_tv.text = TimeUtils.milliseconds2String(millseconds)
                }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
            }
            address_ll -> {
                nextActivityForResult(MapSearchActivity::class.java, LOACTIONCODE)
            }
            next_tv -> {
                if (TextUtils.isEmpty(bean?.offlineInfoId)){
                    presenter?.addEventOfflineInfo(intent.getStringExtra(CommonConstants.ID), signTime, address_tv.text.toString(), addressdetail_et.text.toString(),
                            contacts_et.text.toString(), phone_et.text.toString(), latitude, longitude, society_et.text.toString(), student_et.text.toString(), price_et.text.toString())
                }else {
                    presenter?.editEventOfflineInfo(bean?.offlineInfoId, signTime, address_tv.text.toString(), addressdetail_et.text.toString(),
                            contacts_et.text.toString(), phone_et.text.toString(), latitude, longitude, society_et.text.toString(), student_et.text.toString(), price_et.text.toString())
                }
            }
        }
    }

    override fun success() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            LOACTIONCODE -> {
                var locationBean = data?.getParcelableExtra<LocationBean>(CommonConstants.BEAN)
                address_tv.text = locationBean?.address
                latitude = locationBean?.latitude?.toString()
                longitude = locationBean?.longitude?.toString()
                edit(locationBean?.address != bean?.activityAddress)
            }
        }
    }

}
