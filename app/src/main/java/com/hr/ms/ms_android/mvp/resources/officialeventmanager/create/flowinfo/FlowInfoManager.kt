package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo

import com.hr.ms.ms_android.bean.EventFlow

object FlowInfoManager {
    var eventFlowList = arrayListOf<EventFlow>()

    fun addEventFlow(eventFlow: EventFlow) {
        eventFlowList.add(eventFlow)

    }

    fun sort() {
        eventFlowList.sortBy { it.flowOrderNum }
    }

    fun sortByDescending() {
        eventFlowList.sortByDescending { it.flowOrderNum }
    }

}