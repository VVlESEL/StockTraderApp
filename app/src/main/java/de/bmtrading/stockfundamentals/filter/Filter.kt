package de.bmtrading.stockfundamentals.filter

object Filters {
    val change5Y: Filter = Filter("Change5Y")
    val change2Y: Filter = Filter("Change2Y")
    val change1Y: Filter = Filter("Change1Y")
    val changeYtd: Filter = Filter("ChangeYtd")
    val change6M: Filter = Filter("Change6M")
    val change3M: Filter = Filter("Change3M")
    val change1M: Filter = Filter("Change1M")
    val change30D: Filter = Filter("Change30D")
    val change5D: Filter = Filter("Change5D")
}

data class Filter(val name: String){
    var min: Double = Double.MIN_VALUE
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