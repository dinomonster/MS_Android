package com.hr.ms.ms_android.bean
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class SubjectDetailBean(
        @SerializedName("topicId") var topicId: Int = 0,
        @SerializedName("activityId") var activityId: Int? = 0,
        @SerializedName("accId") var accId: Int? = 0,
        @SerializedName("anchorId") var anchorId: Int? = 0,
        @SerializedName("topicType") var topicType: Int? = 0,
        @SerializedName("topicTitle") var topicTitle: String? = "",
        @SerializedName("topicImg") var topicImg: String? = "",
        @SerializedName("topicDesc") var topicDesc: String? = "",
        @SerializedName("topicStatus") var topicStatus: Int? = 0,
        @SerializedName("beginTime") var beginTime: Long? = 0,
        @SerializedName("topicOutline") var topicOutline: String? = "",
        @SerializedName("lsnCount") var lsnCount: Int? = 0,
        @SerializedName("seriesPrice") var seriesPrice: Int? = 0,
        @SerializedName("isLessonExtend") var isLessonExtend: Int? = 0,
        @SerializedName("gradePrice") var gradePrice: Int? = 0,
        @SerializedName("topicFrom") var topicFrom: Int? = 0,
        @SerializedName("num") var num: Int? = 0,
        @SerializedName("watchNum") var watchNum: Int? = 0,
        @SerializedName("tagId") var tagId: Int? = 0,
        @SerializedName("tagName") var tagName: String? = "",
        @SerializedName("createTime") var createTime: Long? = 0,
        @SerializedName("memberName") var memberName: String? = "",
        @SerializedName("introduction") var introduction: String? = "",
        @SerializedName("dataSource") var dataSource: String? = ""
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(topicId)
        writeValue(activityId)
        writeValue(accId)
        writeValue(anchorId)
        writeValue(topicType)
        writeString(topicTitle)
        writeString(topicImg)
        writeString(topicDesc)
        writeValue(topicStatus)
        writeValue(beginTime)
        writeString(topicOutline)
        writeValue(lsnCount)
        writeValue(seriesPrice)
        writeValue(isLessonExtend)
        writeValue(gradePrice)
        writeValue(topicFrom)
        writeValue(num)
        writeValue(watchNum)
        writeValue(tagId)
        writeString(tagName)
        writeValue(createTime)
        writeString(memberName)
        writeString(introduction)
        writeString(dataSource)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SubjectDetailBean> = object : Parcelable.Creator<SubjectDetailBean> {
            override fun createFromParcel(source: Parcel): SubjectDetailBean = SubjectDetailBean(source)
            override fun newArray(size: Int): Array<SubjectDetailBean?> = arrayOfNulls(size)
        }
    }
}