package de.bmtrading.stockfundamentals

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import de.bmtrading.stockfundamentals.filter.FilterFragment
import de.bmtrading.stockfundamentals.filter.MyDatabaseHelper
import de.bmtrading.stockfundamentals.keyfigures.KeyFiguresFragment
import de.bmtrading.stockfundamentals.sectors.SectorsFragment

class MainActivity : AppCompatActivity() {
    private var mBackPressedTimestamp: Long = 0
    private lateinit var mDrawerLayout: DrawerLayout
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyDatabaseHelper.initialize(this)

        if (savedInstanceState == null) {
            supportActionBar?.title = getString(R.string.sectors)
            supportFragmentManager.beginTransaction().add(R.id.content_frame, SectorsFragment(), SectorsFragment::class.java.simpleName).commit()
        }

        mDrawerLayout = findViewById(R.id.drawer_layout)
        mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            override fun onDrawerClosed(drawerView: View) {
                val fragKeyFig = supportFragmentManager.findFragmentByTag(KeyFiguresFragment::class.java.simpleName)
                if (fragKeyFig != null && fragKeyFig.isVisible) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                super.onDrawerClosed(drawerView)
            }
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle!!)
        mDrawerToggle?.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            mDrawerLayout.closeDrawers()

            if (menuItem.itemId == R.id.nav_sectors) {
                supportActionBar?.title = getString(R.string.sectors)
                supportFragmentManager.beginTransaction().add(R.id.content_frame, SectorsFragment(), SectorsFragment::class.java.simpleName).commit()
            }
            if (menuItem.itemId == R.id.nav_filter) {
                supportActionBar?.title = getString(R.string.filter)
                supportFragmentManager.beginTransaction().add(R.id.content_frame, FilterFragment(), FilterFragment::class.java.simpleName).commit()
            }
            if (menuItem.itemId == R.id.nav_key_figures) {
                supportActionBar?.title = getString(R.string.key_figures)
                supportFragmentManager.beginTransaction().add(R.id.content_frame, KeyFiguresFragment(), KeyFiguresFragment::class.java.simpleName).commit()
            } else {
                val fragKeyFig = supportFragmentManager.findFragmentByTag(KeyFiguresFragment::class.java.simpleName)
                if (fragKeyFig != null && fragKeyFig.isVisible) {
                    supportFragmentManager?.beginTransaction()?.remove(fragKeyFig)?.commit()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (mDrawerToggle!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() < mBackPressedTimestamp + 2000) System.exit(0)
        mBackPressedTimestamp = System.currentTimeMillis()
        Toast.makeText(this, getString(R.string.sure_exit), Toast.LENGTH_SHORT).show()
    }
}