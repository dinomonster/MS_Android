package com.hr.ms.ms_android.mvp.operation.stagemanager.detail.stageedit

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.StageListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import kotlinx.android.synthetic.main.stageedit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class StageEditActivity : BaseActivity(), View.OnClickListener, StageEditContract.View {
    private var presenter: StageEditPresenter? = null
    private var bean: StageListBean.Lists? = null
    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var localImgPath: String? = ""
    private var imageurl: String? = ""
    private var isUploading: Boolean = false
    private var isInfoChange: Boolean = false

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.stageedit_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("驿站资料")
        toolbar.setMoreTitleContent("保存")
        toolbar.addOnMoreListener {
            //            if (isUploading && TextUtils.isEmpty(imageurl)) {
//                showToast("图片上传中...")
//            }else {
//                presenter?.editStage(bean?.stageId, name_et.text.toString(), imageurl, intro_et.text.toString(), city_et.text.toString())
//            }
        }
        presenter = StageEditPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        user_tv.text = bean?.stageOwnerName
        name_et.setText(bean?.stageName)
        city_et.setText(bean?.stageName)
        ImageLoadUtils.loadCropCircleImage(this, bean?.stageImg, image_iv)

        ViewUtils.setOnClickListeners(this, chooseimg_ll)
        addTextWathcer(name_et, city_et, intro_et)

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
    }


    fun addTextWathcer(vararg view: EditText) {
        for (v in view) {
            v.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    isInfoChange = true
                }
            })
        }
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            chooseimg_ll -> {
                presenter?.checkExternalStoragePermission(this)
            }
        }
    }

    override fun uploadImgSucess(url: String?) {
        imageurl = url
    }

    override fun editSucess() {
    }

    override fun createECardSucess() {
        onBackPressed()
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
                ImageLoadUtils.loadCropCircleImage(this, localImgPath, image_iv)
                isUploading = true
                presenter?.uploadImg(localImgPath)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                localImgPath = imageUtils?.getCameraImage(resultCode, data)
                ImageLoadUtils.loadCropCircleImage(this, localImgPath, image_iv)
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
