package com.hr.ms.ms_android.bean

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.hr.ms.ms_android.constants.CommonConstants.TYPELEVEL0

/**
 * Created by Dino on 2018/3/8.
 */

class MainIconBean(var id: Int, var img: Int, var title: String?) : AbstractExpandableItem<SubMainIconBean>(), MultiItemEntity {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return TYPELEVEL0
    }

}
