package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo.immanager

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.AppManager
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ImManager
import com.hr.ms.ms_android.bean.UserSelectBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.chooseuser.EventChooseUserActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.CodeStringUtils.imManagerTypeCode
import com.hr.ms.ms_android.utils.CodeStringUtils.imManagerTypeString
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.TextChangeListener
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.immanager_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class ImManagerActivity : BaseActivity(), View.OnClickListener, ImManagerContract.View {
    private val LOACTIONCODE: Int = 1000
    private var presenter: ImManagerPresenter? = null
    private var bean: ImManager? = null
    private var userBean: UserSelectBean.Lists? = null

    private var imManagerType: Int? = null

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.immanager_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("直播辅导员信息")
        presenter = ImManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, type_ll, next_tv)
        setSpan(type_title_tv, freenum_title_tv)
        setData()

    }

    fun setData() {
        title_tv.text = intent.getStringExtra(CommonConstants.MSG)
        userBean = intent.getParcelableExtra(CommonConstants.USERBEAN)
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        if (userBean != null) {
            ImageLoadUtils.loadCropCircleImage(this, userBean?.userImg, iv_image)
            name_tv.text = userBean?.userName
            next_tv.text = "立即添加"
        } else {
            ImageLoadUtils.loadCropCircleImage(this, bean?.imManagerUserImg, iv_image)
            name_tv.text = bean?.imManagerUserName
            freenum_et.setText(bean?.freeNum?.toString())
            imManagerType = bean?.imManagerType
            type_tv.text = CodeStringUtils.getImManagerTypeString(bean?.imManagerType)

            freenum_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.freeNum?.toString()) })
            edit(false)
            next_tv.text = "保存"
        }

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
            type_ll -> {
                BottomMenuDialog().showBottomDialog(
                        this,
                        imManagerTypeString, BottomClickListener().click {
                    edit(imManagerTypeCode[0] != bean?.imManagerType)
                    imManagerType = imManagerTypeCode[0]
                    type_tv.text = imManagerTypeString[0]
                }, BottomClickListener().click {
                    edit(imManagerTypeCode[1] != bean?.imManagerType)
                    imManagerType = imManagerTypeCode[1]
                    type_tv.text = imManagerTypeString[1]
                })
            }
            next_tv -> {
                if (TextUtils.isEmpty(bean?.imManagerInfoId)) {
                    presenter?.addEventImManager(intent.getStringExtra(CommonConstants.ID), userBean?.userId?.toString(), imManagerType, freenum_et.text.toString())
                } else {
                    presenter?.editEventImManager(bean?.imManagerInfoId, imManagerType, freenum_et.text.toString())
                }
            }
        }
    }

    override fun success() {
        AppManager.getAppManager().finishActivity(EventChooseUserActivity::class.java)
        onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            LOACTIONCODE -> {

            }
        }
    }

}
