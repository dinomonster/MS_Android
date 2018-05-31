package com.hr.ms.ms_android.mvp.operation.usermanager.edit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.imageload.ImageLoadUtils
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.utils.TimeUtils
import com.hr.ms.ms_android.AppConfig
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.TagListBean
import com.hr.ms.ms_android.bean.UserDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.constants.CommonConstants.ID
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.tagmanager.TagManagerActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.GetImageUtils
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.GetImageDialog
import kotlinx.android.synthetic.main.op_useredit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class UserEditActivity : BaseActivity(), View.OnClickListener, UserEditContract.View {
    private lateinit var presenter: UserEditPresenter
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    private var isEdit = false

    private var getImageDialog: GetImageDialog? = null
    private var imageUtils: GetImageUtils? = null

    private var role: Int? = -1
    private var status: Int? = -1
    private var sex: Int? = -1
    private var identityId: Int? = -1
    private var fieldId: Int? = -1
    private var localImgPath: String? = ""
    private var email: String? = ""

    override fun setViewId(): Int {
        return R.layout.op_useredit_activity
    }

    override fun initData() {
        presenter = UserEditPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        ViewUtils.setOnClickListeners(this, photo_iv, role_ll, identity_ll, territory_ll, sex_ll, birth_ll)

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

        initBar()
        presenter.getAccountVoInfo(intent.getIntExtra(ID, -1))
    }


    private fun initBar() {
        toolbar.setTitleContent("用户详情")
        toolbar.setMoreTitleContent("编辑")
        ViewUtils.setIsEnable(false, photo_iv, name_et, role_ll, identity_ll, territory_ll, company_et, title_et, sex_ll, birth_ll, motto_et, desc_et)
        ViewUtils.setIsVisibility(View.GONE, role_arrow, identity_arrow, territory_arrow, sex_arrow, birth_arrow)
        toolbar.addOnMoreListener {
            if (isEdit) {
                isEdit = false
                toolbar.setMoreTitleContent("编辑")
                ViewUtils.setIsEnable(false, photo_iv, name_et, role_ll, identity_ll, territory_ll, company_et, title_et, sex_ll, birth_ll, motto_et, desc_et)
                ViewUtils.setIsVisibility(View.GONE, role_arrow, identity_arrow, territory_arrow, sex_arrow, birth_arrow)
                presenter.uploadImg(localImgPath)
            } else {
                isEdit = true
                toolbar.setMoreTitleContent("完成")
                ViewUtils.setIsEnable(true, photo_iv, name_et, role_ll, identity_ll, territory_ll, company_et, title_et, sex_ll, birth_ll, motto_et, desc_et)
                ViewUtils.setIsVisibility(View.VISIBLE, role_arrow, identity_arrow, territory_arrow, sex_arrow, birth_arrow)
            }
        }
    }

    override fun uploadImgSucess(url: String?) {
        presenter.editAccount(role!!, status!!, name_et.text.toString(), url, email, desc_et.text.toString(), sex!!, birth_tv.text.toString(), motto_et.text.toString(), identityId!!, fieldId!!, company_et.text.toString(), title_et.text.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.role_ll -> {
                MaterialDialog.Builder(this)
                        .title("选择角色")
                        .items(CodeStringUtils.identityArry)
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            role = CodeStringUtils.identityCodeArry[which]
                            role_tv.text = text
                        })
                        .show()
            }
            R.id.identity_ll -> {
                var bundle = Bundle()
                bundle.putInt(CommonConstants.TYPE, CodeStringUtils.tagTypeCode[1])
                nextActivityForResult(TagManagerActivity::class.java, CodeStringUtils.tagTypeCode[1], bundle)
            }
            R.id.territory_ll -> {
                var bundle = Bundle()
                bundle.putInt(CommonConstants.TYPE, CodeStringUtils.tagTypeCode[2])
                nextActivityForResult(TagManagerActivity::class.java, CodeStringUtils.tagTypeCode[2], bundle)
            }
            R.id.photo_iv -> {
                presenter.checkExternalStoragePermission(this)
            }
            R.id.sex_ll -> {
                MaterialDialog.Builder(this)
                        .title("选择性别")
                        .items(CodeStringUtils.sexStringArray)
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            sex = CodeStringUtils.sexCodeArray[which]
                            sex_tv.text = text
                        })
                        .show()
            }
            R.id.birth_ll -> {
                AppConfig.getInstance()?.getimePicker()?.setCallBack { timePickerView, millseconds ->
                    TimeUtils.milliseconds2String3(millseconds)
                }?.build()?.show(supportFragmentManager, "all")
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
                ImageLoadUtils.loadCropCircleImage(this, localImgPath, photo_iv)
            }
            CommonConstants.GET_CAMERA_IMAGE -> {
                // 相机拍照获得图片
                localImgPath = imageUtils?.getCameraImage(resultCode, data)
                ImageLoadUtils.loadCropCircleImage(this, localImgPath, photo_iv)
            }
            CodeStringUtils.tagTypeCode[1] -> {
                var tagbean = data?.getParcelableExtra<TagListBean.Lists>(CommonConstants.BEAN)
                identityId = tagbean?.tagId
                identity_tv.text = tagbean?.tagName
            }
            CodeStringUtils.tagTypeCode[2] -> {
                var tagbean = data?.getParcelableExtra<TagListBean.Lists>(CommonConstants.BEAN)
                fieldId = tagbean?.tagId
                territory_tv.text = tagbean?.tagName
            }
        }

    }

    override fun showData(bean: UserDetailBean?) {
        status = bean?.status
        role = bean?.role
        sex = bean?.sex
        identityId = bean?.identityId
        fieldId = bean?.fieldId
        email = bean?.email
        ImageLoadUtils.loadCropCircleImage(this, bean?.imageUri, photo_iv)
        name_et.setText(bean?.memberName)
        role_tv.text = CodeStringUtils.getIdentityString(bean?.role)
        identity_tv.text = bean?.identityName
        territory_tv.text = bean?.fieldName
        company_et.setText(bean?.seniorInfoVo?.company)
        title_et.setText(bean?.seniorInfoVo?.title)
        sex_tv.text = CodeStringUtils.getSexString(bean?.sex)
        birth_tv.text = bean?.birth
        motto_et.setText(bean?.motto)
        desc_et.setText(bean?.introduction)
    }

    override fun editSucess() {
        isEdit = false
        initBar()
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
