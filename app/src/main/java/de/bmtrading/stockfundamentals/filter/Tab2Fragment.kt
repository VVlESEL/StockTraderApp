package de.bmtrading.stockfundamentals.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import de.bmtrading.stockfundamentals.R

enum class MinMax{
    MIN,
    MAX
}

class Tab2Fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filter_tab2_fragment,container,false)

        val editTextMin5y: EditText = view.findViewById(R.id.editTextMin5y)
        setListener(editTextMin5y,Filters.change5Y,MinMax.MIN)

        val editTextMax5y: EditText = view.findViewById(R.id.editTextMax5y)
        setListener(editTextMax5y,Filters.change5Y,MinMax.MAX)

        val editTextMin2y: EditText = view.findViewById(R.id.editTextMin2y)
        setListener(editTextMin2y,Filters.change2Y,MinMax.MIN)

        val editTextMax2y: EditText = view.findViewById(R.id.editTextMax2y)
        setListener(editTextMax2y,Filters.change2Y,MinMax.MAX)

        val editTextMin1y: EditText = view.findViewById(R.id.editTextMin1y)
        setListener(editTextMin1y,Filters.change1Y,MinMax.MIN)

        val editTextMax1y: EditText = view.findViewById(R.id.editTextMax1y)
        setListener(editTextMax1y,Filters.change1Y,MinMax.MAX)

        val editTextMinYtd: EditText = view.findViewById(R.id.editTextMinYtd)
        setListener(editTextMinYtd,Filters.changeYtd,MinMax.MIN)

        val editTextMaxYtd: EditText = view.findViewById(R.id.editTextMaxYtd)
        setListener(editTextMaxYtd,Filters.changeYtd,MinMax.MAX)

        val editTextMin6M: EditText = view.findViewById(R.id.editTextMin6m)
        setListener(editTextMin6M,Filters.change6M,MinMax.MIN)

        val editTextMax6M: EditText = view.findViewById(R.id.editTextMax6m)
        setListener(editTextMax6M,Filters.change6M,MinMax.MAX)

        val editTextMin3M: EditText = view.findViewById(R.id.editTextMin3m)
        setListener(editTextMin3M,Filters.change3M,MinMax.MIN)

        val editTextMax3M: EditText = view.findViewById(R.id.editTextMax3m)
        setListener(editTextMax3M,Filters.change3M,MinMax.MAX)

        val editTextMin1M: EditText = view.findViewById(R.id.editTextMin1m)
        setListener(editTextMin1M,Filters.change1M,MinMax.MIN)

        val editTextMax1M: EditText = view.findViewById(R.id.editTextMax1m)
        setListener(editTextMax1M,Filters.change1M,MinMax.MAX)

        val editTextMin30D: EditText = view.findViewById(R.id.editTextMin30d)
        setListener(editTextMin30D,Filters.change30D,MinMax.MIN)

        val editTextMax30D: EditText = view.findViewById(R.id.editTextMax1m)
        setListener(editTextMax30D,Filters.change30D,MinMax.MAX)

        val editTextMin5D: EditText = view.findViewById(R.id.editTextMin5d)
        setListener(editTextMin5D,Filters.change5D,MinMax.MIN)

        val editTextMax5D: EditText = view.findViewById(R.id.editTextMax5d)
        setListener(editTextMax5D,Filters.change5D,MinMax.MAX)

        return view
    }

    private fun setListener(editText: EditText, filter: Filter, minMax: MinMax){
        if(minMax == MinMax.MIN) {
            if (filter.min != -Double.MAX_VALUE)
                editText.setText(filter.min.toString())
        }else{
            if (filter.max != Double.MAX_VALUE)
                editText.setText(filter.max.toString())
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var num: Double = if(minMax == MinMax.MIN) -Double.MAX_VALUE else Double.MAX_VALUE
                try{
                    num = s.toString().toDouble()
                }catch (e: Exception) {
                }
                if(minMax == MinMax.MIN) filter.min = num else filter.max = num
            }
        })
    }
}