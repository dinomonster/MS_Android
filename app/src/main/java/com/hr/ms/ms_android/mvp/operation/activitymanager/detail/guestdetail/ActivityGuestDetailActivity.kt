package com.hr.ms.ms_android.mvp.operation.activitymanager.detail.guestdetail

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
import com.hr.ms.ms_android.bean.GuestBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.userchoose.UserChooseActivity
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.liveguestdetail_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class ActivityGuestDetailActivity : BaseActivity(), View.OnClickListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var bean: GuestBean? = null
    private var status :Int = -1

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null


    override fun setViewId(): Int {
        return R.layout.liveguestdetail_activity
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
        ViewUtils.setOnClickListeners(this,teacher_iv,sharetime_ll)

        //初始化详情页面数据，并填充
//        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        ImageLoadUtils.loadCropCircleImage(this, null, teacher_iv)

        //根据页面的状态来改变页面
        status = intent.getIntExtra(CommonConstants.ACTIVITYSTATUS,-1)
        initByStatus()
    }

    private fun initByStatus(){
        when(status){
            CommonConstants.STATUS_DETAIL->{//详情状态
                ViewUtils.setIsEnable(false,teacher_iv,sharetime_ll,sharetitle_et,company_et,rank_et)
                ViewUtils.setIsVisibility(View.GONE,tv_save,sharetime_arrow)
                ViewUtils.setTextHint("输入",sharetitle_et,company_et,rank_et)
                toolbar.setTitleContent("嘉宾详情")
                toolbar.setMoreResource(R.drawable.ic_more_vertical)
                toolbar.addOnMoreListener {
//                    BottomMenuDialog().showBottomDialog(
//                            this,
//                            arrayOf("删除","编辑"),
//                            object : BottomMenuDialog.BootomListener {
//                                override fun onClick(which: Int) {
//                                    MaterialDialog.Builder(context)
//                                            .title("提示")
//                                            .content("确认删除？")
//                                            .positiveColor(resources.getColor(R.color.success_status))
//                                            .negativeColor(resources.getColor(R.color.tip_text))
//                                            .positiveText("确认")
//                                            .negativeText("取消")
//                                            .onPositive { _, _ ->
//
//                                            }
//                                            .show()
//                                }
//                            },
//                            object : BottomMenuDialog.BootomListener {
//                                override fun onClick(which: Int) {
//                                    status = CommonConstants.STATUS_EDIT
//                                    initByStatus()
//                                }
//                            }
//                    )
                }
            }
            CommonConstants.STATUS_EDIT->{//编辑状态
                ViewUtils.setIsEnable(true,teacher_iv,sharetime_ll,sharetitle_et,company_et,rank_et)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,sharetime_arrow)
                ViewUtils.setTextHint("输入",sharetitle_et,company_et,rank_et)
                toolbar.setTitleContent("编辑嘉宾")
                toolbar.removeMoreResource()
                tv_save.text = "保存"
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
            CommonConstants.STATUS_ADD->{//新增状态
                ViewUtils.setIsEnable(true,teacher_iv,sharetime_ll,sharetitle_et,company_et,rank_et)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,sharetime_arrow)
                ViewUtils.setTextHint("设置",sharetitle_et,company_et,rank_et)
                toolbar.setTitleContent("新增嘉宾")
                tv_save.text = "提交"
                tv_save.setOnClickListener {  }
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.teacher_iv->{
                nextActivity(UserChooseActivity::class.java)
            }
            R.id.sharetime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.YEAR_MONTH_DAY)?.setCallBack { timePickerView, millseconds ->
                    sharetime_tv.text = TimeUtils.milliseconds2String2(millseconds)
                }?.build()?.show(supportFragmentManager, "YEAR_MONTH_DAY")
            }
            R.id.starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.HOURS_MINS)?.setCallBack { timePickerView, millseconds ->
                    sharetime_tv.text = TimeUtils.milliseconds2String6(millseconds)
                }?.build()?.show(supportFragmentManager, "HOURS_MINS")
            }
            R.id.endtime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.HOURS_MINS)?.setCallBack { timePickerView, millseconds ->
                    sharetime_tv.text = TimeUtils.milliseconds2String6(millseconds)
                }?.build()?.show(supportFragmentManager, "HOURS_MINS")
            }
        }
    }


}
