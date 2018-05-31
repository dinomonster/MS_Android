package com.hr.ms.ms_android.mvp.operation.interviewmanager.edit

import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ScheduleBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.SubjectAdapter
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

class InterviewEditActivity : BaseActivity(), View.OnClickListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var bean: ScheduleBean? = null
    private var isEdit = false

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null
    private lateinit var adapter:SubjectAdapter

    override fun setViewId(): Int {
        return R.layout.subjectdetail_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("访谈详情")
        bean = intent.getParcelableExtra("ScheduleBean")


//        ImageLoadUtils.loadCropCircleImage(this, null, photo_iv)


//        photo_iv.setOnClickListener(this)

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



        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener({refreshLayout.isRefreshing = false})
        adapter = SubjectAdapter(recyclerView)
        adapter.addHeaderView(layoutInflater.inflate(R.layout.subject_info_layout,null))
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
//            var intent = Intent(this, SubjectDetailActivity::class.java)
//            intent.putExtra("ScheduleBean",adapter.data[position] as ScheduleBean)
//            startActivity(intent)
        }
        adapter.setOnLoadMoreListener({

        },recyclerView)


        val beans = ArrayList<ScheduleBean>()
        for(i in 1 .. 5) {
            var bean = ScheduleBean()
            bean.cover = R.mipmap.ic_launcher
            bean.themeName = "2月5第四活动"
            bean.teacher = "李四"
            bean.address = "广东省深圳市宝安区七星创意工场右侧阁楼103室"
            bean.desc = "简介"
            bean.societyCount = 100
            bean.studentCount = 200
            bean.entryFee = "¥200"
            bean.activityDate = "2018-07-05 14:00"
            bean.signDate = "2018-07-05 14:00"
            bean.type = "待发布"
            beans.add(bean)
        }
//        adapter.showMultiPage(beans,1)

    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.area_ll -> {
            }
            R.id.guest_ll->{
            }
            R.id.photo_iv -> {
                getImageDialog?.show()
            }
            R.id.date_ll -> {
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
//        ImageLoadUtils.loadCropCircleImage(this, picturePath, photo_iv)
    }


}
