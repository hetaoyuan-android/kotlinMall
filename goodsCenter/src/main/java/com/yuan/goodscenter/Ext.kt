package com.yuan.goodscenter

import android.widget.EditText
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton

fun NumberButton.getEditText(): EditText {
    return find(R.id.text_count)
}