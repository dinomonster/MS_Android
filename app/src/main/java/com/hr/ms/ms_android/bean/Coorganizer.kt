package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

data class Coorganizer(
        var coorganizerItemType: Int = 0,
        @SerializedName("coorganizerId") var coorganizerId: Int? = null,
        @SerializedName("coorganizerInfoId") var coorganizerInfoId: String? = "",
        @SerializedName("coorganizerName") var coorganizerName: String? = "",
        @SerializedName("coorganizerLogo") var coorganizerLogo: String? = "",
        @SerializedName("coorganizerUrl") var coorganizerUrl: String? = "",
        @SerializedName("coorganizerUserName") var coorganizerUserName: String? = "",
        @SerializedName("coorganizerUserImg") var coorganizerUserImg: String? = "",
        @SerializedName("freeNum") var freeNum: Int? = null,
        @SerializedName("createTime") var createTime: Long? = null
) : MultiItemEntity, Parcelable {
    override fun getItemType(): Int {
        return coorganizerItemType
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(coorganizerItemType)
        writeValue(coorganizerId)
        writeString(coorganizerInfoId)
        writeString(coorganizerName)
        writeString(coorganizerLogo)
        writeString(coorganizerUrl)
        writeString(coorganizerUserName)
        writeString(coorganizerUserImg)
        writeValue(freeNum)
        writeValue(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Coorganizer> = object : Parcelable.Creator<Coorganizer> {
            override fun createFromParcel(source: Parcel): Coorganizer = Coorganizer(source)
            override fun newArray(size: Int): Array<Coorganizer?> = arrayOfNulls(size)
        }
    }
}