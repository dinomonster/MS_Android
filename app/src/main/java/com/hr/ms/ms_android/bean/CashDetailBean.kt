package com.hr.ms.ms_android.bean
import com.google.gson.annotations.SerializedName



data class CashDetailBean(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("memberName") var memberName: String? = "",
    @SerializedName("applyAmount") var applyAmount: Int? = 0,
    @SerializedName("dataSource") var dataSource: String? = "",
    @SerializedName("createTime") var createTime: Long? = 0,
    @SerializedName("realAmount") var realAmount: Int? = 0,
    @SerializedName("poundageAmount") var poundageAmount: Int? = 0,
    @SerializedName("orderNo") var orderNo: String? = "",
    @SerializedName("status") var status: Int? = 0,
    @SerializedName("checkAmount") var checkAmount: Int? = 0,
    @SerializedName("audit") var audit: String? = "",
    @SerializedName("reason") var reason: String? = "",
    @SerializedName("wechatStatus") var wechatStatus: String? = "",
    @SerializedName("transferTime") var transferTime: String? = "",
    @SerializedName("remark") var remark: String? = "",
    @SerializedName("bankCardId") var bankCardId: Int? = 0,
    @SerializedName("realName") var realName: String? = "",
    @SerializedName("identityCard") var identityCard: String? = "",
    @SerializedName("bankCard") var bankCard: String? = "",
    @SerializedName("mobile") var mobile: String? = "",
    @SerializedName("bankName") var bankName: String? = ""
)