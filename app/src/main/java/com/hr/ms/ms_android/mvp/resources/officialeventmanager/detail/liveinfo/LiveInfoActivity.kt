package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.ChooseBean
import com.hr.ms.ms_android.bean.ImManager
import com.hr.ms.ms_android.bean.OfficialEventDetailBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.chooseuser.EventChooseUserActivity
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo.immanager.ImManagerActivity
import com.hr.ms.ms_android.utils.ViewUtils
import com.hr.ms.ms_android.widget.TextChangeListener
import com.hr.ms.ms_android.widget.dialog.BottomClickListener
import com.hr.ms.ms_android.widget.dialog.BottomMenuDialog
import kotlinx.android.synthetic.main.liveinfo_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class LiveInfoActivity : BaseActivity(), View.OnClickListener, LiveInfoContract.View {
    private var presenter: LiveInfoPresenter? = null
    private var bean: OfficialEventDetailBean? = null
    private var adapter: LiveInfoAdapter? = null
    private var isFirst: Boolean = true

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.liveinfo_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("直播信息")
        presenter = LiveInfoPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        adapter = LiveInfoAdapter(recyclerView)
        adapter?.isUseEmpty(false)
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            var data = adapter.data[position] as ImManager
            when (view.id) {
                R.id.more_iv -> {
                    BottomMenuDialog().showBottomDialog(
                            this,
                            arrayOf("编辑", "删除"), BottomClickListener().click {
                        var intent = Intent(this, ImManagerActivity::class.java)
                        intent.putExtra(CommonConstants.BEAN, data)
                        intent.putExtra(CommonConstants.ID, bean?.officialEvent?.officialEventInfoId)
                        intent.putExtra(CommonConstants.MSG, bean?.officialEvent?.officialEventTitle)
                        startActivity(intent)
                    }, BottomClickListener().click {
                        presenter?.deleteImManagerInfo(data.imManagerInfoId)
                    })
                }
            }
        }
        recyclerView.adapter = adapter
        ViewUtils.setOnClickListeners(this, add_tv, next_tv, copy_push_tv, copy_watch_tv)
        setSpan(book_title_tv, watch_title_tv)
        edit(false)
    }

    override fun onResume() {
        super.onResume()
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }

    fun setData() {
        title_tv.text = bean?.officialEvent?.officialEventTitle
        book_et.setText(bean?.liveinfo?.liveBookNumBase?.toString())
        actualbook_tv.text = bean?.liveinfo?.liveBookNum?.toString()
        watch_et.setText(bean?.liveinfo?.liveWatchNumBase?.toString())
        actualwatch_tv.text = bean?.liveinfo?.liveWatchNum?.toString()
        tip_et.setText(bean?.liveinfo?.chatroomMsg)
        push_tv.text = bean?.liveinfo?.pushStreameUrl
        watch_tv.text = bean?.liveinfo?.playUrl

        book_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.liveinfo?.liveBookNum?.toString()) })
        watch_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.liveinfo?.liveWatchNum?.toString()) })
        tip_et.addTextChangedListener(TextChangeListener().textChange { p0, p1, p2, p3 -> edit(p0.toString() != bean?.liveinfo?.chatroomMsg) })

    }

    fun edit(status: Boolean) {
        next_tv.isEnabled = status
    }

    fun setSpan(vararg view: TextView) {
        for (v in view) {
            var span = SpannableString(v.text)
            span.setSpan(ForegroundColorSpan(resources.getColor(R.color.focus_red_text)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            v.text = span
        }
    }

    override fun showDetail(bean: OfficialEventDetailBean?) {
        this.bean = bean
        adapter?.showMultiPage(bean?.imManagerList, 1)
        if (isFirst) setData()
        isFirst = false
    }

    override fun onClick(p0: View?) {
        when (p0) {
            add_tv -> {
                var intent = Intent(this, EventChooseUserActivity::class.java)
                intent.putExtra(CommonConstants.BEAN, ChooseBean(ImManagerActivity::class.java))
                intent.putExtra(CommonConstants.ID, bean?.officialEvent?.officialEventInfoId)
                intent.putExtra(CommonConstants.MSG, bean?.officialEvent?.officialEventTitle)
                startActivity(intent)
            }
            copy_push_tv -> {
                val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = cmb.primaryClip
                clipData.addItem(ClipData.Item(push_tv.text?.trim()))
                cmb.primaryClip = clipData
            }
            copy_watch_tv -> {
                val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = cmb.primaryClip
                clipData.addItem(ClipData.Item(watch_tv.text?.trim()))
                cmb.primaryClip = clipData
            }
            next_tv -> {
                if (TextUtils.isEmpty(bean?.liveinfo?.liveInfoId)) {
                    presenter?.addEventLiveInfo(intent.getStringExtra(CommonConstants.ID), tip_et.text.toString(), book_et.text.toString(), watch_et.text.toString())
                } else {
                    presenter?.editEventLiveInfo(bean?.liveinfo?.liveInfoId, tip_et.text.toString(), book_et.text.toString(), watch_et.text.toString())
                }
            }
        }
    }

    override fun saveSuccess() {
    }

    override fun deleteSuccess() {
        presenter?.getOfficialEventDetail(intent.getStringExtra(CommonConstants.ID))
    }


}
