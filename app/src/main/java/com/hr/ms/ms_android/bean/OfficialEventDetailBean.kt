package com.hr.ms.ms_android.bean

import com.google.gson.annotations.SerializedName


data class OfficialEventDetailBean(
        @SerializedName("officialEvent") var officialEvent: OfficialEvent? = OfficialEvent(),
        @SerializedName("eventFlowList") var eventFlowList: List<EventFlow?>? = listOf(),
        @SerializedName("imManagerList") var imManagerList: List<ImManager?>? = listOf(),
        @SerializedName("sponsorList") var sponsorList: List<Sponsor?>? = listOf(),
        @SerializedName("coorganizerList") var coorganizerList: List<Coorganizer?>? = listOf(),
        @SerializedName("offlineinfo") var offlineinfo: Offlineinfo? = Offlineinfo(),
        @SerializedName("liveinfo") var liveinfo: Liveinfo? = Liveinfo()
)