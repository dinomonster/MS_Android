package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

class ScheduleBean() : Parcelable{
    var cover: Int? = null
    var themeName: String? = null
    var teacher: String? = null
    var address: String? = null
    var desc: String? = null
    var societyCount: Int? = null
    var studentCount: Int? = null
    var entryFee: String? = null
    var activityDate: String? = null
    var signDate: String? = null
    var type: String? = null

    constructor(parcel: Parcel) : this() {
        cover = parcel.readValue(Int::class.java.classLoader) as? Int
        themeName = parcel.readString()
        teacher = parcel.readString()
        address = parcel.readString()
        desc = parcel.readString()
        societyCount = parcel.readValue(Int::class.java.classLoader) as? Int
        studentCount = parcel.readValue(Int::class.java.classLoader) as? Int
        entryFee = parcel.readString()
        activityDate = parcel.readString()
        signDate = parcel.readString()
        type = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(cover)
        parcel.writeString(themeName)
        parcel.writeString(teacher)
        parcel.writeString(address)
        parcel.writeString(desc)
        parcel.writeValue(societyCount)
        parcel.writeValue(studentCount)
        parcel.writeString(entryFee)
        parcel.writeString(activityDate)
        parcel.writeString(signDate)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleBean> {
        override fun createFromParcel(parcel: Parcel): ScheduleBean {
            return ScheduleBean(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleBean?> {
            return arrayOfNulls(size)
        }
    }
}