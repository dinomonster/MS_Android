package com.hr.ms.ms_android.bean

import com.google.gson.annotations.SerializedName


data class WithdrawCashCheckBean(
        @SerializedName("checkAmount") var checkAmount: Double? = null,
        @SerializedName("checkUser") var checkUser: String? = "",
        @SerializedName("refuseReason") var refuseReason: String? = "",
        @SerializedName("wechatHandleStatus") var wechatHandleStatus: String? = "",
        @SerializedName("wechatHandleTime") var wechatHandleTime: Long? = null,
        @SerializedName("wechatRefuseReason") var wechatRefuseReason: String? = ""
)