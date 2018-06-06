package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Liveinfo(
        @SerializedName("liveInfoId") var liveInfoId: String? = "",
        @SerializedName("playUrl") var playUrl: String? = "",
        @SerializedName("pushStreameUrl") var pushStreameUrl: String? = "",
        @SerializedName("chatroomMsg") var chatroomMsg: String? = "",
        @SerializedName("liveStatus") var liveStatus: Int? = null,
        @SerializedName("liveWatchNum") var liveWatchNum: Int? = null,
        @SerializedName("liveWatchNumBase") var liveWatchNumBase: Int? = null,
        @SerializedName("liveBookNum") var liveBookNum: Int? = null,
        @SerializedName("liveBookNumBase") var liveBookNumBase: Int? = null,
        @SerializedName("currentGuestId") var currentGuestId: Int? = null,
        @SerializedName("nextGuestId") var nextGuestId: Int? = null,
        @SerializedName("createTime") var createTime: Long? = null
) : Parcelable {
    constructor(source: Parcel) : this(
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
        writeString(liveInfoId)
        writeString(playUrl)
        writeString(pushStreameUrl)
        writeString(chatroomMsg)
        writeValue(liveStatus)
        writeValue(liveWatchNum)
        writeValue(liveWatchNumBase)
        writeValue(liveBookNum)
        writeValue(liveBookNumBase)
        writeValue(currentGuestId)
        writeValue(nextGuestId)
        writeValue(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Liveinfo> = object : Parcelable.Creator<Liveinfo> {
            override fun createFromParcel(source: Parcel): Liveinfo = Liveinfo(source)
            override fun newArray(size: Int): Array<Liveinfo?> = arrayOfNulls(size)
        }
    }
}
