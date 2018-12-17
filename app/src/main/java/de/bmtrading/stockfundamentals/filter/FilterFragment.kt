package de.bmtrading.stockfundamentals.filter

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.bmtrading.stockfundamentals.R

class FilterFragment : Fragment() {
    private var mViewPager: ViewPager? = null
    private var mButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filter_frame, container, false)

        mViewPager = view.findViewById(R.id.container)
        setupViewPager(mViewPager!!)

        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)

        mButton = view.findViewById(R.id.buttonReset)
        mButton?.setOnClickListener {
            Filters.resetFilters()
            setupViewPager(mViewPager!!)
        }

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(Tab1Fragment(),"Key Figures")
        adapter.addFragment(Tab2Fragment(),"Change")
        viewPager.adapter = adapter
    }
}