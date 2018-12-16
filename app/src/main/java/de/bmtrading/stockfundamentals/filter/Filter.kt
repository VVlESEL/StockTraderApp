package de.bmtrading.stockfundamentals.filter

import android.util.Log
import iex.Stock

object Filters {
    val marketCap = Filter("MarketCap")
    val beta = Filter("Beta")
    val dividendYield = Filter("DividendYield")
    val returnOnEquity = Filter("ReturnOnEquity")
    val returnOnAssets = Filter("ReturnOnAssets")
    val peRatio = Filter("PERatio")
    val profitMargin = Filter("ProfitMargin")

    val change5Y = Filter("Change5Y")
    val change2Y = Filter("Change2Y")
    val change1Y = Filter("Change1Y")
    val changeYtd = Filter("ChangeYtd")
    val change6M = Filter("Change6M")
    val change3M = Filter("Change3M")
    val change1M = Filter("Change1M")
    val change30D = Filter("Change30D")
    val change5D = Filter("Change5D")

    fun checkStock(stock: Stock): Boolean {

        return stock.marketcap > marketCap.min &&
                stock.marketcap < marketCap.max &&
                stock.beta > beta.min &&
                stock.beta < beta.max &&
                stock.dividendYield > dividendYield.min &&
                stock.dividendYield < dividendYield.max &&
                stock.returnOnEquity > returnOnEquity.min &&
                stock.returnOnEquity < returnOnEquity.max &&
                stock.returnOnAssets > returnOnAssets.min &&
                stock.returnOnAssets < returnOnAssets.max &&
                stock.peRatio > peRatio.min &&
                stock.peRatio < peRatio.max &&
                stock.profitMargin > profitMargin.min &&
                stock.profitMargin < profitMargin.max &&
                stock.year5ChangePercent*100 > change5Y.min &&
                stock.year5ChangePercent*100 < change5Y.max &&
                stock.year2ChangePercent*100 > change2Y.min &&
                stock.year2ChangePercent*100 < change2Y.max &&
                stock.year1ChangePercent*100 > change1Y.min &&
                stock.year1ChangePercent*100 < change1Y.max &&
                stock.ytdChange*100 > changeYtd.min &&
                stock.ytdChange*100 < changeYtd.max &&
                stock.month6ChangePercent*100 > change6M.min &&
                stock.month6ChangePercent*100 < change6M.max &&
                stock.month3ChangePercent*100 > change3M.min &&
                stock.month3ChangePercent*100 < change3M.max &&
                stock.month1ChangePercent*100 > change1M.min &&
                stock.month1ChangePercent*100 < change1M.max &&
                stock.day30ChangePercent*100 > change30D.min &&
                stock.day30ChangePercent*100 < change30D.max &&
                stock.day5ChangePercent*100 > change5D.min &&
                stock.day5ChangePercent*100 < change5D.max
    }
}

data class Filter(val name: String){
    var min: Double = -Double.MAX_VALUE
    set(value) {
        field = value
        MyDatabaseHelper.updateFilter(name,value,null)
    }

    var max: Double = Double.MAX_VALUE
    set(value) {
        field = value
        MyDatabaseHelper.updateFilter(name,null,value)
    }

    init {
        MyDatabaseHelper.getFilter(this)
    }
}