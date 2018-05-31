package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class SeniorListBean(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = null
) {
    data class Lists(
            @SerializedName("accId") var accId: Int? = null,
            @SerializedName("userImg") var userImg: String? = "",
            @SerializedName("userName") var userName: String? = "",
            @SerializedName("userIdentityType") var userIdentityType: String? = "",
            @SerializedName("seniorIdentity") var seniorIdentity: String? = "",
            @SerializedName("fieldName") var fieldName: String? = "",
            @SerializedName("userTitle") var userTitle: String? = "",
            @SerializedName("company") var company: String? = "",
            @SerializedName("createTime") var createTime: Long? = null
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readValue(Long::class.java.classLoader) as Long?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(accId)
            writeString(userImg)
            writeString(userName)
            writeString(userIdentityType)
            writeString(seniorIdentity)
            writeString(fieldName)
            writeString(userTitle)
            writeString(company)
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