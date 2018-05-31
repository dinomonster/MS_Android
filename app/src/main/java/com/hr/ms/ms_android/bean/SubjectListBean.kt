package com.hr.ms.ms_android.bean
import com.google.gson.annotations.SerializedName



data class SubjectListBean(
    @SerializedName("lists") var lists: List<Lists?>? = listOf(),
    @SerializedName("total") var total: Int? = 0
) {
    data class Lists(
        @SerializedName("topicId") var topicId: Int? = 0,
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
        @SerializedName("seriesPrice") var seriesPrice: Double? = 0.0,
        @SerializedName("isLessonExtend") var isLessonExtend: Int? = 0,
        @SerializedName("gradePrice") var gradePrice: Double? = 0.0,
        @SerializedName("topicFrom") var topicFrom: Int? = 0,
        @SerializedName("num") var num: Int? = 0,
        @SerializedName("watchNum") var watchNum: Int? = 0,
        @SerializedName("tagId") var tagId: Int? = 0,
        @SerializedName("tagName") var tagName: String? = "",
        @SerializedName("createTime") var createTime: Long? = 0,
        @SerializedName("memberName") var memberName: String? = ""
    )
}