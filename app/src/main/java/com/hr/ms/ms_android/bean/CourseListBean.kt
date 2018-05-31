package com.hr.ms.ms_android.bean
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class CourseListBean(
    @SerializedName("lists") var lists: List<Lists?>? = listOf(),
    @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("lsnId") var lsnId: Int = 0,
            @SerializedName("title") var title: String? = "",
            @SerializedName("img") var img: String? = "",
            @SerializedName("beginDate") var beginDate: Long = 0,
            @SerializedName("curstatus") var curstatus: Int = 0,
            @SerializedName("remindTime") var remindTime: Int? = 0,
            @SerializedName("auditStatus") var auditStatus: Int? = 0,
            @SerializedName("isBroadcast") var isBroadcast: Int = 0,
            @SerializedName("videoId") var videoId: String? = "",
            @SerializedName("authKey") var authKey: String? = "",
            @SerializedName("hourLong") var hourLong: String? = "",
            @SerializedName("mediaUrl") var mediaUrl: String? = "",
            @SerializedName("highUrl") var highUrl: String? = "",
            @SerializedName("details") var details: String? = "",
            @SerializedName("sortNo") var sortNo: Int? = 0,
            @SerializedName("createTime") var createTime: Long? = 0,
            @SerializedName("updateTime") var updateTime: Long? = 0
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int,
                source.readString(),
                source.readString(),
                source.readLong(),
                source.readValue(Int::class.java.classLoader) as Int,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int,
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Long::class.java.classLoader) as Long?,
                source.readValue(Long::class.java.classLoader) as Long?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(lsnId)
            writeString(title)
            writeString(img)
            writeLong(beginDate)
            writeValue(curstatus)
            writeValue(remindTime)
            writeValue(auditStatus)
            writeValue(isBroadcast)
            writeString(videoId)
            writeString(authKey)
            writeString(hourLong)
            writeString(mediaUrl)
            writeString(highUrl)
            writeString(details)
            writeValue(sortNo)
            writeValue(createTime)
            writeValue(updateTime)
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