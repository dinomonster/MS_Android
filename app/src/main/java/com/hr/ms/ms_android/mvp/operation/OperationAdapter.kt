package com.hr.ms.ms_android.mvp.operation

import android.content.Intent
import android.support.annotation.IntRange
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.IExpandable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.bean.MainIconBean
import com.hr.ms.ms_android.constants.CommonConstants.TYPELEVEL0
import com.hr.ms.ms_android.constants.CommonConstants.TYPELEVEL1
import com.hr.ms.ms_android.data.AccountHelper
import com.hr.ms.ms_android.mvp.operation.usermanager.identitymanager.IdentityManagerActivity
import com.hr.ms.ms_android.mvp.operation.usermanager.seniormanager.SeniorManagerActivity
import com.hr.ms.ms_android.mvp.operation.usermanager.userlist.UserManagerActivity


class OperationAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    constructor(data: List<MultiItemEntity>) : super(data) {
        addItemType(TYPELEVEL0, R.layout.main_item)
        addItemType(TYPELEVEL1, R.layout.sub_main_item)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when (helper?.itemViewType) {
            TYPELEVEL0 -> {
                item as MainIconBean
                if (item.img != -1) helper.setImageResource(R.id.iv, item.img)
                helper.setText(R.id.tv, item.title)
            }
            TYPELEVEL1 -> {
                if(AccountHelper.getMenuAuth().isOPUserListEnable){
                    helper.setGone(R.id.user_ll,true)
                }else{
                    helper.setGone(R.id.user_ll,false)
                }
                if(AccountHelper.getMenuAuth().isIdentifyManagerEnable){
                    helper.setGone(R.id.iden_ll,true)
                }else{
                    helper.setGone(R.id.iden_ll,false)
                }
                helper.getView<View>(R.id.user_ll).setOnClickListener { mContext.startActivity(Intent(mContext, UserManagerActivity::class.java)) }
                helper.getView<View>(R.id.iden_ll).setOnClickListener { mContext.startActivity(Intent(mContext, IdentityManagerActivity::class.java)) }
                helper.getView<View>(R.id.senior_ll).setOnClickListener { mContext.startActivity(Intent(mContext, SeniorManagerActivity::class.java)) }
            }
        }
    }

    fun customExpand(position: Int, location: Int) {
        val expandable = getExpandableItem(position) ?: return
        if (!hasSubItems(expandable)) {
            expandable.isExpanded = true
            notifyItemChanged(position)
            return
        }
        var subItemCount = 0
        if (!expandable.isExpanded) {
            val list = expandable.subItems
            mData.addAll(location + 1, list as List<MultiItemEntity>)
            subItemCount += recursiveExpand(position + 1, list)

            expandable.isExpanded = true
            //            subItemCount += list.size();
        }
        notifyItemRangeInserted(location + 1, subItemCount)
    }

    fun customCollapse(position: Int, location: Int) {
        var pos = position - headerLayoutCount

        val expandable = getExpandableItem(pos) ?: return
        val subItemCount = recursiveCollapse(pos, location+1)
        expandable.isExpanded = false
        notifyItemRangeRemoved(location + 1, subItemCount)
    }


    private fun getExpandableItem(position: Int): IExpandable<*>? {
        val item = getItem(position)
        return if (isExpandable(item)) {
            item as IExpandable<*>?
        } else {
            null
        }
    }

    private fun hasSubItems(item: IExpandable<*>?): Boolean {
        if (item == null) {
            return false
        }
        val list = item.subItems
        return list != null && list.size > 0
    }

    private fun recursiveExpand(position: Int, list: List<*>): Int {
        var count = list.size
        var pos = position + list.size - 1
        var i = list.size - 1
        while (i >= 0) {
            if (list[i] is IExpandable<*>) {
                val item = list[i] as IExpandable<*>
                if (item.isExpanded && hasSubItems(item)) {
                    val subList = item.subItems
                    mData.addAll(pos + 1, subList as List<MultiItemEntity>)
                    val subItemCount = recursiveExpand(pos + 1, subList)
                    count += subItemCount
                }
            }
            i--
            pos--
        }
        return count
    }

    private fun recursiveCollapse(@IntRange(from = 0) position: Int, location: Int): Int {
        val item = getItem(position)
        if (!isExpandable(item)) {
            return 0
        }
        val expandable = item as IExpandable<*>?
        var subItemCount = 0
        if (expandable!!.isExpanded) {
            val subItems = expandable.subItems ?: return 0

            for (i in subItems.indices.reversed()) {
                val subItem = subItems[i]
                val pos = if (item != null && mData != null && !mData.isEmpty()) mData.indexOf(item) else -1
                if (pos < 0) {
                    continue
                }
                if (subItem is IExpandable<*>) {
                    subItemCount += recursiveCollapse(pos,location)
                }
                mData.removeAt(location)
                subItemCount++
            }
        }
        return subItemCount
    }

}

