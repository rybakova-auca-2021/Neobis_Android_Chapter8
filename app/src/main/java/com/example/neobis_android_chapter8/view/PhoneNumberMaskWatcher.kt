package com.example.neobis_android_chapter8.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberMaskWatcher(private val editText: EditText) : TextWatcher {

    private var isFormatting: Boolean = false

    companion object {
        private const val MASK_FORMAT = "+000 000 000 000"
        private const val MASK_CHARACTER = '0'
    }

    init {
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) return

        isFormatting = true

        val phone = s.toString().replace("[^\\d]".toRegex(), "")
        val formattedPhone = StringBuilder()

        var maskIndex = 0
        var phoneIndex = 0

        while (maskIndex < MASK_FORMAT.length && phoneIndex < phone.length) {
            if (MASK_FORMAT[maskIndex] == MASK_CHARACTER) {
                formattedPhone.append(phone[phoneIndex])
                phoneIndex++
            } else {
                formattedPhone.append(MASK_FORMAT[maskIndex])
            }
            maskIndex++
        }

        while (maskIndex < MASK_FORMAT.length) {
            if (MASK_FORMAT[maskIndex] != MASK_CHARACTER) {
                formattedPhone.append(MASK_FORMAT[maskIndex])
            }
            maskIndex++
        }

        editText.setText(formattedPhone.toString())
        editText.setSelection(formattedPhone.length)
        isFormatting = false
    }
}
