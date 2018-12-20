package de.bmtrading.stockfundamentals.keyfigures

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.evrencoskun.tableview.TableView
import de.bmtrading.stockfundamentals.R
import de.bmtrading.stockfundamentals.filter.Filters
import de.bmtrading.stockfundamentals.filter.Filters.checkStock
import de.bmtrading.stockfundamentals.keyfigures.KeyFiguresFragment.Companion.mStockList
import de.bmtrading.stockfundamentals.keyfigures.ui.MyTableAdapter
import de.bmtrading.stockfundamentals.keyfigures.ui.MyTableViewListener
import iex.IexApiController

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.keyfigures_frame, container, false)

        mProgressBar = view.findViewById(R.id.progressBar)
        mTextViewProgress = view.findViewById(R.id.textViewProgress)
        mTableView = view.findViewById(R.id.tableView)

        initializeTableView(mTableView!!)

        if (mStockList != null) {
            mTableAdapter?.setStockList(mStockList?.filter { checkStock(it) })
            hideProgressBar()
            setHasOptionsMenu(true)
        } else {
            refreshStockList()
        }

        return view
    }

    override fun onAttach(context: Context?) {
        if(activity!!.findViewById<DrawerLayout>(R.id.drawer_layout) != null){
            val drawer: DrawerLayout = activity!!.findViewById(R.id.drawer_layout)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
        super.onAttach(context)
    }

    override fun onDetach() {
        val drawer: DrawerLayout = activity!!.findViewById(R.id.drawer_layout)
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        super.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.keyfigures_menu, menu)

        val drawable: Drawable = menu!!.findItem(R.id.refresh).icon
        drawable.mutate()
        drawable.setColorFilter(ContextCompat.getColor(context!!,R.color.selected_text_color), PorterDuff.Mode.SRC_ATOP)
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
                val symbols = IexApiController.getSP500Symbols()
                val types = listOf(Types.company.name, Types.stats.name, Types.quote.name)
                mStockList = IexApiController.getStocksList(symbols, types)
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
            var dots: Int = 0

            override fun run() {
                if (mStockList != null) {
                    mTableAdapter?.setStockList(mStockList?.filter { checkStock(it) })
                    hideProgressBar()
                    setHasOptionsMenu(true)
                } else {
                    handler.postDelayed(this, 500)
                    mTextViewProgress?.text = "${String.format("%.0f", IexApiController.mProgress * 100)}%"
                }
            }
        }
        handler.post(runnable)
    }
}