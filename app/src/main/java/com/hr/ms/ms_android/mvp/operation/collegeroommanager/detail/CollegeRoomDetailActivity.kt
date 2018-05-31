package com.hr.ms.ms_android.mvp.operation.collegeroommanager.detail

import android.view.View
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CollegeRoomListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.collegeroomdetail_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CollegeRoomDetailActivity : BaseActivity(), View.OnClickListener {
    private var bean: CollegeRoomListBean.Lists? = null
    override fun getPresenter(): MvpPresenter? {
        return null
    }

    override fun setViewId(): Int {
        return R.layout.collegeroomdetail_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setBackgroundColor(resources.getColor(R.color.bg))
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        toolbar.setTitleContent(bean?.collegeRoomName)
        showInfo()
        ViewUtils.setOnClickListeners(this, collegeroom_info_ll, agent_ll, stage_user_ll)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            collegeroom_info_ll -> {
            }
            agent_ll -> {
            }
            stage_user_ll -> {
            }
        }
    }


    fun showInfo() {
        ImageLoadUtils.loadCropCircleImage(this, bean?.collegeRoomImg, image_iv, R.drawable.default_head)
        name_tv.text = bean?.collegeRoomName
    }


}
