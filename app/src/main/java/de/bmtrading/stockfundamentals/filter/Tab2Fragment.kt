package de.bmtrading.stockfundamentals.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import de.bmtrading.stockfundamentals.R
import de.bmtrading.stockfundamentals.filter.util.EditTextModifier.setListener
import de.bmtrading.stockfundamentals.filter.util.MinMax

class Tab2Fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filter_tab2_fragment,container,false)

        val editTextMin5y: EditText = view.findViewById(R.id.editTextMin5y)
        setListener(context!!, editTextMin5y,Filters.change5Y, MinMax.MIN)

        val editTextMax5y: EditText = view.findViewById(R.id.editTextMax5y)
        setListener(context!!, editTextMax5y,Filters.change5Y,MinMax.MAX)

        val editTextMin2y: EditText = view.findViewById(R.id.editTextMin2y)
        setListener(context!!, editTextMin2y,Filters.change2Y,MinMax.MIN)

        val editTextMax2y: EditText = view.findViewById(R.id.editTextMax2y)
        setListener(context!!, editTextMax2y,Filters.change2Y,MinMax.MAX)

        val editTextMin1y: EditText = view.findViewById(R.id.editTextMin1y)
        setListener(context!!, editTextMin1y,Filters.change1Y,MinMax.MIN)

        val editTextMax1y: EditText = view.findViewById(R.id.editTextMax1y)
        setListener(context!!, editTextMax1y,Filters.change1Y,MinMax.MAX)

        val editTextMinYtd: EditText = view.findViewById(R.id.editTextMinYtd)
        setListener(context!!, editTextMinYtd,Filters.changeYtd,MinMax.MIN)

        val editTextMaxYtd: EditText = view.findViewById(R.id.editTextMaxYtd)
        setListener(context!!, editTextMaxYtd,Filters.changeYtd,MinMax.MAX)

        val editTextMin6M: EditText = view.findViewById(R.id.editTextMin6m)
        setListener(context!!, editTextMin6M,Filters.change6M,MinMax.MIN)

        val editTextMax6M: EditText = view.findViewById(R.id.editTextMax6m)
        setListener(context!!, editTextMax6M,Filters.change6M,MinMax.MAX)

        val editTextMin3M: EditText = view.findViewById(R.id.editTextMin3m)
        setListener(context!!, editTextMin3M,Filters.change3M,MinMax.MIN)

        val editTextMax3M: EditText = view.findViewById(R.id.editTextMax3m)
        setListener(context!!, editTextMax3M,Filters.change3M,MinMax.MAX)

        val editTextMin1M: EditText = view.findViewById(R.id.editTextMin1m)
        setListener(context!!, editTextMin1M,Filters.change1M,MinMax.MIN)

        val editTextMax1M: EditText = view.findViewById(R.id.editTextMax1m)
        setListener(context!!, editTextMax1M,Filters.change1M,MinMax.MAX)

        val editTextMin30D: EditText = view.findViewById(R.id.editTextMin30d)
        setListener(context!!, editTextMin30D,Filters.change30D,MinMax.MIN)

        val editTextMax30D: EditText = view.findViewById(R.id.editTextMax1m)
        setListener(context!!, editTextMax30D,Filters.change30D,MinMax.MAX)

        val editTextMin5D: EditText = view.findViewById(R.id.editTextMin5d)
        setListener(context!!, editTextMin5D,Filters.change5D,MinMax.MIN)

        val editTextMax5D: EditText = view.findViewById(R.id.editTextMax5d)
        setListener(context!!, editTextMax5D,Filters.change5D,MinMax.MAX)

        return view
    }
}