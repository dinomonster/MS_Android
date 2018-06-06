package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OfficialEvent(
        @SerializedName("officialEventInfoId") var officialEventInfoId: String? = "",
        @SerializedName("officialEventCreatorId") var officialEventCreatorId: Int? = null,
        @SerializedName("officialEventImg") var officialEventImg: String? = "",
        @SerializedName("officialEventTitle") var officialEventTitle: String? = "",
        @SerializedName("officialEventBeginDate") var officialEventBeginDate: Long? = null,
        @SerializedName("officialEventEndDate") var officialEventEndDate: Long? = null,
        @SerializedName("officialEventIntro") var officialEventIntro: String? = "",
        @SerializedName("officialEventNotice") var officialEventNotice: String? = "",
        @SerializedName("officialEventType") var officialEventType: Int? = null,
        @SerializedName("isCheCk") var isCheCk: Int? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(officialEventInfoId)
        writeValue(officialEventCreatorId)
        writeString(officialEventImg)
        writeString(officialEventTitle)
        writeValue(officialEventBeginDate)
        writeValue(officialEventEndDate)
        writeString(officialEventIntro)
        writeString(officialEventNotice)
        writeValue(officialEventType)
        writeValue(isCheCk)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OfficialEvent> = object : Parcelable.Creator<OfficialEvent> {
            override fun createFromParcel(source: Parcel): OfficialEvent = OfficialEvent(source)
            override fun newArray(size: Int): Array<OfficialEvent?> = arrayOfNulls(size)
        }
    }
}