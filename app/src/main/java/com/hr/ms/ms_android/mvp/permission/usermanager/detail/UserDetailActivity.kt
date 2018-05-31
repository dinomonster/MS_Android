package com.hr.ms.ms_android.mvp.permission.usermanager.detail

import android.app.Activity
import android.content.Intent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.RoleBean
import com.hr.ms.ms_android.bean.UserManagerBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.mvp.permission.usermanager.rolecheck.RoleCheckActivity
import com.hr.ms.ms_android.mvp.permission.usermanager.roleselect.RoleSelectActivity
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.useredit_activity.*

class UserDetailActivity : BaseActivity() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var userBean: UserManagerBean? = null
    private var status :Int = -1

    override fun setViewId(): Int {
        return R.layout.useredit_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)

        //初始化详情页面数据，并填充
        userBean = intent.getParcelableExtra(CommonConstants.BEAN)
        acount_et.setText(userBean?.account)
        username_et.setText(userBean?.userName)

        //根据页面的状态来改变页面
        status = intent.getIntExtra(CommonConstants.ACTIVITYSTATUS,-1)
        initByStatus()
    }

    private fun initByStatus(){
        when(status){
            CommonConstants.STATUS_DETAIL->{//详情状态
                ViewUtils.setIsEnable(false,username_et,acount_et)
                ViewUtils.setIsVisibility(View.VISIBLE,logincount_ll,lastlogin_ll)
                ViewUtils.setIsVisibility(View.GONE,tv_save,role_arrow)
                ViewUtils.setTextHint("输入",username_et,acount_et)
                toolbar.setTitleContent("用户详情")
                toolbar.setMoreResource(R.drawable.ic_more_vertical)
                toolbar.addOnMoreListener {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("删除","编辑","重置密码"),
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
                            },
                            object : BottomMenuDialog.BootomListener {
                                override fun onClick(which: Int) {

                                }
                            }
                    )
                }
                role_ll.setOnClickListener { nextActivity(RoleCheckActivity::class.java) }
            }
            CommonConstants.STATUS_EDIT->{//编辑状态
                ViewUtils.setIsEnable(true,username_et,acount_et)
                ViewUtils.setIsVisibility(View.VISIBLE,logincount_ll,lastlogin_ll,tv_save,role_arrow)
                ViewUtils.setTextHint("输入",username_et,acount_et)
                toolbar.setTitleContent("编辑用户")
                toolbar.removeMoreResource()
                tv_save.text = "保存"
                role_ll.setOnClickListener { nextActivityForResult(RoleSelectActivity::class.java,100) }
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
            CommonConstants.STATUS_ADD->{//新增状态
                ViewUtils.setIsEnable(true,username_et,acount_et)
                ViewUtils.setIsVisibility(View.GONE,logincount_ll,lastlogin_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,role_arrow)
                ViewUtils.setTextHint("设置",username_et,acount_et)
                toolbar.setTitleContent("新增用户")
                tv_save.text = "提交"
                role_ll.setOnClickListener { nextActivityForResult(RoleSelectActivity::class.java,100) }
                tv_save.setOnClickListener {  }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 100&&resultCode == Activity.RESULT_OK){
            var roleBean = data?.getParcelableExtra<RoleBean>("RoleBean")
            role_tv.text = roleBean?.name
        }
    }

}
