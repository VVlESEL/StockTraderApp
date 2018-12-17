package de.bmtrading.stockfundamentals.filter.util

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import de.bmtrading.stockfundamentals.R
import de.bmtrading.stockfundamentals.filter.Filter

enum class MinMax {
    MIN,
    MAX
}

object EditTextModifier {
    fun setListener(context: Context, editText: EditText, filter: Filter, minMax: MinMax) {
        if (minMax == MinMax.MIN) {
            if (filter.min != -Double.MAX_VALUE) editText.setText(filter.min.toString())
        } else {
            if (filter.max != Double.MAX_VALUE) editText.setText(filter.max.toString())
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var num: Double = if (minMax == MinMax.MIN) -Double.MAX_VALUE else Double.MAX_VALUE
                try {
                    num = s.toString().toDouble()
                } catch (e: Exception) {
                }
                if (minMax == MinMax.MIN){
                    filter.min = num
                } else {
                    filter.max = num
                }
                if(filter.min >= filter.max) editText.setTextColor(Color.RED)
                else editText.setTextColor(ContextCompat.getColor(context,R.color.selected_text_color))
            }
        })
    }
}