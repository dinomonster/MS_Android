package com.hr.ms.ms_android.mvp.resources

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseAppCompatFragment
import com.hr.ms.ms_android.bean.MainIconBean
import com.hr.ms.ms_android.data.AccountHelper
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.OfficialEventManagerActivity
import com.hr.ms.ms_android.mvp.resources.overview.OverviewActivity
import com.hr.ms.ms_android.mvp.resources.teachersmanager.TeachersManagerActivity
import kotlinx.android.synthetic.main.resources_fragment.*
import java.util.*

/**
 * Created by Dino on 2018/4/3.
 */
class ResourcesFragment : BaseAppCompatFragment() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    override fun init() {
        stopLoadingAnima()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = GridLayoutManager(context,3)
        var adapter = MyRecyclerAdapter(recyclerView, R.layout.main_item, null)
        recyclerView.adapter = adapter
        val data = ArrayList<MainIconBean>()
        if(AccountHelper.getMenuAuth().isOverViewEnable)data.add(MainIconBean(0,R.mipmap.ic_launcher,"概览"))
        if(AccountHelper.getMenuAuth().isTeacherManagerEnable)data.add(MainIconBean(1,R.mipmap.ic_launcher,"师资管理"))
        if(AccountHelper.getMenuAuth().isScheduleManagerEnable)data.add(MainIconBean(2,R.mipmap.ic_launcher,"官方活动管理"))
        adapter.showSinglePage(data)
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            when(position){
                0->nextActivity(OverviewActivity::class.java)
                1->nextActivity(TeachersManagerActivity::class.java)
                2->nextActivity(OfficialEventManagerActivity::class.java)
            }
        }
//        recyclerView.addItemDecoration(GridItemDecoration(context))
    }

    override fun getLayoutId(): Int {
        return R.layout.resources_fragment
    }

    inner class MyRecyclerAdapter(recyclerView: RecyclerView, layoutIdRes: Int, data: List<MainIconBean>?) : BaseRecyclerViewAdapter<MainIconBean>(recyclerView, layoutIdRes, data) {

        override fun convert(helper: BaseViewHolder, item: MainIconBean) {
            if(item.img != -1)helper.setImageResource(R.id.iv,item.img)
            helper.setText(R.id.tv,item.title)
        }

    }

}