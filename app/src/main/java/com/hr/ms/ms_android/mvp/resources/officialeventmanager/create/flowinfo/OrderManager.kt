package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo

import java.util.*

object OrderManager {
    var orderList = arrayListOf<Int>()

    fun clear(){
        orderList.clear()
    }
    fun addOrder(order: String?) {
        if (order != null) orderList.add(order.toInt())
    }

    fun isAlreadyExist(order: String?): Boolean {
        for (s in orderList) {
            if (s == order?.toInt()) {
                return true
            }
        }
        return false
    }

    fun generateOrder(): String {
        if (orderList.size == 0) return "1"
        var max = Collections.max(orderList)
        return (max + 1).toString()
    }
}