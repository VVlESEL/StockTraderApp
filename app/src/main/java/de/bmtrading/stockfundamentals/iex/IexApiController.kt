package iex

import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class IexApiController {
    var mProgress = 0.0

    /**
     * Returns a json string resulting form a call to the chosen url
     */
    private fun fetchData(stringUrl: String) : String {
        val url = URL(stringUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        val inputStream = httpURLConnection.inputStream
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        val response = StringBuffer()
        var inputLine = bufferedReader.readLine()
        while (inputLine != null) {
            response.append(inputLine)
            inputLine = bufferedReader.readLine()
        }

        return response.toString()
    }

    /**
     * Returns a json string with the dividends for the chosen symbol and time period
     */
    fun getDividends(symbol: String, period: String): String {
        return fetchData(Endpoints.DIVIDENDS(symbol, period))
    }

    /**
     * Returns a list of the requested stocks containing information about the requested types
     */
    @Suppress("UNCHECKED_CAST")
    fun getStocksList(symbolsList: List<String>, typesList: List<String>): List<Stock> {
        mProgress = 0.0

        val stocksList = ArrayList<Stock>()

        var counter = 0
        var startIndex = 0
        for(i in 1..symbolsList.size) {
            if(i % 50 == 0 || i == symbolsList.size) {
                val data = getKeyFiguresList(symbolsList.subList(startIndex,i),typesList)

                val parser = Parser()
                val dataStringBuilder = StringBuilder(data)

                Log.d("StockFundamentals","getStockList: Progress is $i/${symbolsList.size}...")
                startIndex = i+1

                val jsonObj: JsonObject = parser.parse(dataStringBuilder) as JsonObject

                Log.d("StockFundamentals",data.toString())
                jsonObj.map { map ->
                    counter++
                    mProgress = counter/symbolsList.size.toDouble()

                    val stockObj = JsonObject()

                    val obj = map.value as JsonObject

                    val companyNamne = Types.company.name
                    if(obj[companyNamne] != null) {
                        val company = obj[companyNamne] as JsonObject
                        stockObj.putAll(company)
                    }
                    val statsName = Types.stats.name
                    if(obj[statsName] != null) {
                        val stats = obj[statsName] as JsonObject
                        stockObj.putAll(stats)
                    }
                    val quoteName = Types.quote.name
                    if(obj[quoteName] != null) {
                        val quote = obj[quoteName] as JsonObject
                        stockObj.putAll(quote)
                    }

                    val stock = Klaxon().parse<Stock>(stockObj.toJsonString())
                    if(stock != null) stocksList.add(stock)

                    val financialsName = Types.financials.name
                    if(typesList.contains(financialsName) && obj[financialsName] != null) {
                        val financialsArr = (obj[financialsName] as JsonObject)[financialsName] as JsonArray<JsonObject>
                        stock?.financials = financialsArr
                    }
                    val earningsName = Types.earnings.name
                    if(typesList.contains(earningsName) && obj[earningsName] != null) {
                        val earningsArr = (obj[earningsName] as JsonObject)[earningsName] as JsonArray<JsonObject>
                        stock?.earnings = earningsArr
                    }
                    val newsName = Types.news.name
                    if(typesList.contains(newsName) && obj[newsName] != null) {
                        val newsArr= obj[newsName] as JsonArray<JsonObject>
                        stock?.news = newsArr
                    }
                }
            }
        }
        mProgress = 0.0

        return stocksList
    }

    /**
     * Returns a JSON string with the chosen key figures for the chosen symbols list
     */
    fun getKeyFiguresList(symbolsList: List<String>, typesList: List<String>): String {
        val types = StringBuilder()
        for(s in typesList) {
            if(types.isNotEmpty()) types.append(",")
            types.append(s)
        }

        val symbols = StringBuilder()
        val res = StringBuilder()
        var counter = 0
        for(s in symbolsList) {
            if(symbols.isNotEmpty()) symbols.append(",")
            symbols.append(s)
            counter++
            if(counter >= 99 || s == symbolsList.last()) {
                val data = fetchData(Endpoints.MARKET(symbols.toString(),types.toString()))
                res.append(data)
                counter = 0
                symbols.setLength(0)
            }
        }

        return res.toString()
    }

    /**
     * Returns a list of all available symbols
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllSymbols(): ArrayList<String> {
        val res = fetchData(Endpoints.SYMBOLS)

        val parser = Parser()
        val resString = StringBuilder(res)
        val jsonArr: JsonArray<String> = parser.parse(resString) as JsonArray<String>

        val list = ArrayList<String>()

        jsonArr.mapChildren { obj ->
            if(obj["type"] == "cs"){
                list.add(obj["symbol"] as String)
            }
        }

        return list
    }

    /**
     * Returns a list of all S&P500 symbols
     */
    fun getSP500Symbols(): ArrayList<String> {
        Log.d("StockFundamentals","getSP500Symbols: Parsing wikipedia...")
        val soup = Jsoup.parse(URL("https://en.wikipedia.org/wiki/List_of_S%26P_500_companies"),8000)
        Log.d("StockFundamentals","getSP500Symbols: Finished parsing wikipedia...")
        val table = soup.select("table").first()
        val rows = table.select("tr")

        val list = ArrayList<String>()

        for(row in rows) {
            val data = row.select("td").first()
            data?: continue
            val symbol = data.text()
            list.add(symbol)
        }

        return list
    }
}