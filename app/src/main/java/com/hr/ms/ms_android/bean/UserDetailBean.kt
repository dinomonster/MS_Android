package com.hr.ms.ms_android.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserDetailBean(
        @SerializedName("accId") var accId: Int? = 0,
        @SerializedName("accName") var accName: String? = "",
        @SerializedName("status") var status: Int? = 0,
        @SerializedName("role") var role: Int? = 0,
        @SerializedName("mobile") var mobile: String? = "",
        @SerializedName("email") var email: String? = "",
        @SerializedName("certNo") var certNo: String? = "",
        @SerializedName("memberName") var memberName: String? = "",
        @SerializedName("nowPoints") var nowPoints: Double? = 0.0,
        @SerializedName("income") var income: Double? = 0.0,
        @SerializedName("totalPay") var totalPay: Double? = 0.0,
        @SerializedName("nowStore") var nowStore: Double? = 0.0,
        @SerializedName("imageUri") var imageUri: String? = "",
        @SerializedName("recommendId") var recommendId: Int? = 0,
        @SerializedName("recommendName") var recommendName: String? = "",
        @SerializedName("introduction") var introduction: String? = "",
        @SerializedName("sex") var sex: Int? = 0,
        @SerializedName("frozen") var frozen: Double? = 0.0,
        @SerializedName("agent") var agent: Int? = 0,
        @SerializedName("isScholar") var isScholar: Int? = 0,
        @SerializedName("code") var code: String? = "",
        @SerializedName("birth") var birth: String? = "",
        @SerializedName("identityId") var identityId: Int? = 0,
        @SerializedName("identityName") var identityName: String? = "",
        @SerializedName("fieldId") var fieldId: Int? = 0,
        @SerializedName("fieldName") var fieldName: String? = "",
        @SerializedName("motto") var motto: String? = "",
        @SerializedName("studyTime") var studyTime: Double? = 0.0,
        @SerializedName("reportNum") var reportNum: String? = "",
        @SerializedName("createTime") var createTime: Long? = 0,
        @SerializedName("updateTime") var updateTime: Long? = 0,
        @SerializedName("seniorInfoVo") var seniorInfoVo: SeniorInfoVo? = SeniorInfoVo(),
        @SerializedName("studentInfoVo") var studentInfoVo: StudentInfoVo? = StudentInfoVo(),
        @SerializedName("myRolesVo") var myRolesVo: MyRolesVo? = MyRolesVo()) : Parcelable {
    data class MyRolesVo(
            @SerializedName("jobOffers") var jobOffers: Int? = 0,
            @SerializedName("notification") var notification: Int? = 0,
            @SerializedName("purchase") var purchase: Int? = 0,
            @SerializedName("download") var download: Int? = 0,
            @SerializedName("bussRoom") var bussRoom: Int? = 0,
            @SerializedName("set") var set: Int? = 0,
            @SerializedName("spread") var spread: Int? = 0,
            @SerializedName("vip") var vip: Int? = 0,
            @SerializedName("resume") var resume: Int? = 0,
            @SerializedName("wallet") var wallet: Int? = 0,
            @SerializedName("rank") var rank: Int? = 0,
            @SerializedName("collegeRoom") var collegeRoom: Int? = 0,
            @SerializedName("assistant") var assistant: Int? = 0,
            @SerializedName("stage") var stage: Int? = 0,
            @SerializedName("addressBook") var addressBook: Int? = 0,
            @SerializedName("isScholar") var isScholar: Int? = 0,
            @SerializedName("roleTeacher") var roleTeacher: Int? = 0,
            @SerializedName("roleStudent") var roleStudent: Int? = 0,
            @SerializedName("agent") var agent: Int? = 0
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(jobOffers)
            writeValue(notification)
            writeValue(purchase)
            writeValue(download)
            writeValue(bussRoom)
            writeValue(set)
            writeValue(spread)
            writeValue(vip)
            writeValue(resume)
            writeValue(wallet)
            writeValue(rank)
            writeValue(collegeRoom)
            writeValue(assistant)
            writeValue(stage)
            writeValue(addressBook)
            writeValue(isScholar)
            writeValue(roleTeacher)
            writeValue(roleStudent)
            writeValue(agent)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<MyRolesVo> = object : Parcelable.Creator<MyRolesVo> {
                override fun createFromParcel(source: Parcel): MyRolesVo = MyRolesVo(source)
                override fun newArray(size: Int): Array<MyRolesVo?> = arrayOfNulls(size)
            }
        }
    }

    data class SeniorInfoVo(
            @SerializedName("id") var id: Int? = 0,
            @SerializedName("accId") var accId: Int? = 0,
            @SerializedName("title") var title: String? = "",
            @SerializedName("company") var company: String? = "",
            @SerializedName("createTime") var createTime: Long? = 0,
            @SerializedName("updateTime") var updateTime: String? = ""
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readValue(Int::class.java.classLoader) as Int?,
                source.readString(),
                source.readString(),
                source.readValue(Long::class.java.classLoader) as Long?,
                source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(id)
            writeValue(accId)
            writeString(title)
            writeString(company)
            writeValue(createTime)
            writeString(updateTime)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SeniorInfoVo> = object : Parcelable.Creator<SeniorInfoVo> {
                override fun createFromParcel(source: Parcel): SeniorInfoVo = SeniorInfoVo(source)
                override fun newArray(size: Int): Array<SeniorInfoVo?> = arrayOfNulls(size)
            }
        }
    }

    data class StudentInfoVo(
            @SerializedName("id") var id: String? = "",
            @SerializedName("accId") var accId: String? = "",
            @SerializedName("studentNo") var studentNo: String? = "",
            @SerializedName("schoolId") var schoolId: String? = "",
            @SerializedName("faculty") var faculty: String? = "",
            @SerializedName("greadeId") var greadeId: String? = "",
            @SerializedName("code") var code: Double? = 0.0,
            @SerializedName("cardImg") var cardImg: String? = ""
    ) : Parcelable {
        constructor(source: Parcel) : this(
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readValue(Double::class.java.classLoader) as Double?,
                source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(id)
            writeString(accId)
            writeString(studentNo)
            writeString(schoolId)
            writeString(faculty)
            writeString(greadeId)
            writeValue(code)
            writeString(cardImg)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<StudentInfoVo> = object : Parcelable.Creator<StudentInfoVo> {
                override fun createFromParcel(source: Parcel): StudentInfoVo = StudentInfoVo(source)
                override fun newArray(size: Int): Array<StudentInfoVo?> = arrayOfNulls(size)
            }
        }
    }

    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readParcelable<SeniorInfoVo>(SeniorInfoVo::class.java.classLoader),
            source.readParcelable<StudentInfoVo>(StudentInfoVo::class.java.classLoader),
            source.readParcelable<MyRolesVo>(MyRolesVo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(accId)
        writeString(accName)
        writeValue(status)
        writeValue(role)
        writeString(mobile)
        writeString(email)
        writeString(certNo)
        writeString(memberName)
        writeValue(nowPoints)
        writeValue(income)
        writeValue(totalPay)
        writeValue(nowStore)
        writeString(imageUri)
        writeValue(recommendId)
        writeString(recommendName)
        writeString(introduction)
        writeValue(sex)
        writeValue(frozen)
        writeValue(agent)
        writeValue(isScholar)
        writeString(code)
        writeString(birth)
        writeValue(identityId)
        writeString(identityName)
        writeValue(fieldId)
        writeString(fieldName)
        writeString(motto)
        writeValue(studyTime)
        writeString(reportNum)
        writeValue(createTime)
        writeValue(updateTime)
        writeParcelable(seniorInfoVo, flags)
        writeParcelable(studentInfoVo, flags)
        writeParcelable(myRolesVo, flags)
    }

    companion object CREATOR : Parcelable.Creator<UserDetailBean> {
        override fun createFromParcel(parcel: Parcel): UserDetailBean {
            return UserDetailBean(parcel)
        }

        override fun newArray(size: Int): Array<UserDetailBean?> {
            return arrayOfNulls(size)
        }
    }
}
