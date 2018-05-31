package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Dino on 2018/3/7.
 */

class TeacherBean() : Parcelable{

    var photo: Int? = null
    var name: String? = null
    var intro: String? = null
    var type: String? = null
    var mobile: String? = null
    var territory: String? = null
    var talkType: String? = null
    var fee: String? = null
    var talkDate: String? = null

    constructor(parcel: Parcel) : this() {
        photo = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        intro = parcel.readString()
        type = parcel.readString()
        mobile = parcel.readString()
        territory = parcel.readString()
        talkType = parcel.readString()
        fee = parcel.readString()
        talkDate = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(photo)
        parcel.writeString(name)
        parcel.writeString(intro)
        parcel.writeString(type)
        parcel.writeString(mobile)
        parcel.writeString(territory)
        parcel.writeString(talkType)
        parcel.writeString(fee)
        parcel.writeString(talkDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TeacherBean> {
        override fun createFromParcel(parcel: Parcel): TeacherBean {
            return TeacherBean(parcel)
        }

        override fun newArray(size: Int): Array<TeacherBean?> {
            return arrayOfNulls(size)
        }
    }
}
