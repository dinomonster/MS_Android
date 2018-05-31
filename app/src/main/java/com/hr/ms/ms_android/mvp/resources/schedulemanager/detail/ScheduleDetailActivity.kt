package com.hr.ms.ms_android.mvp.resources.schedulemanager.detail

import android.content.Intent
import android.provider.MediaStore
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ScheduleBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.resources.schedulemanager.detail.guest.GuestActivity
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import com.jzxiang.pickerview.data.Type
import com.smarttop.library.bean.City
import com.smarttop.library.bean.County
import com.smarttop.library.bean.Province
import com.smarttop.library.bean.Street
import com.smarttop.library.widget.BottomDialog
import kotlinx.android.synthetic.main.scheduleedit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class ScheduleDetailActivity : BaseActivity(), View.OnClickListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var bean: ScheduleBean? = null
    private var status :Int = -1

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null


    override fun setViewId(): Int {
        return R.layout.scheduleedit_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        getImageDialog = GetImageDialog(this)
        getImageDialog?.setCanceledOnTouchOutside(true)
        imageUtils = GetImageUtils(this)
        getImageDialog?.setOnGetImageListener(object : GetImageDialog.OnGetImageListener {
            override fun fromPhotos() {
                val intent_album = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent_album, CommonConstants.GET_PHOTOS_IMAGE)
            }

            override fun fromCamera() {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CommonConstants.GET_CAMERA_IMAGE)
            }
        })

        //初始化监听
        ViewUtils.setOnClickListeners(this,photo_iv,area_ll,guest_ll,starttime_ll,endtime_ll,signtime_ll,issuetime_ll)

        //初始化详情页面数据，并填充
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        ImageLoadUtils.loadCropCircleImage(this, null, photo_iv)

        //根据页面的状态来改变页面
        status = intent.getIntExtra(CommonConstants.ACTIVITYSTATUS,-1)
        initByStatus()
    }


    private fun initByStatus(){
        when(status){
            CommonConstants.STATUS_DETAIL->{//详情状态
                ViewUtils.setIsEnable(false,photo_iv,themeName_et,teacher_et,addressdetail_et,activity_desc_et,societyCount_et,studentCount_et,fee_et,starttime_ll,endtime_ll,signtime_ll,area_ll,issuetime_ll,guest_ll)
                ViewUtils.setIsVisibility(View.GONE,tv_save,area_arrow,starttime_arrow,endtime_arrow,signtime_arrow,issuetime_arrow,guest_arrow)
                ViewUtils.setTextHint("输入",themeName_et,teacher_et,addressdetail_et,activity_desc_et,societyCount_et,studentCount_et,fee_et)
                toolbar.setTitleContent("排课详情")
                toolbar.setMoreResource(R.drawable.ic_more_vertical)
                toolbar.addOnMoreListener {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("删除","编辑"),
                            object : BottomMenuDialog.BootomListener {
                                override fun onClick(which: Int) {
                                    MaterialDialog.Builder(context)
                                            .title("提示")
                                            .content("确认删除？")
                                            .positiveColor(resources.getColor(R.color.success_status))
                                            .negativeColor(resources.getColor(R.color.tip_text))
                                            .positiveText("确认")
                                            .negativeText("取消")
                                            .onPositive { _, _ ->

                                            }
                                            .show()
                                }
                            },
                            object : BottomMenuDialog.BootomListener {
                                override fun onClick(which: Int) {
                                    status = CommonConstants.STATUS_EDIT
                                    initByStatus()
                                }
                            }
                    )
                }
            }
            CommonConstants.STATUS_EDIT->{//编辑状态
                ViewUtils.setIsEnable(true,photo_iv,themeName_et,teacher_et,addressdetail_et,activity_desc_et,societyCount_et,studentCount_et,fee_et,starttime_ll,endtime_ll,signtime_ll,area_ll,issuetime_ll,guest_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,area_arrow,starttime_arrow,endtime_arrow,signtime_arrow,issuetime_arrow,guest_arrow)
                ViewUtils.setTextHint("输入",themeName_et,teacher_et,addressdetail_et,activity_desc_et,societyCount_et,studentCount_et,fee_et)
                toolbar.setTitleContent("编辑排课")
                toolbar.removeMoreResource()
                tv_save.text = "保存"
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
            CommonConstants.STATUS_ADD->{//新增状态
                ViewUtils.setIsEnable(true,photo_iv,themeName_et,teacher_et,addressdetail_et,activity_desc_et,societyCount_et,studentCount_et,fee_et,starttime_ll,endtime_ll,signtime_ll,area_ll,issuetime_ll,guest_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,area_arrow,starttime_arrow,endtime_arrow,signtime_arrow,issuetime_arrow,guest_arrow)
                ViewUtils.setTextHint("设置",themeName_et,teacher_et,addressdetail_et,activity_desc_et,societyCount_et,studentCount_et,fee_et)
                toolbar.setTitleContent("新增排课")
                tv_save.text = "提交"
                tv_save.setOnClickListener {  }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.area_ll -> {
                val dialog = BottomDialog(context)
                dialog.setOnAddressSelectedListener({province:Province?, city:City?, county:County?, street:Street?->
                    area_tv.text = (if (province == null) "" else province.name) + (if (city == null) "" else city.name) + (if (county == null) "" else county.name) +
                            if (street == null) "" else street.name
                    dialog?.dismiss()
                })
                dialog.setDialogDismisListener {
                    dialog?.dismiss()
                }
//                dialog.setSelectorAreaPositionListener(this)
                dialog.show()
            }
            R.id.guest_ll->{
                nextActivity(GuestActivity::class.java)
            }
            R.id.photo_iv -> {
                getImageDialog?.show()
            }
            R.id.starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                    starttime_tv.text = TimeUtils.milliseconds2String2(millseconds)
                }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
            }
            R.id.endtime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                    endtime_tv.text = TimeUtils.milliseconds2String2(millseconds)
                }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
            }
            R.id.issuetime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                    issuetime_tv.text = TimeUtils.milliseconds2String2(millseconds)
                }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
            }
            R.id.signtime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                    signtime_tv.text = TimeUtils.milliseconds2String2(millseconds)
                }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        var picturePath: String? = null
        when (requestCode) {
            CommonConstants.GET_PHOTOS_IMAGE -> {
                // 从相册中选取图片
                picturePath = imageUtils?.getPhotosImage(resultCode, data)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                picturePath = imageUtils?.getCameraImage(resultCode, data)
            }
        }
        ImageLoadUtils.loadCropCircleImage(this, picturePath, photo_iv)
    }


}
