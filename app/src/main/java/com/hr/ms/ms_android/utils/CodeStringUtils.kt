package com.hr.ms.ms_android.utils

import android.text.TextUtils

/**
 * Created by Dino on 2017/10/19.
 */

object CodeStringUtils {
    val isOrNoCode = arrayListOf(0, 1)//0不是 1是
    val tagTypeCode = arrayListOf(1, 2, 3, 4)//1:课题标签 2:身份标签 3:研究/感兴趣领域 4:企业行业领域

    val imManagerTypeCode = arrayListOf(1, 2)
    val imManagerTypeString = arrayOf("辅导员", "金句编写者")

    val stageCodeArry = arrayListOf(0, 1, 2)
    val stageStringArry = arrayListOf("个人驿站", "城市驿站", "校园驿站")

    val identityCodeArry = arrayListOf(null, 0, 1, 2, 3)
    val identityArry = arrayListOf("全部", "普通用户", "师兄", "客座嘉宾", "学生")

    val sexCodeArray = arrayListOf(0, 1)
    val sexStringArray = arrayListOf("女", "男")

    val withdrawCashModeCode = arrayListOf(null, 1, 2)
    val withdrawCashModeString = arrayListOf("全部", "微信", "银行")

    val checkStatusCode = arrayListOf(null, 0, 1, 2, 3)
    val checkStatusString = arrayListOf("全部", "待审核", "已完成", "已拒绝", "微信平台处理中")

    val sortRuleCode = arrayListOf(1, 2)
    val sortRuleString = arrayListOf("时间升序", "时间降序")
    //服务给的太随意，无奈
    val sortRuleCode2 = arrayListOf(1, 2)
    val sortRuleString2 = arrayListOf("时间降序", "时间升序")

    val topicTypeCode = arrayListOf(3, 1, 2)
    val topicTypeString = arrayListOf("全部", "音频", "视频")

    val topicFromCode = arrayListOf(3, 0, 1, 2)
    val topicFromString = arrayListOf("全部", "官方", "私塾", "企业学院")

    val auditStatusCode = arrayListOf(3, 0, 1, 2)
    val auditStatusString = arrayListOf("全部", "审核中", "审核通过", "不通过 ")

    val isBroadcastCode = arrayListOf(2, 0, 1)
    val isBroadcastString = arrayListOf("全部", "非录播", "录播")

    val roleTypeCode = arrayListOf(1, 2, 3)
    val roleTypeString = arrayListOf("代言人", "学霸", "学员")

    val userIdentityTypeCode = arrayListOf(null, 1, 2)
    val userIdentityTypeString = arrayListOf("全部", "导师", "客座嘉宾")

    val eventStatusCode = arrayListOf(null, 0, 1, 2, 3, 4)
    val eventStatusString = arrayListOf("全部", "审核中", "被拒绝", "报名中", "已结束", "进行中")

    val activityTypeCode = arrayListOf(null, 1, 2, 3)
    val activityTypeString = arrayListOf("全部", "仅线下", "仅直播", "线下同步直播")
    val activityTypeString2 = arrayOf("线下活动", "直播活动", "线下同步直播")


    val nodeTypeString = arrayOf("签到", "嘉宾分享", "中场休息", "嘉宾互动", "活动结束", "嘉宾晚宴")
    val nodeTypeCode = arrayOf(1, 2, 3, 4, 5, 6)

    private fun notNULL(code: String): String {
        return if (TextUtils.isEmpty(code)) "" else code
    }


    fun getNodeTypeString(code: Int?): String {
        return when (code) {
            nodeTypeCode[0] -> nodeTypeString[0]
            nodeTypeCode[1] -> nodeTypeString[1]
            nodeTypeCode[2] -> nodeTypeString[2]
            nodeTypeCode[3] -> nodeTypeString[3]
            nodeTypeCode[4] -> nodeTypeString[4]
            nodeTypeCode[5] -> nodeTypeString[5]
            else -> ""
        }
    }

    /**
     * 运营管理=》用户列表=》身份类型
     * @param code
     * @return
     */
    fun getActivityTypeString(code: Int?): String {
        return when (code) {
            activityTypeCode[1] -> activityTypeString2[0]
            activityTypeCode[2] -> activityTypeString2[1]
            activityTypeCode[3] -> activityTypeString2[2]
            else -> ""
        }
    }

    fun getImManagerTypeString(code: Int?): String {
        return when (code) {
            imManagerTypeCode[0] -> imManagerTypeString[0]
            imManagerTypeCode[1] -> imManagerTypeString[1]
            else -> ""
        }
    }

    /**
     * 运营管理=》用户列表=》身份类型
     * @param code
     * @return
     */
    fun getIdentityString(code: Int?): String {
        return when (code) {
            identityCodeArry[0] -> identityArry[0]
            identityCodeArry[1] -> identityArry[1]
            identityCodeArry[2] -> identityArry[2]
            identityCodeArry[3] -> identityArry[3]
            identityCodeArry[4] -> identityArry[4]
            else -> ""
        }
    }

    fun getIdentityCode(code: String?): Int? {
        return when (code) {
            identityArry[0] -> identityCodeArry[0]
            identityArry[1] -> identityCodeArry[1]
            identityArry[2] -> identityCodeArry[2]
            identityArry[3] -> identityCodeArry[3]
            identityArry[4] -> identityCodeArry[4]
            else -> -1
        }
    }

    /**
     * 性别
     * @param code
     * @return
     */
    fun getSexString(code: Int?): String {
        return when (code) {
            sexCodeArray[0] -> sexStringArray[0]
            sexCodeArray[1] -> sexStringArray[1]
            else -> ""
        }
    }

    fun getSexCode(code: String?): Int {
        return when (code) {
            sexStringArray[0] -> sexCodeArray[0]
            sexStringArray[1] -> sexCodeArray[1]
            else -> -1
        }
    }

    /**
     * 提现状态
     * @param code
     * @return
     */
    fun getCheckStatusString(code: Int?): String {
        return when (code) {
            checkStatusCode[0] -> checkStatusString[0]
            checkStatusCode[1] -> checkStatusString[1]
            checkStatusCode[2] -> checkStatusString[2]
            checkStatusCode[3] -> checkStatusString[3]
            checkStatusCode[4] -> checkStatusString[4]
            else -> ""
        }
    }

    fun getCheckStatusCode(code: String?): Int? {
        return when (code) {
            checkStatusString[0] -> checkStatusCode[0]
            checkStatusString[1] -> checkStatusCode[1]
            checkStatusString[2] -> checkStatusCode[2]
            checkStatusString[3] -> checkStatusCode[3]
            checkStatusString[4] -> checkStatusCode[4]
            else -> null
        }
    }

    /**
     * 提现方式
     * @param code
     * @return
     */
    fun getWithdrawCashModeString(code: Int?): String {
        return when (code) {
            withdrawCashModeCode[0] -> withdrawCashModeString[0]
            withdrawCashModeCode[1] -> withdrawCashModeString[1]
            withdrawCashModeCode[2] -> withdrawCashModeString[2]
            else -> ""
        }
    }

    fun getWithdrawCashModeCode(code: String?): Int? {
        return when (code) {
            withdrawCashModeString[0] -> withdrawCashModeCode[0]
            withdrawCashModeString[1] -> withdrawCashModeCode[1]
            withdrawCashModeString[2] -> withdrawCashModeCode[2]
            else -> null
        }
    }

    /**
     * 课题类型
     * @param code
     * @return
     */
    fun getTopicTypeString(code: Int?): String {
        return when (code) {
            topicTypeCode[0] -> topicTypeString[0]
            topicTypeCode[1] -> topicTypeString[1]
            topicTypeCode[2] -> topicTypeString[2]
            else -> ""
        }
    }

    fun getTopicTypeCode(code: String?): Int {
        return when (code) {
            topicTypeString[0] -> topicTypeCode[0]
            topicTypeString[1] -> topicTypeCode[1]
            topicTypeString[2] -> topicTypeCode[2]
            else -> -1
        }
    }

    /**
     * 是否录播
     * @param code
     * @return
     */
    fun getisBroadcastString(code: Int?): String {
        return when (code) {
            isBroadcastCode[0] -> isBroadcastString[0]
            isBroadcastCode[1] -> isBroadcastString[1]
            isBroadcastCode[2] -> isBroadcastString[2]
            else -> ""
        }
    }

    fun getisBroadcastCode(code: String?): Int {
        return when (code) {
            isBroadcastString[0] -> isBroadcastCode[0]
            isBroadcastString[1] -> isBroadcastCode[1]
            isBroadcastString[2] -> isBroadcastCode[2]
            else -> -1
        }
    }

    /**
     * 审核状态
     * @param code
     * @return
     */
    fun getauditStatusString(code: Int?): String {
        return when (code) {
            auditStatusCode[0] -> auditStatusString[0]
            auditStatusCode[1] -> auditStatusString[1]
            auditStatusCode[2] -> auditStatusString[2]
            auditStatusCode[3] -> auditStatusString[3]
            else -> ""
        }
    }

    fun getauditStatusCode(code: String?): Int {
        return when (code) {
            auditStatusString[0] -> auditStatusCode[0]
            auditStatusString[1] -> auditStatusCode[1]
            auditStatusString[2] -> auditStatusCode[2]
            auditStatusString[3] -> auditStatusCode[3]
            else -> -1
        }
    }


    /**
     * 官方活动状态
     * @param code
     * @return
     */
    fun getEventStatusString(code: Int?): String? {
        return when (code) {
            eventStatusCode[0] -> eventStatusString[0]
            eventStatusCode[1] -> eventStatusString[1]
            eventStatusCode[2] -> eventStatusString[2]
            eventStatusCode[3] -> eventStatusString[3]
            else -> ""
        }
    }

    fun getEventStatusCode(code: String?): Int? {
        return when (code) {
            eventStatusString[0] -> eventStatusCode[0]
            eventStatusString[1] -> eventStatusCode[1]
            eventStatusString[2] -> eventStatusCode[2]
            eventStatusString[3] -> eventStatusCode[3]
            else -> -1
        }
    }

}
