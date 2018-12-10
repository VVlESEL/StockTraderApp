package de.bmtrading.stockfundamentals.overview

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import de.bmtrading.stockfundamentals.MainActivity.Companion.mIexApiController
import de.bmtrading.stockfundamentals.R
import de.bmtrading.stockfundamentals.keyfigures.KeyFiguresFragment.Companion.mStockList
import iex.Sector

class OverviewFragment : Fragment() {
    private var mProgressBar: ProgressBar? = null
    private var mListView: ListView? = null

    companion object {
        @JvmStatic
        var mSectorList: List<Sector>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(mStockList == null) {
            Thread(Runnable {
                mSectorList = mIexApiController.getSectorsList()
            }).start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.overview_frame, container, false)

        mProgressBar = view.findViewById(R.id.progressBar)
        mListView = view.findViewById(R.id.listView)

        showProgressBar()

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if(mSectorList != null){
                    val arr: Array<String> = Array(mSectorList!!.size,{_ -> ""})
                    mSectorList!!.forEachIndexed { index, sector -> arr[index] = sector.name }

                    mListView?.adapter = ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arr)
                    hideProgressBar()
                } else {
                    handler.postDelayed(this, 500)
                }
            }
        }
        handler.postDelayed(runnable, 500)

        return view
    }

    private fun showProgressBar() {
        Log.d(resources.getString(R.string.app_name),"Showing progress bar...")
        mProgressBar?.visibility = View.VISIBLE
        mListView?.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        Log.d(resources.getString(R.string.app_name),"Hiding progress bar...")
        mProgressBar?.visibility = View.INVISIBLE
        mListView?.visibility = View.VISIBLE
    }
}