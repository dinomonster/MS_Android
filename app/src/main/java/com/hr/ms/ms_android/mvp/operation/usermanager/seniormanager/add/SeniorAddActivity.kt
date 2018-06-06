package com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.TagListBean
import com.hr.ms.ms_android.bean.UserSelectBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.tagmanager.TagManagerActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.seniorinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SeniorAddActivity : BaseActivity(), View.OnClickListener, SeniorAddContract.View {
    private var presenter: SeniorAddPresenter? = null
    private var userBean: UserSelectBean.Lists? = null
    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var type: Int? = null
    private var identity: Int? = null
    private var field: Int? = null

    private var localImgPath: String? = ""
    private var imageurl: String? = ""
    private var isUploading: Boolean = false

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.seniorinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("完善资料")
        presenter = SeniorAddPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        userBean = intent.getParcelableExtra(CommonConstants.BEAN)
        username_et.setText(userBean?.userName)
        base_intro_et.setText(userBean?.userIntro)
        imageurl = userBean?.userImg
        ImageLoadUtils.loadCropCircleImage(this, userBean?.userImg, image_iv, R.drawable.default_head)
        ViewUtils.setOnClickListeners(this, pre_tv, sub_tv, chooseimg_ll, type_ll, identity_ll, field_ll)
        setSpan(type_title_tv, identity_title_tv, field_title_tv, intro_title_tv)

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
            type_ll -> {
                BottomMenuDialog().showBottomDialog(
                        this,
                        arrayOf("导师", "客座嘉宾"),
                        BottomClickListener().click {
                                type_tv.text = "导师"
                                type = 1
                        },
                        BottomClickListener().click {
                                type_tv.text = "客座嘉宾"
                                type = 2
                        }
                )
            }
            identity_ll -> {
                var bundle = Bundle()
                bundle.putInt(CommonConstants.TYPE, CodeStringUtils.tagTypeCode[1])
                nextActivityForResult(TagManagerActivity::class.java, CodeStringUtils.tagTypeCode[1], bundle)
            }
            field_ll -> {
                var bundle = Bundle()
                bundle.putInt(CommonConstants.TYPE, CodeStringUtils.tagTypeCode[2])
                nextActivityForResult(TagManagerActivity::class.java, CodeStringUtils.tagTypeCode[2], bundle)
            }
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
                presenter?.setSenior(userBean?.userId, type, identity, field, intro_et.text.toString(), base_intro_et.text.toString(),imageurl,username_et.text.toString())
            }
        }
    }

    override fun success() {
        dialogHelper?.showChooseDialog("导师身份开通成功！\n是否立即开通私塾？","查看详情","开通私塾",{

        }, {

        })
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
            CodeStringUtils.tagTypeCode[1] -> {
                var tagbean = data?.getParcelableExtra<TagListBean.Lists>(CommonConstants.BEAN)
                identity = tagbean?.tagId
                identity_tv.text = tagbean?.tagName
            }
            CodeStringUtils.tagTypeCode[2] -> {
                var tagbean = data?.getParcelableExtra<TagListBean.Lists>(CommonConstants.BEAN)
                field = tagbean?.tagId
                field_tv.text = tagbean?.tagName
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
