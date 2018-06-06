package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.sponsor

import android.content.Intent
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ChooseBean
import com.hr.ms.ms_android.bean.OfficialEventDetailBean
import com.hr.ms.ms_android.bean.Sponsor
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.chooseuser.EventChooseUserActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.sponsor.add.AddSponsorActivity
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.sponsorinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SponsorInfoActivity : BaseActivity(), SponsorInfoContract.View {
    private var presenter: SponsorPresenter? = null
    private var adapter: SponsorInfoAdapter? = null
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
        toolbar.setTitleContent("赞助商信息")
        presenter = SponsorPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        adapter = SponsorInfoAdapter(null)
        adapter?.setOnItemClickListener { adapter, view, position ->
            if ((adapter.data[position] as Sponsor).itemType == 1) {
                var intent = Intent(this, EventChooseUserActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, ChooseBean(AddSponsorActivity::class.java))
                intent.putExtra(CommonConstants.ID, bean?.officialEvent?.officialEventInfoId)
                intent.putExtra(CommonConstants.MSG, bean?.officialEvent?.officialEventTitle)
                startActivity(intent)
            }
        }
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            var data = adapter.data[position] as Sponsor
            when (view.id) {
                R.id.more_iv -> {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("编辑", "删除"), BottomClickListener().click {
                        var intent = Intent(this, AddSponsorActivity::class.java)
                        intent.putExtra(CommonConstants.MSG, bean?.officialEvent?.officialEventTitle)
                        intent.putExtra(CommonConstants.BEAN, data)
                        startActivity(intent)
                    }, BottomClickListener().click {
                        presenter?.deleteEventSponorInfo(data.sponsorInfoId)
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
        if (bean?.sponsorList == null) bean?.sponsorList = ArrayList()
        (bean?.sponsorList as ArrayList).add(Sponsor(sponsorItemType = 1))
        this.bean = bean
        adapter?.showMultiPage(bean?.sponsorList, 1)
    }

}
