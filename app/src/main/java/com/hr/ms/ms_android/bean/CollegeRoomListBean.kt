package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class CollegeRoomListBean(
    @SerializedName("lists") var lists: List<Lists?>? = listOf(),
    @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("collegeRoomId") var collegeRoomId: Int? = null,
            @SerializedName("collegeRoomName") var collegeRoomName: String? = "",
            @SerializedName("collegeRoomImg") var collegeRoomImg: String? = "",
            @SerializedName("collegeRoomVipPrice") var collegeRoomVipPrice: Double? = null,
            @SerializedName("collegeRoomDesc") var collegeRoomDesc: String? = "",
            @SerializedName("collegeRoomCurNum") var collegeRoomCurNum: Int? = null,
            @SerializedName("collegeRoomTopicNum") var collegeRoomTopicNum: Int? = null,
            @SerializedName("collegeRoomCreateTime") var collegeRoomCreateTime: Long? = null
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readString(),
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Long::class.java.classLoader) as Long?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(collegeRoomId)
            writeString(collegeRoomName)
            writeString(collegeRoomImg)
            writeValue(collegeRoomVipPrice)
            writeString(collegeRoomDesc)
            writeValue(collegeRoomCurNum)
            writeValue(collegeRoomTopicNum)
            writeValue(collegeRoomCreateTime)
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