package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.baseinfo

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ChooseBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.FlowInfoEditActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.OfficialEventDetailActivity
import com.hr.ms.ms_android.utils.CodeStringUtils.activityTypeCode
import com.hr.ms.ms_android.utils.CodeStringUtils.activityTypeString2
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import com.jzxiang.pickerview.data.Type
import kotlinx.android.synthetic.main.oecreatebaseinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class OECreateBaseInfoActivity : BaseActivity(), View.OnClickListener, OECreateBaseInfoContract.View {
    private var presenter: OECreateBaseInfoPresenter? = null
    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var localImgPath: String? = ""
    private var imageurl: String? = ""
    private var isUploading: Boolean = false

    private var type: Int? = activityTypeCode[3]
    private var startTime: Long? = null
    private var endTime: Long? = null

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.oecreatebaseinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("基本信息")
        presenter = OECreateBaseInfoPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, starttime_ll, endtime_ll, next_tv, chooseimg_ll, activitytype_ll)
        setSpan(name_title_tv, image_title_tv, intro_title_tv, tip_title_tv, starttime_title_tv, endtime_title_tv, activitytype_title_tv)

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

        activitytype_tv.text = activityTypeString2[2]
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
            activitytype_ll -> {
                BottomMenuDialog().showBottomDialog(
                        this,
                        activityTypeString2, BottomClickListener().click {
                    type = activityTypeCode[1]
                    activitytype_tv.text = activityTypeString2[0]
                }, BottomClickListener().click {
                    type = activityTypeCode[2]
                    activitytype_tv.text = activityTypeString2[1]
                }, BottomClickListener().click {
                    type = activityTypeCode[3]
                    activitytype_tv.text = activityTypeString2[2]
                })
            }
            chooseimg_ll -> {
                presenter?.checkExternalStoragePermission(this)
            }
            starttime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.ALL)?.setCallBack { timePickerView, millseconds ->
                    startTime = millseconds
                    starttime_tv.text = TimeUtils.milliseconds2String(millseconds)
                }?.build()?.show(supportFragmentManager, "All")
            }
            endtime_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setType(Type.ALL)?.setCallBack { timePickerView, millseconds ->
                    endTime = millseconds
                    endtime_tv.text = TimeUtils.milliseconds2String(millseconds)
                }?.build()?.show(supportFragmentManager, "All")
            }
            next_tv -> {
                if (isUploading) {
                    showToast("图片上传中...")
                    return
                }
                presenter?.addOfficialEvent(name_et.text.toString(), imageurl, intro_et.text.toString(), tip_et.text.toString(), type, startTime, endTime)
            }
        }
    }

    override fun success(id: String) {
        var intent = Intent(this, FlowInfoEditActivity::class.java)
        intent.putExtra(CommonConstants.ID, id)
        intent.putExtra(CommonConstants.BEAN, ChooseBean(OfficialEventDetailActivity::class.java))
        startActivity(intent)
    }

    override fun uploadImgSucess(url: String?) {
        isUploading = false
        imageurl = url
    }


    override fun uploadImgFail() {
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
                ImageLoadUtils.loadImage(this, localImgPath, image_iv)
                isUploading = true
                presenter?.uploadImg(localImgPath)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                localImgPath = imageUtils?.getCameraImage(resultCode, data)
                ImageLoadUtils.loadImage(this, localImgPath, image_iv)
                isUploading = true
                presenter?.uploadImg(localImgPath)
            }
        }
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
