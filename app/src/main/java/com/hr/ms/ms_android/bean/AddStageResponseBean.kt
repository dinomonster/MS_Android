package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class AddStageResponseBean(
        @SerializedName("accId") var accId: Int? = null,
        @SerializedName("activityCount") var activityCount: Int? = null,
        @SerializedName("address") var address: String? = "",
        @SerializedName("createTime") var createTime: Long? = null,
        @SerializedName("deleteState") var deleteState: Int? = null,
        @SerializedName("stageCode") var stageCode: String? = "",
        @SerializedName("stageDesc") var stageDesc: String? = "",
        @SerializedName("stageId") var stageId: Int? = null,
        @SerializedName("stageImg") var stageImg: String? = "",
        @SerializedName("stageName") var stageName: String? = "",
        @SerializedName("stageType") var stageType: Int? = null,
        @SerializedName("updateTime") var updateTime: Long? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(accId)
        writeValue(activityCount)
        writeString(address)
        writeValue(createTime)
        writeValue(deleteState)
        writeString(stageCode)
        writeString(stageDesc)
        writeValue(stageId)
        writeString(stageImg)
        writeString(stageName)
        writeValue(stageType)
        writeValue(updateTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AddStageResponseBean> = object : Parcelable.Creator<AddStageResponseBean> {
            override fun createFromParcel(source: Parcel): AddStageResponseBean = AddStageResponseBean(source)
            override fun newArray(size: Int): Array<AddStageResponseBean?> = arrayOfNulls(size)
        }
    }
}