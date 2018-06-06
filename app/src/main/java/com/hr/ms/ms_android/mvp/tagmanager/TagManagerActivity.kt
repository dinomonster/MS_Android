package com.hr.ms.ms_android.mvp.tagmanager

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.widget.EditText
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.InputDialogBean
import com.hr.ms.ms_android.bean.TagListBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource
import com.hr.ms.ms_android.data.repository.ServiceRepository
import kotlinx.android.synthetic.main.recyclerview_layout.*
import kotlinx.android.synthetic.main.tagmanager_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class TagManagerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, TagManagerContract.View {
    private var presenter: TagManagerPresenter? = null
    private var selectPos: Int = -1

    private lateinit var adapter: TagManagerAdapter
    private var pageNo = 1

    //1:课题标签 2:身份标签 3:研究/感兴趣领域 4:企业行业领域
    private var type: Int = 0

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.tagmanager_activity
    }

    override fun initData() {
        super.initData()
        presenter = TagManagerPresenter(this, ServiceRepository.getInstance(ServiceRemoteDataSource.getInstance(), ServiceLocalDataSource.getInstance()))
        type = intent.getIntExtra(CommonConstants.TYPE, 0)
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        when (type) {
            1 -> toolbar.setTitleContent("课题标签")
            2 -> toolbar.setTitleContent("身份标签")
            3 -> toolbar.setTitleContent("研究领域")
            4 -> toolbar.setTitleContent("企业领域")
            else -> toolbar.setTitleContent("标签列表")
        }
        toolbar.setMoreTitleContent("添加")
        toolbar.setBackTitleContent("取消")
        toolbar.addOnMoreListener {
            dialogHelper?.showInputDialog(InputDialogBean("添加标签", "名称", "", "", "取消", "添加"), {
                dialogHelper?.dismissProgressDialog()
            }, {
                dialogHelper?.dismissProgressDialog()
                presenter?.addTag(type, dialogHelper.dialog?.findViewById<EditText>(R.id.input_et)?.text?.toString())
            })
        }

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        refreshLayout.setOnRefreshListener(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = TagManagerAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adp, view, position ->
            selectPos = position
            adapter.setselected(position)
        }
        adapter.setOnLoadMoreListener({
            pageNo++
            getData()
        }, recyclerView)

        next_tv.setOnClickListener {
            if (selectPos != -1) {
                var intent = Intent()
                intent.putExtra(CommonConstants.BEAN, adapter.data[selectPos] as TagListBean.Lists)
                setResult(Activity.RESULT_OK, intent)
                onBackPressed()
            } else {
                showToast("请选择标签")
            }
        }
    }

    override fun addSuccess() {
        onRefresh()
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        pageNo = 1
        getData()
    }

    private fun getData() {
        presenter?.getTagList(pageNo, type, "")
    }

    override fun showList(list: MutableList<TagListBean.Lists>?) {
        adapter.showMultiPage(list, pageNo)
        refreshLayout.isRefreshing = false
    }

    override fun showNetWorkError() {
        refreshLayout.isRefreshing = false
        adapter.showNetWorkErrorView({ onRefresh() })
    }

    override fun showNoData() {
        refreshLayout.isRefreshing = false
        adapter.showNoDataView()
    }

}
