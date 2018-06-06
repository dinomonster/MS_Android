package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImManager(
        @SerializedName("freeNum") var freeNum: Int? = null,
        @SerializedName("imManagerId") var imManagerId: Int? = null,
        @SerializedName("imManagerType") var imManagerType: Int? = null,
        @SerializedName("imManagerInfoId") var imManagerInfoId: String? = "",
        @SerializedName("imManagerMobile") var imManagerMobile: String? = "",
        @SerializedName("imManagerUserName") var imManagerUserName: String? = "",
        @SerializedName("imManagerUserImg") var imManagerUserImg: String? = "",
        @SerializedName("createTime") var createTime: Long? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(freeNum)
        writeValue(imManagerId)
        writeValue(imManagerType)
        writeString(imManagerInfoId)
        writeString(imManagerMobile)
        writeString(imManagerUserName)
        writeString(imManagerUserImg)
        writeValue(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImManager> = object : Parcelable.Creator<ImManager> {
            override fun createFromParcel(source: Parcel): ImManager = ImManager(source)
            override fun newArray(size: Int): Array<ImManager?> = arrayOfNulls(size)
        }
    }
}