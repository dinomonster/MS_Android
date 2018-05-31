package com.hr.ms.ms_android.bean

/**
 * Created by Dino on 2018/3/7.
 */


data class LoginUserBean(
        var userId: Int = -1,
        var loginName: String = "",
        var password: String = "",
        var userName: String = "",
        var email: String = "",
        var hashSalt: String = "",
        var isLocked: Int = -1,
        var createTime: String = "",
        var updateTime: String = "",
        var count: Int = -1,
        var loginTime: String = "",
        var loginWay: Int = -1,
        var mobile: String = "",
        var menus: List<Menu> = emptyList(),
        var permissions: String = "",
        var roles: String = "",
        var headUrl: String = "",
        var token: String = "",
        var companyId: Int = -1
)

data class Menu(
        var id: Int,
        var name: String,
        var type: String,
        var path: String,
        var code: String,
        var parentId: Int,
        var isAvailable: Int,
        var icon: String,
        var order: Int,
        var isParent: Boolean,
        var check: Boolean,
        var sysPermissions: List<SysPermission>
)

data class SysPermission(
        var id: Int,
        var name: String,
        var type: String,
        var path: String,
        var code: String,
        var parentId: Int,
        var isAvailable: Int,
        var icon: String,
        var order: Int,
        var isParent: Boolean,
        var check: Boolean,
        var sysPermissions: List<SysPermission>
)
