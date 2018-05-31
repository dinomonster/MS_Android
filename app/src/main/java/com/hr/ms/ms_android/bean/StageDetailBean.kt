package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class StageDetailBean(
        @SerializedName("stageId") var stageId: Int? = null,
        @SerializedName("stageType") var stageType: Int? = null,
        @SerializedName("stageName") var stageName: String? = "",
        @SerializedName("stageImg") var stageImg: String? = "",
        @SerializedName("stageOwnerName") var stageOwnerName: String? = "",
        @SerializedName("stageCode") var stageCode: String? = "",
        @SerializedName("stageDesc") var stageDesc: String? = "",
        @SerializedName("stageAddress") var stageAddress: String? = "",
        @SerializedName("familyPhysicalCardTotalNum") var familyPhysicalCardTotalNum: Int? = null,
        @SerializedName("familyPhysicalCardLeftNum") var familyPhysicalCardLeftNum: Int? = null,
        @SerializedName("familyEcardTotalNum") var familyEcardTotalNum: Int? = null,
        @SerializedName("familyEcardLeftNum") var familyEcardLeftNum: Int? = null,
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
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
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
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(stageId)
        writeValue(stageType)
        writeString(stageName)
        writeString(stageImg)
        writeString(stageOwnerName)
        writeString(stageCode)
        writeString(stageDesc)
        writeString(stageAddress)
        writeValue(familyPhysicalCardTotalNum)
        writeValue(familyPhysicalCardLeftNum)
        writeValue(familyEcardTotalNum)
        writeValue(familyEcardLeftNum)
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
        val CREATOR: Parcelable.Creator<StageDetailBean> = object : Parcelable.Creator<StageDetailBean> {
            override fun createFromParcel(source: Parcel): StageDetailBean = StageDetailBean(source)
            override fun newArray(size: Int): Array<StageDetailBean?> = arrayOfNulls(size)
        }
    }
}