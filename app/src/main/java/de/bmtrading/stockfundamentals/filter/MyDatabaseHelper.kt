package de.bmtrading.stockfundamentals.filter

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.util.Log
import android.view.Display

object MyDatabaseHelper{
    private val DB_Name = "DB_FILTER"
    private val TABLE_NAME = "FILTER_TABLE"
    private val COLUMN_NAME = "NAME"
    private val COLUMN_MIN = "MIN"
    private val COLUMN_MAX = "MAX"

    private var myDatabse: SQLiteDatabase? = null
    fun initialize(activity: Activity) {
        try{
            myDatabse = activity.openOrCreateDatabase(DB_Name, Context.MODE_PRIVATE,null)
            myDatabse?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_NAME VARCHAR, $COLUMN_MIN DOUBLE, $COLUMN_MAX DOUBLE)")
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFilter(pFilter: Filter) {
        val cursor = myDatabse?.query(TABLE_NAME,arrayOf(COLUMN_MIN, COLUMN_MAX),"$COLUMN_NAME like '${pFilter.name}'",null, null,null,null)

        if(cursor == null) return
        val minIndex = cursor.getColumnIndex(COLUMN_MIN)
        val maxIndex = cursor.getColumnIndex(COLUMN_MAX)

        cursor.moveToFirst()
        if(cursor.count > 0){
            Log.d("StockFundamentals",cursor.toString())
            pFilter.min = cursor.getDouble(minIndex)
            pFilter.max = cursor.getDouble(maxIndex)
        }
        cursor.close()

    }

    fun updateFilter(pName: String, pMin: Double?, pMax: Double?) {
        val values = ContentValues()
        values.put(COLUMN_NAME, pName)
        if(pMin != null) values.put(COLUMN_MIN, pMin)
        if(pMax != null) values.put(COLUMN_MAX, pMax)

        val id = myDatabse?.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        if (id == -1L) {
            myDatabse?.update(TABLE_NAME, values, "$COLUMN_NAME=?", Array(1){pName})
        }
    }
}