package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable

data class ChooseBean(
        var cls: Class<*>? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readSerializable() as Class<*>?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeSerializable(cls)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ChooseBean> = object : Parcelable.Creator<ChooseBean> {
            override fun createFromParcel(source: Parcel): ChooseBean = ChooseBean(source)
            override fun newArray(size: Int): Array<ChooseBean?> = arrayOfNulls(size)
        }
    }
}