package com.hr.ms.ms_android.widget

import android.text.Editable
import android.text.TextWatcher

class TextChangeListener : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        _OnChange?.invoke(p0, p1, p2, p3)
    }

    private var _OnChange: ((p0: CharSequence?, p1: Int, p2: Int, p3: Int) -> Unit)? = null
    fun textChange(listener: (p0: CharSequence?, p1: Int, p2: Int, p3: Int) -> Unit): TextChangeListener {
        _OnChange = listener
        return this
    }
}