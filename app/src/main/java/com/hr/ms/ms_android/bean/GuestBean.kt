package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Dino on 2018/3/7.
 */

class GuestBean() : Parcelable{

    var name: String? = null
    var type: String? = null
    var territory: String? = null
    var shareNo: String? = null
    var shareTime: String? = null
    var shareTheme: String? = null
    var shareContent: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        type = parcel.readString()
        territory = parcel.readString()
        shareNo = parcel.readString()
        shareTime = parcel.readString()
        shareTheme = parcel.readString()
        shareContent = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(territory)
        parcel.writeString(shareNo)
        parcel.writeString(shareTime)
        parcel.writeString(shareTheme)
        parcel.writeString(shareContent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GuestBean> {
        override fun createFromParcel(parcel: Parcel): GuestBean {
            return GuestBean(parcel)
        }

        override fun newArray(size: Int): Array<GuestBean?> {
            return arrayOfNulls(size)
        }
    }


}
