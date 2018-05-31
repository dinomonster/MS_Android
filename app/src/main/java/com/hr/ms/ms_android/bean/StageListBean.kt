package com.hr.ms.ms_android.bean
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class StageListBean(
    @SerializedName("lists") var lists: List<Lists?>? = listOf(),
    @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("stageId") var stageId: Int? = null,
            @SerializedName("stageName") var stageName: String? = "",
            @SerializedName("stageImg") var stageImg: String? = "",
            @SerializedName("stageOwnerName") var stageOwnerName: String? = "",
            @SerializedName("stageCode") var stageCode: String? = "",
            @SerializedName("familyEcardNum") var familyEcardNum: Int? = null,
            @SerializedName("activityNum") var activityNum: Int? = null,
            @SerializedName("agentNum") var agentNum: Int? = null,
            @SerializedName("scholarNum") var scholarNum: Int? = null,
            @SerializedName("userNum") var userNum: Int? = null,
            @SerializedName("userTotalPay") var userTotalPay: Int? = null,
            @SerializedName("stageOwnerIncome") var stageOwnerIncome: Int? = null,
            @SerializedName("createTime") var createTime: Long? = null
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Long::class.java.classLoader) as Long?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(stageId)
            writeString(stageName)
            writeString(stageImg)
            writeString(stageOwnerName)
            writeString(stageCode)
            writeValue(familyEcardNum)
            writeValue(activityNum)
            writeValue(agentNum)
            writeValue(scholarNum)
            writeValue(userNum)
            writeValue(userTotalPay)
            writeValue(stageOwnerIncome)
            writeValue(createTime)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Lists> = object : Parcelable.Creator<Lists> {
                override fun createFromParcel(source: Parcel): Lists = Lists(source)
                override fun newArray(size: Int): Array<Lists?> = arrayOfNulls(size)
            }
        }
    }
}