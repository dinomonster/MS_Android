package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class StageSelectListBean(
    @SerializedName("lists") var lists: List<Lists?>? = listOf(),
    @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("stageId") var stageId: Int? = 0,
            @SerializedName("stageName") var stageName: String? = "",
            @SerializedName("stageImg") var stageImg: String? = "",
            @SerializedName("stageCode") var stageCode: String? = ""
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(stageId)
            writeString(stageName)
            writeString(stageImg)
            writeString(stageCode)
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