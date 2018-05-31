package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Dino on 2018/4/8.
 */

class UserManagerBean() : Parcelable {
    var account: String? = null
    var password: String? = null
    var userName: String? = null
    var mobile: String? = null
    var email: String? = null
    var isLock: Boolean? = null
    var loginCount: Int? = null
    var loginNearlyDate: String? = null
    var registerType: String? = null
    var role: String? = null
    var createDate: String? = null
    var updateDate: String? = null
    var enterprise: String? = null

    constructor(parcel: Parcel) : this() {
        account = parcel.readString()
        password = parcel.readString()
        userName = parcel.readString()
        mobile = parcel.readString()
        email = parcel.readString()
        isLock = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        loginCount = parcel.readValue(Int::class.java.classLoader) as? Int
        loginNearlyDate = parcel.readString()
        registerType = parcel.readString()
        role = parcel.readString()
        createDate = parcel.readString()
        updateDate = parcel.readString()
        enterprise = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(account)
        parcel.writeString(password)
        parcel.writeString(userName)
        parcel.writeString(mobile)
        parcel.writeString(email)
        parcel.writeValue(isLock)
        parcel.writeValue(loginCount)
        parcel.writeString(loginNearlyDate)
        parcel.writeString(registerType)
        parcel.writeString(role)
        parcel.writeString(createDate)
        parcel.writeString(updateDate)
        parcel.writeString(enterprise)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserManagerBean> {
        override fun createFromParcel(parcel: Parcel): UserManagerBean {
            return UserManagerBean(parcel)
        }

        override fun newArray(size: Int): Array<UserManagerBean?> {
            return arrayOfNulls(size)
        }
    }
}
