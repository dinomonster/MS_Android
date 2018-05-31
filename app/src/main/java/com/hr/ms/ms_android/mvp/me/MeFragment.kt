package com.hr.ms.ms_android.mvp.me

import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseAppCompatFragment

/**
 * Created by Dino on 2018/4/3.
 */
class MeFragment : BaseAppCompatFragment() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    override fun init() {
        stopLoadingAnima()
    }

    override fun getLayoutId(): Int {
        return R.layout.me_fragment
    }

}