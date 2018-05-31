package com.hr.ms.ms_android.bean
import com.google.gson.annotations.SerializedName



data class WithdrawApplyBean(
    @SerializedName("userName") var userName: String? = "",
    @SerializedName("applyAmount") var applyAmount: Double? = null,
    @SerializedName("withdrawCashMode") var withdrawCashMode: Int? =null,
    @SerializedName("applyTime") var applyTime: Long? = null,
    @SerializedName("payableAmount") var payableAmount: Double? = null,
    @SerializedName("poundageAmount") var poundageAmount: Double? = null,
    @SerializedName("orderNo") var orderNo: String? = "",
    @SerializedName("checkStatus") var checkStatus: String? = "",
    @SerializedName("bankCardVo") var bankCardVo: BankCardVo? = BankCardVo()
) {
    data class BankCardVo(
        @SerializedName("bankName") var bankName: String? = "",
        @SerializedName("accountName") var accountName: String? = "",
        @SerializedName("identityCardNum") var identityCardNum: String? = "",
        @SerializedName("bankCardNum") var bankCardNum: String? = "",
        @SerializedName("mobile") var mobile: String? = ""
    )
}