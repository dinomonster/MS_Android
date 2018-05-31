package com.hr.ms.ms_android.utils

import android.text.TextUtils

object PresenterUtils {
    /**
     * 检查参数是否为空
     * 如果为空或者空字符串，则返回相应提示
     * 否则返回null
     */
    fun checkParamasNotNull(useDefaultSuffix: Boolean = true, toastContent: Array<String>, vararg params: Any): String? {
        if (toastContent.size != params.size) {
            throw IllegalArgumentException("params not match, toastContent.size() should be equal params.size()")
        }
        var suffix: String = ""
        if (useDefaultSuffix) suffix = "不能为空"//默认提示后缀
        for (i in params.indices) {
            if (params[i] == null) {
                return toastContent[i] + suffix
            } else if (params[i] is String) {
                if (TextUtils.isEmpty(params[i] as String)) {
                    return toastContent[i] + suffix
                }
            }
        }
        return null
    }
}