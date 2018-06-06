package com.hr.ms.ms_android.mvp.operation.livemanager.detail

import android.content.Intent
import android.provider.MediaStore
import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ScheduleBean
import com.hr.ms.ms_android.bean.TeacherBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.operation.activitymanager.detail.guestdetail.ActivityGuestDetailActivity
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.live_info_layout.*
import kotlinx.android.synthetic.main.livedetail_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

class LiveDetailActivity : BaseActivity(), View.OnClickListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var bean: ScheduleBean? = null

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null
    private lateinit var adapter: LiveGuestAdapter

    private var status :Int = -1

    override fun setViewId(): Int {
        return R.layout.livedetail_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setTitleContent("直播详情")
        toolbar.setStatusBarFontDark(this)
        bean = intent.getParcelableExtra("ScheduleBean")


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

        adapter = LiveGuestAdapter(recyclerView)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        adapter.setOnItemClickListener { adapter, view, position ->
            var intent = Intent()
            intent.setClass(context, ActivityGuestDetailActivity::class.java)
            intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
            intent.putExtra(CommonConstants.BEAN, adapter.data[position] as TeacherBean)
            startActivity(intent)
        }

        val beans = ArrayList<TeacherBean>()
        for(i in 1 .. 6) {
            var bean = TeacherBean()
            beans.add(bean)
        }
        adapter.showSinglePage(beans)

        //初始化监听
        ViewUtils.setOnClickListeners(this,cover_ll,coverbig_ll,starttime_ll,status_ll)
        //根据页面的状态来改变页面
        status = intent.getIntExtra(CommonConstants.ACTIVITYSTATUS,-1)
        initByStatus()
    }

    private fun initByStatus(){
        when(status){
            CommonConstants.STATUS_DETAIL->{//详情状态
                ViewUtils.setIsEnable(false,cover_ll,coverbig_ll,title_et,starttime_ll,themeName_et,status_ll,url_et,watchnum_et,ordercount_et)
                ViewUtils.setIsVisibility(View.GONE,status_arrow,starttime_arrow,tv_save)
                ViewUtils.setIsVisibility(View.VISIBLE,recyclerView,liveguest_ll)
                ViewUtils.setTextHint("输入",title_et,themeName_et,url_et,watchnum_et,ordercount_et)
                toolbar.setMoreResource(R.drawable.ic_more_vertical)
                toolbar.addOnMoreListener {
//                    BottomMenuDialog().showBottomDialog(
//                            this,
//                            arrayOf("编辑详情","添加嘉宾"),
//                            object : BottomMenuDialog.BootomListener {
//                                override fun onClick(which: Int) {
//                                    status = CommonConstants.STATUS_EDIT
//                                    initByStatus()
//                                }
//                            },
//                            object : BottomMenuDialog.BootomListener {
//                                override fun onClick(which: Int) {
//                                    var intent = Intent()
//                                    intent.setClass(context, ActivityGuestDetailActivity::class.java)
//                                    intent.putExtra(CommonConstants.ACTIVITYSTATUS, CommonConstants.STATUS_ADD)
//                                    startActivity(intent)
//                                }
//                            }
//                    )
                }
            }
            CommonConstants.STATUS_EDIT->{//编辑状态
                ViewUtils.setIsEnable(true,cover_ll,coverbig_ll,title_et,starttime_ll,themeName_et,status_ll,url_et,watchnum_et,ordercount_et)
                ViewUtils.setIsVisibility(View.VISIBLE,status_arrow,starttime_arrow,tv_save)
                ViewUtils.setIsVisibility(View.GONE,recyclerView,liveguest_ll)
                ViewUtils.setTextHint("输入",title_et,themeName_et,url_et,watchnum_et,ordercount_et)
                tv_save.text = "保存"
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
            CommonConstants.STATUS_ADD->{//新增状态
                ViewUtils.setIsEnable(true,cover_ll,coverbig_ll,title_et,starttime_ll,themeName_et,status_ll,url_et,watchnum_et,ordercount_et)
                ViewUtils.setIsVisibility(View.VISIBLE,status_arrow,starttime_arrow)
                ViewUtils.setIsVisibility(View.GONE,recyclerView,liveguest_ll)
                ViewUtils.setTextHint("设置",title_et,themeName_et,url_et,watchnum_et,ordercount_et)
                tv_save.text = "提交"
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cover_ll -> {
                getImageDialog?.show()
            }
            R.id.coverbig_ll -> {
                getImageDialog?.show()
            }
            R.id.status_ll -> {

            }
            R.id.starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                    //                    date_tv.text = TimeUtils.milliseconds2String3(millseconds)
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
    }


}
