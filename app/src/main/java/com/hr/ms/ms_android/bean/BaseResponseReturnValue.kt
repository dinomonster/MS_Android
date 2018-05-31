package com.hr.ms.ms_android.bean

/**
 * Created by Dino on 11/3 0003.
 */

class BaseResponseReturnValue<T> {
    var data: T? = null

    /**
     * 返回说明
     */
    var status: String? = null

    var code: Int = 0


    /**
     * 数据是否过期
     */
    var isUpToDate: Boolean = false
}
