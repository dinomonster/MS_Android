package com.hr.ms.ms_android.bean
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class CollegeRoomDetailBean(
        @SerializedName("collegeRoomId") var collegeRoomId: Int? = 0,
        @SerializedName("collegeRoomName") var collegeRoomName: String? = "",
        @SerializedName("collegeRoomImg") var collegeRoomImg: String? = "",
        @SerializedName("collegeRoomAddress") var collegeRoomAddress: String? = "",
        @SerializedName("collegeRoomVipPrice") var collegeRoomVipPrice: Int? = 0,
        @SerializedName("collegeRoomVipDesc") var collegeRoomVipDesc: String? = "",
        @SerializedName("collegeRoomDesc") var collegeRoomDesc: String? = "",
        @SerializedName("collegeRoomCreateTime") var collegeRoomCreateTime: Long? = 0
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(collegeRoomId)
        writeString(collegeRoomName)
        writeString(collegeRoomImg)
        writeString(collegeRoomAddress)
        writeValue(collegeRoomVipPrice)
        writeString(collegeRoomVipDesc)
        writeString(collegeRoomDesc)
        writeValue(collegeRoomCreateTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CollegeRoomDetailBean> = object : Parcelable.Creator<CollegeRoomDetailBean> {
            override fun createFromParcel(source: Parcel): CollegeRoomDetailBean = CollegeRoomDetailBean(source)
            override fun newArray(size: Int): Array<CollegeRoomDetailBean?> = arrayOfNulls(size)
        }
    }
}