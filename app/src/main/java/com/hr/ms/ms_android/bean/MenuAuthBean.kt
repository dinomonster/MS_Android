package com.hr.ms.ms_android.bean

data class MenuAuthBean(
        var isPermissionManagerEnable: Boolean = false,//权限管理
        var isUserManagerEnable: Boolean = false,//用户管理
        var isRoleManagerEnable: Boolean = false,//角色管理
        var isMenuManagerEnable: Boolean = false,//菜单管理

        var isResourceManagerEnable: Boolean = false,//资源管理
        var isOverViewEnable: Boolean = false,//概览
        var isTeacherManagerEnable: Boolean = false,//师资管理
        var isScheduleManagerEnable: Boolean = false,//排课管理

        var isOperationManagerEnable: Boolean = false,//运营管理
        var isWithDrawManagerEnable: Boolean = false,//交易管理
        var isOPUserManagerEnable: Boolean = false,//用户管理
        var isOPUserListEnable: Boolean = false,//用户管理->用户列表
        var isIdentifyManagerEnable: Boolean = false,//用户管理->身份管理
        var isSubjectManagerEnable: Boolean = false,//课题管理
        var isInterviewManagerEnable: Boolean = false,//访谈管理
        var isReportManagerEnable: Boolean = false,//播报管理
        var isPrivateManagerEnable: Boolean = false,//私塾管理
        var isCollegeManagerEnable: Boolean = false,//学院管理
        var isHopManagerEnable: Boolean = false,//驿站管理
        var isCityHopEnable: Boolean = false,//城市驿站
        var isPersonHopEnable: Boolean = false,//个人驿站
        var isSchoolHopEnable: Boolean = false,//校园驿站
        var isActivityManagerEnable: Boolean = false,//活动管理
        var isLiveManagerEnable: Boolean = false,//直播管理
        var isConfigManagerEnable: Boolean = false,//配置管理
        var isConfigResourceManagerEnable: Boolean = false,//配置管理->资源管理
        var isConfigTerraceEnable: Boolean = false,//配置管理->平台配置
        var isConfigBannerEnable: Boolean = false,//配置管理->轮播图
        var isTimeTaskManagerEnable: Boolean = false,//定时任务管理
        var isCommentManagerEnable: Boolean = false,//评论管理
        var isCommonCommentEnable: Boolean = false,//评论管理->通用评论
        var isLiveCommentEnable: Boolean = false,//评论管理->直播评论
        var isOtherCommentEnable: Boolean = false//评论管理->其他评论
)
