package com.hr.ms.ms_android.bean
import com.google.gson.annotations.SerializedName




data class WithdrawCashBean(
    @SerializedName("orderNo") var orderNo: String? = "",
    @SerializedName("userName") var userName: String? = "",
    @SerializedName("applyAmount") var applyAmount: Double? = null,
    @SerializedName("withdrawCashMode") var withdrawCashMode: String? = "",
    @SerializedName("applyTime") var applyTime: Long? = null,
    @SerializedName("checkStatus") var checkStatus: String? = "",
    @SerializedName("needCheckStatus") var needCheckStatus: Int? = null
)