package com.hr.ms.ms_android.bean

import com.google.gson.annotations.SerializedName


data class CardActiveDetailBean(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("activeUserName") var activeUserName: String? = "",
            @SerializedName("cardName") var cardName: String? = "",
            @SerializedName("cardType") var cardType: String? = "",
            @SerializedName("cardCreateTime") var cardCreateTime: Long? = null,
            @SerializedName("activeTime") var activeTime: Long? = null
    )
}