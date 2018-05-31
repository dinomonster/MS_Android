package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class IdentityBeanList(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("accId") var accId: Int? = null,
            @SerializedName("userName") var userName: String? = "",
            @SerializedName("mobile") var mobile: String? = "",
            @SerializedName("isAgent") var isAgent: Int? = null,
            @SerializedName("isScholar") var isScholar: Int? = null,
            @SerializedName("isStageOwner") var isStageOwner: Int? = null
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(accId)
            writeString(userName)
            writeString(mobile)
            writeValue(isAgent)
            writeValue(isScholar)
            writeValue(isStageOwner)
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