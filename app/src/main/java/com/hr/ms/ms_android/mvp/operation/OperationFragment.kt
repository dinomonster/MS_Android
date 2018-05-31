package com.hr.ms.ms_android.mvp.operation

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.better.appbase.mvp.MvpPresenter
import com.better.appbase.recyclerview.BaseRecyclerViewAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseAppCompatFragment
import com.hr.ms.ms_android.bean.MainIconBean
import com.hr.ms.ms_android.bean.SubMainIconBean
import com.hr.ms.ms_android.data.AccountHelper
import com.hr.ms.ms_android.mvp.operation.activitymanager.ActivityManagerActivity
import com.hr.ms.ms_android.mvp.operation.collegeroommanager.CollegeRoomManagerActivity
import com.hr.ms.ms_android.mvp.operation.interviewmanager.InterviewManagerActivity
import com.hr.ms.ms_android.mvp.operation.livemanager.LiveManagerActivity
import com.hr.ms.ms_android.mvp.operation.reportmanager.ReportManagerActivity
import com.hr.ms.ms_android.mvp.operation.stagemanager.StageManagerActivity
import com.hr.ms.ms_android.mvp.operation.subjectmanager.SubjectManagerActivity
import com.hr.ms.ms_android.mvp.operation.withdrawmanager.WithdrawManagerActivity
import kotlinx.android.synthetic.main.operation_fragment.*
import java.util.*




/**
 * Created by Dino on 2018/4/3.
 */
class OperationFragment : BaseAppCompatFragment() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    override fun init() {
        stopLoadingAnima()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Observable.create(Observable)
        val data = ArrayList<MultiItemEntity>()
        if(AccountHelper.getMenuAuth().isWithDrawManagerEnable)data.add(MainIconBean(0, R.drawable.ic_withdraw_manager, "交易管理"))

        var usermanager = MainIconBean(1, R.drawable.ic_op_user_manager, "用户管理")
        if(AccountHelper.getMenuAuth().isOPUserListEnable || AccountHelper.getMenuAuth().isIdentifyManagerEnable)usermanager.addSubItem(SubMainIconBean(-1, R.mipmap.ic_launcher, ""))
        if(AccountHelper.getMenuAuth().isOPUserManagerEnable)data.add(usermanager)

        if(AccountHelper.getMenuAuth().isSubjectManagerEnable)data.add(MainIconBean(2, R.drawable.ic_subjet_manager, "课题管理"))
        if(AccountHelper.getMenuAuth().isInterviewManagerEnable)data.add(MainIconBean(3, R.drawable.ic_interview_manager, "访谈管理"))
        if(AccountHelper.getMenuAuth().isReportManagerEnable)data.add(MainIconBean(4, R.drawable.ic_report_manager, "播报管理"))
        if(AccountHelper.getMenuAuth().isPrivateManagerEnable)data.add(MainIconBean(5, R.drawable.ic_private_manager, "私塾管理"))
        if(AccountHelper.getMenuAuth().isCollegeManagerEnable)data.add(MainIconBean(6, R.drawable.ic_college_manager, "学院管理"))
        if(AccountHelper.getMenuAuth().isHopManagerEnable)data.add(MainIconBean(7, R.drawable.ic_hop_manager, "驿站管理"))
        if(AccountHelper.getMenuAuth().isActivityManagerEnable)data.add(MainIconBean(8, R.drawable.ic_activity_manager, "活动管理"))
        if(AccountHelper.getMenuAuth().isLiveManagerEnable)data.add(MainIconBean(9, R.drawable.ic_live_manager, "直播管理"))
        if(AccountHelper.getMenuAuth().isConfigManagerEnable)data.add(MainIconBean(10, R.drawable.ic_config_manager, "配置管理"))
        if(AccountHelper.getMenuAuth().isTimeTaskManagerEnable)data.add(MainIconBean(11, R.drawable.ic_timetask_manager, "定时任务管理"))
        if(AccountHelper.getMenuAuth().isCommentManagerEnable)data.add(MainIconBean(12, R.drawable.ic_comment_manager, "评论管理"))
        if(data.size%3 != 0) {
            for (i in 0 until (data.size / 3 + 1) * 3 - data.size) {
                data.add(MainIconBean(data.size + i, -1, ""))
            }
        }
        var adapter = OperationAdapter(data)
        adapter.setSpanSizeLookup { gridLayoutManager, position ->
            when (adapter.getItemViewType(position)) {
                1 -> gridLayoutManager.spanCount
                else -> 1
            }
        }
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(GridItemDecoration(context))
        adapter.setOnItemClickListener { adapter, view, position ->
            var id:Int = -1
            adapter as OperationAdapter
            if(adapter.getItem(position) is MainIconBean){
                id = (adapter.getItem(position) as MainIconBean).id
            }else if (adapter.getItem(position) is SubMainIconBean){
                id = (adapter.getItem(position) as SubMainIconBean).id
            }
            when (id) {
                0 -> nextActivity(WithdrawManagerActivity::class.java)
                1 -> {
                    if ((adapter.data[position] as MainIconBean).isExpanded) {
                        adapter.customCollapse(1,2)
//                        adapter.collapse(1)
                    } else {
                        adapter.customExpand(1,2)
                    }
                }
                2 -> nextActivity(SubjectManagerActivity::class.java)
                3 -> nextActivity(InterviewManagerActivity::class.java)
                4 -> nextActivity(ReportManagerActivity::class.java)
                5 -> nextActivity(CollegeRoomManagerActivity::class.java)
                7 -> nextActivity(StageManagerActivity::class.java)
                8 -> nextActivity(ActivityManagerActivity::class.java)
                9 -> nextActivity(LiveManagerActivity::class.java)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.operation_fragment
    }

    inner class MyRecyclerAdapter(recyclerView: RecyclerView, layoutIdRes: Int, data: List<MainIconBean>?) : BaseRecyclerViewAdapter<MainIconBean>(recyclerView, layoutIdRes, data) {

        override fun convert(helper: BaseViewHolder, item: MainIconBean) {
            if (item.img != -1) helper.setImageResource(R.id.iv, item.img)
            helper.setText(R.id.tv, item.title)
        }

    }



}