package com.hr.ms.ms_android.mvp.operation.collegeroommanager.add

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.AppManager
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.CollegeRoomDetailBean
import com.hr.ms.ms_android.bean.SeniorListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.operation.collegeroommanager.chooseusernext.CollegeRoomChooseUserActivity
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import kotlinx.android.synthetic.main.collegeroomadd_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CollegeRoomAddActivity : BaseActivity(), View.OnClickListener, CollegeRoomContract.View {
    private var presenter: CollegeRoomPresenter? = null
    private var seniorBean: SeniorListBean.Lists? = null
    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var localImgPath: String? = ""
    private var imageurl: String? = ""
    private var isUploading: Boolean = false

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.collegeroomadd_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("私塾资料")
        presenter = CollegeRoomPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        seniorBean = intent.getParcelableExtra(CommonConstants.BEAN)
        username_tv.text = seniorBean?.userName
        identity_tv.text = seniorBean?.seniorIdentity + "丨" + seniorBean?.fieldName
        type_tv.text = seniorBean?.userIdentityType
        senior_title_tv.text = seniorBean?.userTitle
        senior_title_tv.movementMethod = ScrollingMovementMethod()
        ImageLoadUtils.loadCropCircleImage(this, seniorBean?.userImg, user_image_iv, R.drawable.default_head)
        ImageLoadUtils.loadImage(this, seniorBean?.userImg, collegeroom_image_iv, R.drawable.default_back)
        imageurl = seniorBean?.userImg
        ViewUtils.setOnClickListeners(this, pre_tv, sub_tv, chooseimg_ll)
        setSpan(name_title_tv, city_title_tv, collegeroom_image_title_tv,collegeroom_intro_title_tv)

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

    fun setSpan(vararg view: TextView) {
        for (v in view) {
            var span = SpannableString(v.text)
            span.setSpan(ForegroundColorSpan(resources.getColor(R.color.focus_red_text)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            v.text = span
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            chooseimg_ll -> {
                presenter?.checkExternalStoragePermission(this)
            }
            pre_tv -> {
                onBackPressed()
            }
            sub_tv -> {
                if (isUploading) {
                    showToast("图片上传中...")
                    return
                }
                presenter?.addCollegeroom(name_et.text.toString() + name_suffix_tv.text.toString(), seniorBean?.accId, city_et.text.toString(), imageurl, price_et.text.toString(), vip_intro_et.text.toString(), collegeroom_intro_et.text.toString())
            }
        }
    }

    override fun success() {
        dialogHelper?.showChooseDialog(name_et.text.toString() + "私塾开通成功!", "私塾详情", "私塾列表", {

        }, {
            AppManager.getAppManager().finishActivity(CollegeRoomChooseUserActivity::class.java)
            onBackPressed()
        })
    }

    override fun showCollegeRoomDetailBean(bean: CollegeRoomDetailBean?) {
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
                ImageLoadUtils.loadImage(this, localImgPath, collegeroom_image_iv)
                isUploading = true
                presenter?.uploadImg(localImgPath)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                localImgPath = imageUtils?.getCameraImage(resultCode, data)
                ImageLoadUtils.loadImage(this, localImgPath, collegeroom_image_iv)
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
