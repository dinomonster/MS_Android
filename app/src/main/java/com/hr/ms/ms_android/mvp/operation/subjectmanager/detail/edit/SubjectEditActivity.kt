package com.hr.ms.ms_android.mvp.operation.subjectmanager.detail.edit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.SubjectDetailBean
import com.hr.ms.ms_android.bean.TagListBean
import com.hr.ms.ms_android.bean.UserSelectBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.tagmanager.TagManagerActivity
import com.hr.ms.ms_android.mvp.userchoose.UserChooseActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.CodeStringUtils.tagTypeCode
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import kotlinx.android.synthetic.main.subjectedit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SubjectEditActivity : BaseActivity(), View.OnClickListener, SubjectEditContract.View {
    private val TEACHERSELECT:Int = 1000
    private lateinit var presenter: SubjectEditPresenter
    private lateinit var bean: SubjectDetailBean
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }


    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var isUploading: Boolean = false

    private var imageurl: String? = ""
    private var anchorId: Int? = -1
    private var isLessonExtend: Int? = -1
    private var tagId: Int? = -1
    private var localImgPath: String? = ""

    override fun setViewId(): Int {
        return R.layout.subjectedit_activity
    }

    override fun initData() {
        presenter = SubjectEditPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setTitleContent("课题编辑")
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        ViewUtils.setOnClickListeners(this, cover_ll, teacher_ll, starttime_ll, push_ll, tag_ll, submit_tv, reupload_tv, reselect_tv)
        ViewUtils.setIsVisibility(View.GONE, source_arrow, type_arrow, private_ll_ll)

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
        desc_et.setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
        bean = intent.getParcelableExtra<SubjectDetailBean>(CommonConstants.BEAN)
        showData(bean)
    }


    override fun uploadImgSucess(url: String?) {
        imageurl = url
    }

    override fun uploadImgFail() {
        uploadfail_ll.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.push_ll -> {
                MaterialDialog.Builder(this)
                        .title("推至课程库")
                        .items("是", "否")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            when (which) {
                                0 -> {
                                    push_tv.text = "是"
                                    gradeprice_ll.visibility = View.VISIBLE
                                    isLessonExtend = 1
                                }
                                1 -> {
                                    push_tv.text = "否"
                                    gradeprice_ll.visibility = View.GONE
                                    gradeprice_et.setText("")
                                    isLessonExtend = 0
                                }
                            }
                        })
                        .show()
            }
            R.id.teacher_ll -> {
                nextActivityForResult(UserChooseActivity::class.java, TEACHERSELECT)
            }
            R.id.cover_ll,R.id.reselect_tv -> {
                presenter.checkExternalStoragePermission(this)
            }
            R.id.tag_ll -> {
                var bundle = Bundle()
                bundle.putInt(CommonConstants.TYPE, tagTypeCode[0])
                nextActivityForResult(TagManagerActivity::class.java, tagTypeCode[0], bundle)
            }
            R.id.starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                    starttime_tv.text = TimeUtils.milliseconds2String(millseconds)
                }?.build()?.show(supportFragmentManager, "all")
            }
            R.id.submit_tv -> {
                if (isUploading && TextUtils.isEmpty(imageurl)) {
                    showToast("图片上传中...")
                    return
                }
                presenter.editTopic(bean.topicId!!, anchorId!!, bean.topicType!!, title_et.text.toString(),
                        if (TextUtils.isEmpty(imageurl)) bean.topicImg else imageurl,
                        desc_et.text.toString(), starttime_tv.text.toString(),
                        price_et.text.toString(), isLessonExtend!!,
                        gradeprice_et.text.toString(), tagId!!)
            }
            R.id.reupload_tv -> {
                presenter.uploadImg(localImgPath)
                uploadfail_ll.visibility = View.GONE
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            CommonConstants.GET_PHOTOS_IMAGE -> {
                // 从相册中选取图片
                localImgPath = imageUtils?.getPhotosImage(resultCode, data)
                ImageLoadUtils.loadImage(this, localImgPath, cover_iv)
                isUploading = true
                uploadfail_ll.visibility = View.GONE
                presenter.uploadImg(localImgPath)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                localImgPath = imageUtils?.getCameraImage(resultCode, data)
                ImageLoadUtils.loadImage(this, localImgPath, cover_iv)
                isUploading = true
                uploadfail_ll.visibility = View.GONE
                presenter.uploadImg(localImgPath)
            }
            TEACHERSELECT -> {
                var bean = data?.getParcelableExtra<UserSelectBean.Lists>(CommonConstants.BEAN)
                anchorId = bean?.userId
                teacher_tv.text = bean?.userName
            }
            CodeStringUtils.tagTypeCode[0] -> {
                var bean = data?.getParcelableExtra<TagListBean.Lists>(CommonConstants.BEAN)
                tagId = bean?.tagId
                tag_tv.text = bean?.tagName
            }
        }

    }

    fun showData(bean: SubjectDetailBean?) {
        ImageLoadUtils.loadImage(this, bean?.topicImg, cover_iv)
        title_et.setText(bean?.topicTitle)
        type_tv.text = CodeStringUtils.getTopicTypeString(bean?.topicType)
        tag_tv.text = bean?.tagName
        tagId = bean?.tagId
        source_tv.text = bean?.dataSource
        if (bean?.beginTime != null) starttime_tv.text = TimeUtils.milliseconds2String(bean?.beginTime!!)
        teacher_tv.text = bean?.memberName
        anchorId = bean?.accId
        price_et.setText("" + bean?.seriesPrice)
        desc_et.setText(bean?.topicDesc)
        if (bean?.isLessonExtend == 0) {
            push_tv.text = "否"
            gradeprice_ll.visibility = View.GONE
        } else {
            push_tv.text = "是"
            gradeprice_ll.visibility = View.VISIBLE
        }
        gradeprice_et.setText(bean?.gradePrice.toString())
        isLessonExtend = bean?.isLessonExtend

    }

    override fun editSucess() {
        onBackPressed()
    }

    override fun startSelectPic() {
        getImageDialog?.show()
    }

    override fun showSetPermissionDialog(s: String?) {
        MaterialDialog.Builder(this)
                .title("帮助")
                .content("当前应用缺少" + s + "权限。\n请点击“设置”-“权限”-打开所需权限。\n最后点击两次后退按钮即可返回。")
                .positiveText("设置")
                .negativeText("取消")
                .onPositive { dialog, which ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                }
                .show()
    }

}
