package de.bmtrading.stockfundamentals.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import de.bmtrading.stockfundamentals.R
import de.bmtrading.stockfundamentals.filter.util.EditTextModifier
import de.bmtrading.stockfundamentals.filter.util.MinMax

class Tab1Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filter_tab1_fragment, container, false)

        val editTextMinMarketCap: EditText = view.findViewById(R.id.editTextMinMarketCap)
        EditTextModifier.setListener(context!!, editTextMinMarketCap, Filters.marketCap, MinMax.MIN)

        val editTextMaxMarketCap: EditText = view.findViewById(R.id.editTextMaxMarketCap)
        EditTextModifier.setListener(context!!, editTextMaxMarketCap, Filters.marketCap, MinMax.MAX)

        val editTextMinBeta: EditText = view.findViewById(R.id.editTextMinBeta)
        EditTextModifier.setListener(context!!, editTextMinBeta, Filters.beta, MinMax.MIN)

        val editTextMaxBeta: EditText = view.findViewById(R.id.editTextMaxBeta)
        EditTextModifier.setListener(context!!, editTextMaxBeta, Filters.beta, MinMax.MAX)

        val editTextMinDividendYield: EditText = view.findViewById(R.id.editTextMinDividendYield)
        EditTextModifier.setListener(context!!, editTextMinDividendYield, Filters.dividendYield, MinMax.MIN)

        val editTextMaxDividendYield: EditText = view.findViewById(R.id.editTextMaxDividendYield)
        EditTextModifier.setListener(context!!, editTextMaxDividendYield, Filters.dividendYield, MinMax.MAX)

        val editTextMinReturnOnEquity: EditText = view.findViewById(R.id.editTextMinReturnOnEquity)
        EditTextModifier.setListener(context!!, editTextMinReturnOnEquity, Filters.returnOnEquity, MinMax.MIN)

        val editTextMaxReturnOnEquity: EditText = view.findViewById(R.id.editTextMaxReturnOnEquity)
        EditTextModifier.setListener(context!!, editTextMaxReturnOnEquity, Filters.returnOnEquity, MinMax.MAX)

        val editTextMinReturnOnAssets: EditText = view.findViewById(R.id.editTextMinReturnOnAssets)
        EditTextModifier.setListener(context!!, editTextMinReturnOnAssets, Filters.returnOnAssets, MinMax.MIN)

        val editTextMaxReturnOnAssets: EditText = view.findViewById(R.id.editTextMaxReturnOnAssets)
        EditTextModifier.setListener(context!!, editTextMaxReturnOnAssets, Filters.returnOnAssets, MinMax.MAX)

        val editTextMinPeRatio: EditText = view.findViewById(R.id.editTextMinPeRatio)
        EditTextModifier.setListener(context!!, editTextMinPeRatio, Filters.peRatio, MinMax.MIN)

        val editTextMaxPeRatio: EditText = view.findViewById(R.id.editTextMaxPeRatio)
        EditTextModifier.setListener(context!!, editTextMaxPeRatio, Filters.peRatio, MinMax.MAX)

        val editTextMinProfitMargin: EditText = view.findViewById(R.id.editTextMinProfitMargin)
        EditTextModifier.setListener(context!!, editTextMinProfitMargin, Filters.profitMargin, MinMax.MIN)

        val editTextMaxProfitMargin: EditText = view.findViewById(R.id.editTextMaxProfitMargin)
        EditTextModifier.setListener(context!!, editTextMaxProfitMargin, Filters.profitMargin, MinMax.MAX)

        return view
    }
}