package de.bmtrading.stockfundamentals.tableview.ui

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.evrencoskun.tableview.TableView
import de.bmtrading.stockfundamentals.R

import iex.IexApiController
import iex.Stock
import iex.Types

class MainFragment : Fragment() {

    private var mIexApiController: IexApiController = IexApiController()
    private var mTableAdapter: MyTableAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mTextViewProgress: TextView? = null
    private var mTableView: TableView? = null
    private var mStockList: List<Stock>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(mStockList == null) {
            Thread(Runnable {
                val symbols = listOf("AAPL","AMZN")//mIexApiController.getSP500Symbols()
                val types = listOf(Types.company.name, Types.stats.name, Types.quote.name)
                mStockList = mIexApiController.getStocksList(symbols, types)
            }).start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mProgressBar = view.findViewById(R.id.progressBar)
        mTextViewProgress = view.findViewById(R.id.textViewProgress)
        mTableView = view.findViewById(R.id.tableView)

        showProgressBar()

        initializeTableView(mTableView!!)

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if(mStockList != null){
                    mTableAdapter?.setStockList(mStockList)
                    hideProgressBar()
                } else {
                    handler.postDelayed(this, 500)
                    mTextViewProgress?.text = "${String.format("%.0f",mIexApiController.mProgress*100)}%"
                }
            }
        }
        handler.postDelayed(runnable, 500)

        return view
    }

    private fun initializeTableView(tableView: TableView) {
        // Create TableView Adapter
        mTableAdapter = MyTableAdapter(context)
        tableView.adapter = mTableAdapter

        // Create listener
        tableView.tableViewListener = MyTableViewListener(tableView)
    }

    private fun showProgressBar() {
        Log.d(resources.getString(R.string.app_name),"Showing progress bar...")
        mProgressBar?.visibility = View.VISIBLE
        mTextViewProgress?.visibility = View.VISIBLE
        mTableView?.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        Log.d(resources.getString(R.string.app_name),"Hiding progress bar...")
        mProgressBar?.visibility = View.INVISIBLE
        mTextViewProgress?.visibility = View.INVISIBLE
        mTableView?.visibility = View.VISIBLE
    }
}