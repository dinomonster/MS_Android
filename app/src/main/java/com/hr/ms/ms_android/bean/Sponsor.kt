package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

data class Sponsor(
        var sponsorItemType: Int = 0,
        @SerializedName("freeNum") var freeNum: Int? = null,
        @SerializedName("sponsorInfoId") var sponsorInfoId: String? = "",
        @SerializedName("sponsorId") var sponsorId: String? = "",
        @SerializedName("sponsorLogo") var sponsorLogo: String? = "",
        @SerializedName("sponsorUserImg") var sponsorUserImg: String? = "",
        @SerializedName("sponsorUserName") var sponsorUserName: String? = "",
        @SerializedName("sponsorName") var sponsorName: String? = "",
        @SerializedName("sponsorUrl") var sponsorUrl: String? = "",
        @SerializedName("createTime") var createTime: Long? = null
) : MultiItemEntity, Parcelable {
    override fun getItemType(): Int {
        return sponsorItemType
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
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(sponsorItemType)
        writeValue(freeNum)
        writeString(sponsorInfoId)
        writeString(sponsorId)
        writeString(sponsorLogo)
        writeString(sponsorUserImg)
        writeString(sponsorUserName)
        writeString(sponsorName)
        writeString(sponsorUrl)
        writeValue(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Sponsor> = object : Parcelable.Creator<Sponsor> {
            override fun createFromParcel(source: Parcel): Sponsor = Sponsor(source)
            override fun newArray(size: Int): Array<Sponsor?> = arrayOfNulls(size)
        }
    }
}