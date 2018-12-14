package de.bmtrading.stockfundamentals.keyfigures

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.evrencoskun.tableview.TableView
import de.bmtrading.stockfundamentals.MainActivity
import de.bmtrading.stockfundamentals.MainActivity.Companion.mIexApiController
import de.bmtrading.stockfundamentals.R
import de.bmtrading.stockfundamentals.keyfigures.ui.MyTableAdapter
import de.bmtrading.stockfundamentals.keyfigures.ui.MyTableViewListener

import iex.Stock
import iex.Types

class KeyFiguresFragment : Fragment() {
    private var mTableAdapter: MyTableAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mTextViewProgress: TextView? = null
    private var mTableView: TableView? = null

    companion object {
        @JvmStatic
        var mStockList: List<Stock>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        if (mStockList == null) {
            refreshStockList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.keyfigures_frame, container, false)

        mProgressBar = view.findViewById(R.id.progressBar)
        mTextViewProgress = view.findViewById(R.id.textViewProgress)
        mTableView = view.findViewById(R.id.tableView)

        initializeTableView(mTableView!!)

        return view
    }

    override fun onAttach(context: Context?) {
        MainActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        super.onAttach(context)
    }

    override fun onDetach() {
        MainActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        super.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.keyfigures_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.refresh -> {
                refreshStockList()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initializeTableView(tableView: TableView) {
        // Create TableView Adapter
        mTableAdapter = MyTableAdapter(context)
        tableView.adapter = mTableAdapter

        // Create listener
        tableView.tableViewListener = MyTableViewListener(tableView)
    }

    private fun showProgressBar() {
        Log.d(context?.resources?.getString(R.string.app_name), "Showing progress bar...")
        mProgressBar?.visibility = View.VISIBLE
        mTextViewProgress?.visibility = View.VISIBLE
        mTableView?.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        Log.d(context?.resources?.getString(R.string.app_name), "Hiding progress bar...")
        mProgressBar?.visibility = View.INVISIBLE
        mTextViewProgress?.visibility = View.INVISIBLE
        mTableView?.visibility = View.VISIBLE
    }

    private fun refreshStockList() {
        showProgressBar()
        mStockList = null
        setHasOptionsMenu(false)

        Thread(Runnable {
            try {
                val symbols = mIexApiController.getSP500Symbols()
                val types = listOf(Types.company.name, Types.stats.name, Types.quote.name)
                mStockList = mIexApiController.getStocksList(symbols, types)
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    val frag = fragmentManager?.findFragmentByTag(KeyFiguresFragment::class.java.simpleName)
                    if (frag != null) fragmentManager?.beginTransaction()?.remove(frag)?.commit()
                }
            }
        }).start()

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (mStockList != null) {
                    mTableAdapter?.setStockList(mStockList)
                    hideProgressBar()
                    setHasOptionsMenu(true)
                } else {
                    handler.postDelayed(this, 500)
                    mTextViewProgress?.text = "${String.format("%.0f", mIexApiController.mProgress * 100)}%"
                }
            }
        }
        handler.post(runnable)
    }
}