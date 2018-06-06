package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Offlineinfo(
        @SerializedName("offlineInfoId ") var offlineInfoId: String? = "",
        @SerializedName("attendanceDate") var attendanceDate: Long? = null,
        @SerializedName("activityAddress") var activityAddress: String? = "",
        @SerializedName("detailAddress") var detailAddress: String? = "",
        @SerializedName("longitude") var longitude: String? = "",
        @SerializedName("latitude") var latitude: String? = "",
        @SerializedName("socialNum") var socialNum: Int? = null,
        @SerializedName("schoolNum") var schoolNum: Int? = null,
        @SerializedName("socialEntErNum") var socialEntErNum: Int? = null,
        @SerializedName("schoolEntErNum") var schoolEntErNum: Int? = null,
        @SerializedName("enterNum") var enterNum: Int? = null,
        @SerializedName("price") var price: Int? = null,
        @SerializedName("contactName") var contactName: String? = "",
        @SerializedName("contactTel") var contactTel: String? = "",
        @SerializedName("createTime") var createTime: Long? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
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
            source.readString(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(offlineInfoId)
        writeValue(attendanceDate)
        writeString(activityAddress)
        writeString(detailAddress)
        writeString(longitude)
        writeString(latitude)
        writeValue(socialNum)
        writeValue(schoolNum)
        writeValue(socialEntErNum)
        writeValue(schoolEntErNum)
        writeValue(enterNum)
        writeValue(price)
        writeString(contactName)
        writeString(contactTel)
        writeValue(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Offlineinfo> = object : Parcelable.Creator<Offlineinfo> {
            override fun createFromParcel(source: Parcel): Offlineinfo = Offlineinfo(source)
            override fun newArray(size: Int): Array<Offlineinfo?> = arrayOfNulls(size)
        }
    }
}