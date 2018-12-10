package iex

import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.IOException
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
    fun getStocksList(symbolsList: List<String>, typesList: List<String>): List<Stock> {
        mProgress = 0.0

        val stocksList = ArrayList<Stock>()
        val gson = Gson()

        var counter = 0
        var startIndex = 0
        for(i in 1..symbolsList.size) {
            if(i % 50 == 0 || i == symbolsList.size) {
                val data = getKeyFiguresList(symbolsList.subList(startIndex,i),typesList)

                Log.d("StockFundamentals","getStockList: Progress is $i/${symbolsList.size}...")
                startIndex = i+1

                val jsonObj = JSONObject(data)

                jsonObj.keys().forEach { s ->
                    counter++
                    mProgress = counter/symbolsList.size.toDouble()

                    val stockObj = JSONObject()
                    val obj = jsonObj.getJSONObject(s)

                    val companyName = Types.company.name
                    if (obj[companyName] != null) {
                        val temp = obj.getJSONObject(companyName)
                        temp.keys().forEach {
                            stockObj.put(it,temp[it])
                        }
                    }
                    val statsName = Types.stats.name
                    if (obj[statsName] != null) {
                        val temp = obj.getJSONObject(statsName)
                        temp.keys().forEach {
                            stockObj.put(it,temp[it])
                        }
                    }
                    val quoteName = Types.quote.name
                    if (obj[quoteName] != null) {
                        val temp = obj.getJSONObject(quoteName)
                        temp.keys().forEach {
                            stockObj.put(it,temp[it])
                        }
                    }

                    val stock = gson.fromJson<Stock>(stockObj.toString(),Stock::class.java)
                    if (stock != null) stocksList.add(stock)

                    val financialsName = Types.financials.name
                    if(typesList.contains(financialsName) && obj[financialsName] != null) {
                        val temp = obj.getJSONObject(financialsName)
                        val tempArr = temp.getJSONArray(financialsName)
                        stock?.financials = tempArr
                    }
                    val earningsName = Types.earnings.name
                    if(typesList.contains(earningsName) && obj[earningsName] != null) {
                        val temp = obj.getJSONObject(earningsName)
                        val tempArr = temp.getJSONArray(earningsName)
                        stock?.earnings = tempArr
                    }
                    val newsName = Types.news.name
                    if(typesList.contains(newsName) && obj[newsName] != null) {
                        val temp = obj.getJSONObject(newsName)
                        val tempArr = temp.getJSONArray(newsName)
                        stock?.news = tempArr
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
    private fun getKeyFiguresList(symbolsList: List<String>, typesList: List<String>): String {
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
/*
    /**
     * Returns a list of all available symbols
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllSymbols(): ArrayList<String> {
        val list = ArrayList<String>()

        val res = fetchData(Endpoints.SYMBOLS)
        val jsonArr = JSONArray(res)
        for(i in 0 until jsonArr.length()-1) {
            val jsonObj = jsonArr.getJSONObject(i)
            if(jsonObj["type"] == "cs"){
                list.add(jsonObj["symbol"] as String)
            }
        }

        return list
    }
*/
    /**
     * Returns a list of all S&P500 symbols
     */
    @Throws(IOException::class)
    fun getSP500Symbols(): ArrayList<String> {
        Log.d("StockFundamentals","getSP500Symbols: Parsing wikipedia...")
        val soup: Document
        try {
            soup = Jsoup.parse(URL("https://en.wikipedia.org/wiki/List_of_S%26P_500_companies"), 8000)
        }catch (e: Exception) {
            throw e
        }
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

    /**
     * Returns a list of the sectors
     */
    fun getSectorsList(): List<Sector> {
        val sectorList = ArrayList<Sector>()
        val gson = Gson()

        val data = fetchData(Endpoints.SECTORS)
        val jsonArr = JSONArray(data)

        for(i in 0 until jsonArr.length()-1){
            val obj = jsonArr.getJSONObject(i)

            val sector = gson.fromJson<Sector>(obj.toString(),Sector::class.java)
            if (sector != null) sectorList.add(sector)
        }

        return sectorList
    }
}