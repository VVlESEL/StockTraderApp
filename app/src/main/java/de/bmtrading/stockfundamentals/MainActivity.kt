package de.bmtrading.stockfundamentals

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.Toast
import de.bmtrading.stockfundamentals.keyfigures.KeyFiguresFragment
import de.bmtrading.stockfundamentals.overview.OverviewFragment
import iex.IexApiController
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    private var mBackPressedTimestamp: Long = 0
    private lateinit var mDrawerLayout:DrawerLayout

    companion object {
        @JvmStatic
        val mIexApiController: IexApiController = IexApiController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.content_frame, OverviewFragment(), OverviewFragment::class.java.simpleName).commit()
        }

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mDrawerLayout.closeDrawers()

            if(menuItem.itemId == R.id.nav_key_figures){
                supportFragmentManager.beginTransaction().add(R.id.content_frame, KeyFiguresFragment(), KeyFiguresFragment::class.java.simpleName).commit()
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            true
        }
    }

    override fun onBackPressed(){
        //if fraement visible go back to main screen
        val frag = supportFragmentManager.findFragmentByTag(KeyFiguresFragment::class.java.simpleName)
        if(frag != null && frag.isVisible) {
            frag.view?.visibility = View.GONE
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }else{
            if (System.currentTimeMillis() < mBackPressedTimestamp + 2000) System.exit(0)
            mBackPressedTimestamp = System.currentTimeMillis()
            Toast.makeText(this, getString(R.string.sure_exit), Toast.LENGTH_SHORT).show()
        }
    }
}