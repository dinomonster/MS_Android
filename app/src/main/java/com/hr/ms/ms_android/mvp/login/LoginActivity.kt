package com.hr.ms.ms_android.mvp.login

import android.text.TextUtils
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.AccountHelper
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.main.MainActivity
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created by Dino on 2018/4/3.
 */
class LoginActivity : BaseActivity(),LoginContract.View {
    private lateinit var presenter:LoginPresenter
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.login_layout
    }

    override fun initData() {
        presenter = LoginPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        toolbar.setStatusBarFontDark(this)
        toolbar.addOnBackListener({ onBackPressed() })
        login.setOnClickListener({ presenter.login(account_et.text.toString(),pwd_et.text.toString()) })
        var msg = intent.getStringExtra(CommonConstants.MSG)
        if(!TextUtils.isEmpty(msg))showToast(msg)
    }

    override fun onResume() {
        super.onResume()
        account_et.setText(AccountHelper.getAccount())
        pwd_et.setText(AccountHelper.getPwd())
    }

    override fun loginFail() {
    }

    override fun loginSuccess() {
        onBackPressed()
        nextActivity(MainActivity::class.java)
    }

    override fun accountError(msg:String) {
        account_et.error = msg
    }

    override fun pwdError(msg:String) {
        pwd_et.error = msg
    }
}