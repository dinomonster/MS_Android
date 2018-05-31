package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Dino on 2018/4/9.
 */

class RoleBean() : Parcelable{
    var name: String? = null
    var isSelect: Boolean = false

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        isSelect = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (isSelect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoleBean> {
        override fun createFromParcel(parcel: Parcel): RoleBean {
            return RoleBean(parcel)
        }

        override fun newArray(size: Int): Array<RoleBean?> {
            return arrayOfNulls(size)
        }
    }
}
