package de.bmtrading.stockfundamentals.overview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import iex.Sector
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.bmtrading.stockfundamentals.R
import java.text.SimpleDateFormat
import java.util.*

class MyArrayAdapter(con: Context, resourceId: Int, list: List<Sector>) :
        ArrayAdapter<Sector>(con, resourceId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val sector = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.overview_list_item_layout, parent, false)
        }
        // Lookup view for data population
        val tvSector = view!!.findViewById(R.id.textViewSector) as TextView
        val tvChange = view.findViewById(R.id.textViewChange) as TextView
        val tvChangeTime = view.findViewById(R.id.textViewChangeTime) as TextView
        // Populate the data into the template view using the data object
        tvSector.text = sector.name
        tvChange.text = "${String.format("%.2f",sector.performance*100)}%"
        tvChangeTime.text = convertLongToTime(sector.lastUpdated)
        //set color
        val color: Int
        when(sector.performance >= 0){
            true -> color = ContextCompat.getColor(tvChange.context, R.color.profit_percent_text_color)
            false -> color = ContextCompat.getColor(tvChange.context, R.color.loss_percent_text_color)
        }
        tvChange.setTextColor(color)

        // Return the completed view to render on screen
        return view
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(date)
    }
}