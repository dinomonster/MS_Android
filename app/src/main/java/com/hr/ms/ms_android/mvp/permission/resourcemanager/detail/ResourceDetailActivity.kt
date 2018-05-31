package com.hr.ms.ms_android.mvp.permission.resourcemanager.detail

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ResourceBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.resourceedit_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*



class ResourceDetailActivity : BaseActivity(), View.OnClickListener {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private var bean: ResourceBean? = null
    private var status :Int = -1

    override fun setViewId(): Int {
        return R.layout.resourceedit_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)

        //初始化详情页面数据，并填充
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        name_et.setText(bean?.name)
        type_tv.text = bean?.type
        tag_et.setText(bean?.tag)
        isactivate_tv.text = bean?.isActivate
        url_et.setText(bean?.url)

        type_ll.setOnClickListener(this)
        isactivate_ll.setOnClickListener(this)
        //根据页面的状态来改变页面
        status = intent.getIntExtra(CommonConstants.ACTIVITYSTATUS,-1)
        initByStatus()
    }

    private fun initByStatus(){
        when(status){
            CommonConstants.STATUS_DETAIL->{//详情状态
                ViewUtils.setIsEnable(false,name_et,url_et,tag_et,type_ll,isactivate_ll)
                ViewUtils.setIsVisibility(View.GONE,tv_save,type_arrow,isactivate_arrow)
                ViewUtils.setTextHint("输入",name_et,url_et,tag_et)
                toolbar.setTitleContent("菜单详情")
                toolbar.setMoreResource(R.drawable.ic_more_vertical)
                toolbar.addOnMoreListener {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("删除","编辑"),
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
                            }
                    )
                }
            }
            CommonConstants.STATUS_EDIT->{//编辑状态
                ViewUtils.setIsEnable(true,name_et,url_et,tag_et,type_ll,isactivate_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,type_arrow,isactivate_arrow)
                ViewUtils.setTextHint("输入",name_et,url_et,tag_et)
                toolbar.setTitleContent("编辑菜单")
                toolbar.removeMoreResource()
                tv_save.text = "保存"
                tv_save.setOnClickListener {
                    status = CommonConstants.STATUS_DETAIL
                    initByStatus()
                }
            }
            CommonConstants.STATUS_ADD->{//新增状态
                ViewUtils.setIsEnable(true,name_et,url_et,tag_et,type_ll,isactivate_ll)
                ViewUtils.setIsVisibility(View.VISIBLE,tv_save,type_arrow,isactivate_arrow)
                ViewUtils.setTextHint("设置",name_et,url_et,tag_et)
                toolbar.setTitleContent("新增菜单")
                tv_save.text = "提交"
                tv_save.setOnClickListener {  }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.type_ll -> {
                MaterialDialog.Builder(this)
                        .title("选择类型")
                        .items("系统", "模块", "菜单", "资源")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            type_tv.text =text
                        })
                        .show()
            }
            R.id.isactivate_ll -> {
                MaterialDialog.Builder(this)
                        .title("是否激活")
                        .items("是", "否")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            isactivate_tv.text =text
                            true
                        })
                        .show()
            }
        }
    }

}
