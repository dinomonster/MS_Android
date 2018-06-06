package com.hr.ms.ms_android.bean

import com.google.gson.annotations.SerializedName


data class OfficialEventListBean(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = null
) {
    data class Lists(
            @SerializedName("eventId") var eventId: String? = "",
            @SerializedName("eventTitle") var eventTitle: String? = "",
            @SerializedName("eventImge") var eventImge: String? = "",
            @SerializedName("eventStatus") var eventStatus: Int? = null,
            @SerializedName("eventBeginDate") var eventBeginDate: Long? = null,
            @SerializedName("eventEndDate") var eventEndDate: Long? = null,
            @SerializedName("eventCreateTime") var eventCreateTime: Long? = null,
            @SerializedName("eventType") var eventType: Int? = null,
            @SerializedName("eventTypeStr") var eventTypeStr: String? = ""
    )
}