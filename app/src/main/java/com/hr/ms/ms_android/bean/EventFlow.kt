package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

data class EventFlow(
        var flowItemType: Int = 0,
        @SerializedName("flowInfoId") var flowInfoId: String? = "",
        @SerializedName("flowPointName") var flowPointName: String? = "",
        @SerializedName("flowPointType") var flowPointType: Int? = null,
        @SerializedName("flowBeginDate") var flowBeginDate: Long? = null,
        @SerializedName("flowEndDate") var flowEndDate: Long? = null,
        @SerializedName("flowOrderNum") var flowOrderNum: Int? = null,
        @SerializedName("extra1") var extra1: Int? = null,
        @SerializedName("extra2") var extra2: String? = "",
        @SerializedName("createTime") var createTime: Long? = null,
        @SerializedName("officialEventGuestVo") var officialEventGuestVo: OfficialEventGuestVo? = OfficialEventGuestVo()
) : MultiItemEntity, Parcelable {
    override fun getItemType(): Int {
        return flowItemType
    }

    data class OfficialEventGuestVo(
            @SerializedName("seniorId") var seniorId: Int? = null,
            @SerializedName("seniorName") var seniorName: String? = "",
            @SerializedName("seniorImage") var seniorImage: String? = "",
            @SerializedName("seniorTitle") var seniorTitle: String? = "",
            @SerializedName("seniorIntro") var seniorIntro: String? = ""
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(seniorId)
            writeString(seniorName)
            writeString(seniorImage)
            writeString(seniorTitle)
            writeString(seniorIntro)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<OfficialEventGuestVo> = object : Parcelable.Creator<OfficialEventGuestVo> {
                override fun createFromParcel(source: Parcel): OfficialEventGuestVo = OfficialEventGuestVo(source)
                override fun newArray(size: Int): Array<OfficialEventGuestVo?> = arrayOfNulls(size)
            }
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readParcelable<OfficialEventGuestVo>(OfficialEventGuestVo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(flowItemType)
        writeString(flowInfoId)
        writeString(flowPointName)
        writeValue(flowPointType)
        writeValue(flowBeginDate)
        writeValue(flowEndDate)
        writeValue(flowOrderNum)
        writeValue(extra1)
        writeString(extra2)
        writeValue(createTime)
        writeParcelable(officialEventGuestVo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<EventFlow> = object : Parcelable.Creator<EventFlow> {
            override fun createFromParcel(source: Parcel): EventFlow = EventFlow(source)
            override fun newArray(size: Int): Array<EventFlow?> = arrayOfNulls(size)
        }
    }
}