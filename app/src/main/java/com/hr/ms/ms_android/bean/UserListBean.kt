package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * Created by Dino on 2018/3/7.
 */


data class UserListBean(
        @SerializedName("lists") var lists: List<Lists?>? = listOf(),
        @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("accId") var accId: Int? = null,
            @SerializedName("userName") var userName: String? = "",
            @SerializedName("userIdentity") var userIdentity: String? = "",
            @SerializedName("totalPay") var totalPay: Double? = null,
            @SerializedName("income") var income: Double? = null,
            @SerializedName("nowStore") var nowStore: Double? = null,
            @SerializedName("frozen") var frozen: Double? = null,
            @SerializedName("nowPoints") var nowPoints: Double? = null,
            @SerializedName("recommendId") var recommendId: Int? = null,
            @SerializedName("recommendName") var recommendName: String? = "",
            @SerializedName("createTime") var createTime: Long? = null,
            @SerializedName("roleList") var roleList: List<String?>? = listOf()
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readValue(Long::class.java.classLoader) as Long?,
                ArrayList<String?>().apply { source.readList(this, String::class.java.classLoader) }
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(accId)
            writeString(userName)
            writeString(userIdentity)
            writeValue(totalPay)
            writeValue(income)
            writeValue(nowStore)
            writeValue(frozen)
            writeValue(nowPoints)
            writeValue(recommendId)
            writeString(recommendName)
            writeValue(createTime)
            writeList(roleList)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Lists> = object : Parcelable.Creator<Lists> {
                override fun createFromParcel(source: Parcel): Lists = Lists(source)
                override fun newArray(size: Int): Array<Lists?> = arrayOfNulls(size)
            }
        }
    }
}
