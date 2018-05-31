package com.hr.ms.ms_android.bean
import com.google.gson.annotations.SerializedName



data class AliOSSBean(
    @SerializedName("status") var status: String? = "",
    @SerializedName("AccessKeyId") var accessKeyId: String? = "",
    @SerializedName("AccessKeySecret") var accessKeySecret: String? = "",
    @SerializedName("SecurityToken") var securityToken: String? = "",
    @SerializedName("Expiration") var expiration: String? = "",
    @SerializedName("EndPoint") var endPoint: String? = ""
)