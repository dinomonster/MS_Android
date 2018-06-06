package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer.add

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.AppManager
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.Coorganizer
import com.hr.ms.ms_android.bean.UserSelectBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.chooseuser.EventChooseUserActivity
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.TextChangeListener
import kotlinx.android.synthetic.main.addcoorganizer_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class AddCoorganizerActivity : BaseActivity(), View.OnClickListener, AddCoorganizerContract.View {
    private var presenter: AddCoorganizerPresenter? = null
    private var bean: Coorganizer? = null
    private var userBean: UserSelectBean.Lists? = null
    private var guestId: String? = null

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var localImgPath: String? = ""
    private var imageurl: String? = ""
    private var isUploading: Boolean = false

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.addcoorganizer_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("承办方信息")
        presenter = AddCoorganizerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        setSpan(name_title_tv, image_title_tv, freenum_title_tv)
        ViewUtils.setOnClickListeners(this, chooseimg_ll, save_tv)
        setData()

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

    fun setData() {
        title_tv.text = intent.getStringExtra(CommonConstants.MSG)
        userBean = intent.getParcelableExtra(CommonConstants.USERBEAN)
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        if (userBean != null) {
            ImageLoadUtils.loadCropCircleImage(this, userBean?.userImg, iv_image)
            name_tv.text = userBean?.userName
            save_tv.text = "立即添加"
        } else {
            guestId = bean?.coorganizerId?.toString()
            save_tv.text = "保存"
            ImageLoadUtils.loadCropCircleImage(this, bean?.coorganizerUserImg, iv_image)
            ImageLoadUtils.loadCropCircleImage(this, bean?.coorganizerLogo, logo_iv)
            imageurl = bean?.coorganizerLogo
            name_tv.text = bean?.coorganizerUserName
            name_et.setText(bean?.coorganizerName)
            url_et.setText(bean?.coorganizerUrl)
            freenum_et.setText(bean?.freeNum?.toString())
            edit(false)

            name_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.coorganizerName) })
            url_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.coorganizerUrl) })
            freenum_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.freeNum?.toString()) })
        }

    }

    fun edit(status: Boolean) {
        save_tv.isEnabled = status
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
            save_tv -> {
                if (isUploading) {
                    showToast("图片上传中...")
                    return
                }
                if (TextUtils.isEmpty(bean?.coorganizerInfoId)) {
                    presenter?.addEventCoorganizer(intent.getStringExtra(CommonConstants.ID), userBean?.userId?.toString(), name_et.text.toString(), imageurl, url_et.text.toString(), freenum_et.text.toString())
                } else {
                    presenter?.editEventCoorganizer(bean?.coorganizerInfoId, name_et.text.toString(), imageurl, url_et.text.toString(), freenum_et.text.toString())
                }
            }
            chooseimg_ll -> {
                presenter?.checkExternalStoragePermission(this)
            }
        }
    }

    override fun success(id: String) {
        AppManager.getAppManager().finishActivity(EventChooseUserActivity::class.java)
        onBackPressed()
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
                ImageLoadUtils.loadImage(this, localImgPath, logo_iv)
                isUploading = true
                presenter?.uploadImg(localImgPath)
                edit(true)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                localImgPath = imageUtils?.getCameraImage(resultCode, data)
                ImageLoadUtils.loadImage(this, localImgPath, logo_iv)
                isUploading = true
                presenter?.uploadImg(localImgPath)
                edit(true)
            }
        }
    }

    override fun uploadImgSucess(url: String?) {
        isUploading = false
        imageurl = url
    }


    override fun uploadImgFail() {
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
