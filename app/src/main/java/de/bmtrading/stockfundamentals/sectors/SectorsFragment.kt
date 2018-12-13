package de.bmtrading.stockfundamentals.sectors

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import de.bmtrading.stockfundamentals.MainActivity.Companion.mIexApiController
import de.bmtrading.stockfundamentals.R
import iex.Sector

class SectorsFragment : Fragment() {
    private var mProgressBar: ProgressBar? = null
    private var mListView: ListView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    companion object {
        @JvmStatic
        var mSectorList: List<Sector>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(mSectorList == null) {
            Thread(Runnable {
                mSectorList = mIexApiController.getSectorsList()
            }).start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sectors_frame, container, false)

        mProgressBar = view.findViewById(R.id.progressBar)
        mListView = view.findViewById(R.id.listView)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        val colorBg = ContextCompat.getColor(mSwipeRefreshLayout!!.context, R.color.unselected_background_color)
        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeColor(colorBg)
        val colorProgress = ContextCompat.getColor(mSwipeRefreshLayout!!.context,R.color.colorAccent)
        mSwipeRefreshLayout?.setColorSchemeColors(colorProgress)

        showProgressBar()

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if(mSectorList != null){
                    val myArrayAdapter = MyArrayAdapter(context!!,R.layout.sectors_list_item_layout, mSectorList!!)
                    mListView?.adapter = myArrayAdapter

                    mSwipeRefreshLayout?.setOnRefreshListener {
                        Thread(Runnable {
                            mSectorList = mIexApiController.getSectorsList()
                            activity?.runOnUiThread({
                                myArrayAdapter.notifyDataSetChanged()
                                mSwipeRefreshLayout?.isRefreshing = false
                            })
                        }).start()
                    }

                    hideProgressBar()
                } else {
                    handler.postDelayed(this, 500)
                }
            }
        }
        handler.post(runnable)

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