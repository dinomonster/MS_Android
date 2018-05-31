package com.hr.ms.ms_android.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.hr.ms.ms_android.constants.CommonConstants.TYPELEVEL1

/**
 * Created by Dino on 2018/3/8.
 */

class SubMainIconBean(var id: Int, var img: Int, var title: String?): MultiItemEntity {
    override fun getItemType(): Int {
        return TYPELEVEL1
    }

}
