package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class UserSelectBean(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = null
) {
    data class Lists(
            @SerializedName("userId") var userId: Int? = null,
            @SerializedName("userName") var userName: String? = "",
            @SerializedName("userIdentity") var userIdentity: String? = "",
            @SerializedName("userIdentityId") var userIdentityId: Int? = null,
            @SerializedName("userFieldId") var userFieldId: Int? = null,
            @SerializedName("userImg") var userImg: String? = "",
            @SerializedName("userIntro") var userIntro: String? = "",
            @SerializedName("createTime") var createTime: Long? = null,
            var isChecked: Boolean = false
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readValue(Long::class.java.classLoader) as Long?,
                1 == source.readInt()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(userId)
            writeString(userName)
            writeString(userIdentity)
            writeValue(userIdentityId)
            writeValue(userFieldId)
            writeString(userImg)
            writeString(userIntro)
            writeValue(createTime)
            writeInt((if (isChecked) 1 else 0))
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