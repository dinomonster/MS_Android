package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.setagent

import android.content.Intent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.IdentityBeanList
import com.hr.ms.ms_android.bean.StageSelectListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.stagechoose.StageChooseActivity
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.setagent_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SetAgentActivity : BaseActivity(), View.OnClickListener, SetAgentContract.View {
    private val STAGESELECT = 1000
    private lateinit var presenter: SetAgentPresenter
    private var stageId: Int? = null
    private var dateType: String? = null
    private lateinit var bean: IdentityBeanList.Lists
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }


    override fun setViewId(): Int {
        return R.layout.setagent_activity
    }

    override fun initData() {
        toolbar.setTitleContent("代言人设置")
        toolbar.setStatusBarFontDark(this)
        toolbar.addOnBackListener { onBackPressed() }
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        presenter = SetAgentPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, stage_ll, submit_tv, agenttype_ll)

        name_tv.text = bean.userName
        mobile_tv.text = bean.mobile
    }


    override fun onClick(v: View?) {
        when (v) {
            stage_ll -> {
                nextActivityForResult(StageChooseActivity::class.java, STAGESELECT)
            }
            agenttype_ll -> {
                MaterialDialog.Builder(context)
                        .title("选择代言人类型")
                        .items("永久代言人", "非永久代言人")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            agenttype_tv.text = text
                            when (which) {
                                0 -> {
                                    dateType = "0"
                                    year_ll.visibility = View.GONE
                                }
                                1 -> {
                                    dateType = "-1"
                                    year_ll.visibility = View.VISIBLE
                                }
                            }
                        })
                        .show()
            }
            submit_tv -> {
                if(dateType == null){
                    showToast("请选择代言人类型")
                    return
                }
                presenter.setAgentOrScholar(bean?.accId, CodeStringUtils.roleTypeCode[0], if(dateType== "-1") year_et.text.toString() else dateType, stageId)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            STAGESELECT -> {
                var bean = data?.getParcelableExtra<StageSelectListBean.Lists>(CommonConstants.BEAN)
                stageId = bean?.stageId
                stagey_tv.text = bean?.stageName
            }
        }
    }

    override fun success() {
        onBackPressed()
    }

}
