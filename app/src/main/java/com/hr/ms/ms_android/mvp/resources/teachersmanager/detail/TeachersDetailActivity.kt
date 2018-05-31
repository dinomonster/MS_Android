package com.hr.ms.ms_android.mvp.resources.teachersmanager.detail

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
import com.hr.ms.ms_android.bean.TeacherBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.teacheredit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class TeachersDetailActivity : BaseActivity(), View.OnClickListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var bean: TeacherBean? = null
    private var status :Int = -1

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null


    override fun setViewId(): Int {
        return R.layout.teacheredit_activity
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
        ViewUtils.setOnClickListeners(this,photo_iv,type_ll,territory_ll,talktype_ll,date_ll)

        //初始化详情页面数据，并填充
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        name_et.setText(bean?.name)
        desc_et.setText(bean?.intro)
        mobile_et.setText(bean?.mobile)
        fee_et.setText(bean?.fee)
        ImageLoadUtils.loadCropCircleImage(this, null, photo_iv)

        //根据页面的状态来改变页面
        status = intent.getIntExtra(CommonConstants.ACTIVITYSTATUS,-1)
        initByStatus()
    }

    private fun initByStatus(){
        when(status){
            CommonConstants.STATUS_DETAIL->{//详情状态
                ViewUtils.setIsEnable(false,name_et,desc_et,mobile_et,fee_et,photo_iv,type_ll,territory_ll,talktype_ll,date_ll)
                ViewUtils.setIsVisibility(View.GONE,tv_save,type_arrow,territory_arrow,talktype_arrow,date_arrow)
                ViewUtils.setTextHint("输入",name_et,desc_et,mobile_et,fee_et)
                toolbar.setTitleContent("导师详情")
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
                ViewUtils.setIsEnable(true,name_et,desc_et,mobile_et,fee_et,photo_iv,type_ll,territory_ll,talktype_ll,date_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,type_arrow,territory_arrow,talktype_arrow,date_arrow)
                ViewUtils.setTextHint("输入",name_et,desc_et,mobile_et,fee_et)
                toolbar.setTitleContent("编辑导师")
                toolbar.removeMoreResource()
                tv_save.text = "保存"
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
            CommonConstants.STATUS_ADD->{//新增状态
                ViewUtils.setIsEnable(true,name_et,desc_et,mobile_et,fee_et,photo_iv,type_ll,territory_ll,talktype_ll,date_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,type_arrow,territory_arrow,talktype_arrow,date_arrow)
                ViewUtils.setTextHint("设置",name_et,desc_et,mobile_et,fee_et)
                toolbar.setTitleContent("新增导师")
                tv_save.text = "提交"
                tv_save.setOnClickListener {  }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.type_ll -> {
                MaterialDialog.Builder(this)
                        .title("选择导师类型")
                        .items("企业家/高管", "专家/顾问", "教授/学者")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            type_tv.text = text
                        })
                        .show()
            }
            R.id.photo_iv -> {
                getImageDialog?.show()
            }
            R.id.territory_ll -> {
                MaterialDialog.Builder(this)
                        .title("选择研究领域")
                        .items("企业家/高管", "专家/顾问", "教授/学者")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            territory_tv.text = text
                        })
                        .show()
            }
            R.id.talktype_ll -> {
                MaterialDialog.Builder(this)
                        .title("选择洽谈状态")
                        .items("文化管理", "战略管理", "资本架构")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            talktype_tv.text = text
                        })
                        .show()
            }
            R.id.date_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                    date_tv.text = TimeUtils.milliseconds2String3(millseconds)
                }?.build()?.show(supportFragmentManager, "all")
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
