package com.hr.ms.ms_android.utils

import android.text.TextUtils

object StringReplaceUtil {
    private fun relpaceAction(name:String,regular:String):String{
        return name.replace(regular.toRegex(),"*")
    }

    /**
     * 身份证号替换，保留前六位 后四位
     */
    fun idCardReplaceWithStar(idCard:String?):String{
        if(TextUtils.isEmpty(idCard))return ""
        return relpaceAction(idCard!!,"(\\\\w{6})(.*)(\\\\w{4})")
    }

    /**
     * 银行卡号替换，保留前四位 后四位
     */
    fun bankCardReplaceWithStar(idCard:String?):String{
        if(TextUtils.isEmpty(idCard))return ""
        return relpaceAction(idCard!!,"(\\\\w{4})(.*)(\\\\w{4})")
    }

    /**
     * 手机号替换，保留前三位 后一位
     */
    fun mobileReplaceWithStar(idCard:String?):String{
        if(TextUtils.isEmpty(idCard))return ""
        return relpaceAction(idCard!!,"(?<=\\\\d{3})\\\\d(?=\\\\d{3})")
    }
}