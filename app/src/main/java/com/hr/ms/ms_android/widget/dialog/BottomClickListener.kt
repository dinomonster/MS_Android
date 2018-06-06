package com.hr.ms.ms_android.widget.dialog

import com.adorkable.iosdialog.ActionSheetDialog

class BottomClickListener : ActionSheetDialog.OnSheetItemClickListener {
    override fun onClick(which: Int) {
        _OnClick?.invoke(which)
    }

    private var _OnClick: ((which: Int) -> Unit)? = null
    fun click(listener: (which: Int) -> Unit) : BottomClickListener {
        _OnClick = listener
        return this
    }

}