package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

data class LocationBean(
        var address: String? = "",
        var latitude: Double? = null,
        var longitude: Double? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(address)
        writeValue(latitude)
        writeValue(longitude)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LocationBean> = object : Parcelable.Creator<LocationBean> {
            override fun createFromParcel(source: Parcel): LocationBean = LocationBean(source)
            override fun newArray(size: Int): Array<LocationBean?> = arrayOfNulls(size)
        }
    }
}