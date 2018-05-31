package com.hr.ms.ms_android.bean

import com.google.gson.annotations.SerializedName


data class AgentOrScholarBean(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = null
) {
    data class Lists(
            @SerializedName("userId") var userId: Int? = null,
            @SerializedName("userName") var userName: String? = "",
            @SerializedName("userImg") var userImg: String? = "",
            @SerializedName("activeTime") var activeTime: Long? = null,
            @SerializedName("expireTime") var expireTime: Long? = null
    )
}