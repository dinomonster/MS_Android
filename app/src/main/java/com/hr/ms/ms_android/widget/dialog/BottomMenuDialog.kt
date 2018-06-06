package com.hr.ms.ms_android.widget.dialog

import android.content.Context
import com.adorkable.iosdialog.ActionSheetDialog

class BottomMenuDialog {
    fun showBottomDialog(context: Context, text: Array<String>, vararg listener: BottomClickListener) {
        var dialog = ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
        for (i in 0 until text.size) {
            dialog.addSheetItem(text[i], ActionSheetDialog.SheetItemColor.Black, listener[i])
        }
        dialog.show()
    }
}