package com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.setscholar

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.IdentityBeanList
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.utils.CodeStringUtils
import com.hr.ms.ms_android.utils.ViewUtils
import kotlinx.android.synthetic.main.setscholar_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SetScholarActivity : BaseActivity(), View.OnClickListener, SetScholarContract.View {
    private lateinit var presenter: SetScholarPresenter
    private var dateType: String? = null
    private lateinit var bean: IdentityBeanList.Lists
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }


    override fun setViewId(): Int {
        return R.layout.setscholar_activity
    }

    override fun initData() {
        toolbar.setTitleContent("学霸设置")
        toolbar.setStatusBarFontDark(this)
        toolbar.addOnBackListener { onBackPressed() }
        bean = intent.getParcelableExtra(CommonConstants.BEAN)
        presenter = SetScholarPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        ViewUtils.setOnClickListeners(this, scholar_ll, submit_tv)

        name_tv.text = bean.userName
        mobile_tv.text = bean.mobile
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.scholar_ll -> {
                MaterialDialog.Builder(context)
                        .title("选择学霸类型")
                        .items("永久学霸", "非永久学霸")
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            scholar_tv.text = text
                            when (which){
                                0->{
                                    dateType = "0"
                                    year_ll.visibility = View.GONE
                                }
                                1->{
                                    dateType = "-1"
                                    year_ll.visibility = View.VISIBLE
                                }
                            }
                        })
                        .show()
            }
            R.id.submit_tv -> {
                if(dateType == null){
                    showToast("请选择学霸类型")
                    return
                }
                presenter.setAgentOrScholar(bean?.accId, CodeStringUtils.roleTypeCode[1],if(dateType== "-1") year_et.text.toString() else dateType ,null)
            }
        }
    }


    override fun success() {
        onBackPressed()
    }

}
