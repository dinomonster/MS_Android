package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Dino on 2018/4/9.
 */

class ResourceBean() : Parcelable {
    var name: String? = null
    var type: String? = null
    var url: String? = null
    var tag: String? = null
    var isActivate: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        type = parcel.readString()
        url = parcel.readString()
        tag = parcel.readString()
        isActivate = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeString(tag)
        parcel.writeString(isActivate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResourceBean> {
        override fun createFromParcel(parcel: Parcel): ResourceBean {
            return ResourceBean(parcel)
        }

        override fun newArray(size: Int): Array<ResourceBean?> {
            return arrayOfNulls(size)
        }
    }
}
