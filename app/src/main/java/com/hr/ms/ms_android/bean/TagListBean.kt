package com.hr.ms.ms_android.bean
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class TagListBean(
    @SerializedName("lists") var lists: List<Lists?>? = listOf(),
    @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
            @SerializedName("tagId") var tagId: Int? = 0,
            @SerializedName("tagName") var tagName: String? = "",
            @SerializedName("tagType") var tagType: Int? = 0,
            @SerializedName("createTime") var createTime: Long? = 0,
            @SerializedName("parentId") var parentId: Int? = 0
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Long::class.java.classLoader) as Long?,
                source.readValue(Int::class.java.classLoader) as Int?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(tagId)
            writeString(tagName)
            writeValue(tagType)
            writeValue(createTime)
            writeValue(parentId)
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