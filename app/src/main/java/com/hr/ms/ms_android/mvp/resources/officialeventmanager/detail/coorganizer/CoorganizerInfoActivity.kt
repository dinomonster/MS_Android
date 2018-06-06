package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer

import android.content.Intent
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ChooseBean
import com.hr.ms.ms_android.bean.Coorganizer
import com.hr.ms.ms_android.bean.OfficialEventDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.chooseuser.EventChooseUserActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.coorganizer.add.AddCoorganizerActivity
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.sponsorinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class CoorganizerInfoActivity : BaseActivity(), CoorganizerInfoContract.View {
    private var presenter: CoorganizerPresenter? = null
    private var adapter: CoorganizerInfoAdapter? = null
    private var bean: OfficialEventDetailBean? = null
    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.sponsorinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("承办方信息")
        presenter = CoorganizerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        adapter = CoorganizerInfoAdapter(null)
        adapter?.setOnItemClickListener { adapter, view, position ->
            if ((adapter.data[position] as Coorganizer).itemType == 1) {
                var intent = Intent(this, EventChooseUserActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, ChooseBean(AddCoorganizerActivity::class.java))
                intent.putExtra(CommonConstants.ID, bean?.officialEvent?.officialEventInfoId)
                intent.putExtra(CommonConstants.MSG, bean?.officialEvent?.officialEventTitle)
                startActivity(intent)
            }
        }
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            var data = adapter.data[position] as Coorganizer
            when (view.id) {
                R.id.more_iv -> {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("编辑", "删除"), BottomClickListener().click {
                        var intent = Intent(this, AddCoorganizerActivity::class.java)
                        intent.putExtra(CommonConstants.MSG, bean?.officialEvent?.officialEventTitle)
                        intent.putExtra(CommonConstants.BEAN, data)
                        startActivity(intent)
                    }, BottomClickListener().click {
                        presenter?.deleteEventSponorInfo(data.coorganizerInfoId)
                    })
                }
            }
        }
        recyclerView.adapter = adapter
    }

    override fun deleteSuccess() {
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }

    override fun onResume() {
        super.onResume()
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }


    override fun showDetail(bean: OfficialEventDetailBean?) {
        title_tv.text = bean?.officialEvent?.officialEventTitle
        if (bean?.coorganizerList == null) bean?.coorganizerList = ArrayList()
        (bean?.coorganizerList as ArrayList).add(Coorganizer(coorganizerItemType = 1))
        this.bean = bean
        adapter?.showMultiPage(bean?.coorganizerList, 1)
    }

}
