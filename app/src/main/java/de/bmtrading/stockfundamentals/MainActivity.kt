package de.bmtrading.stockfundamentals

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import de.bmtrading.stockfundamentals.filter.FilterFragment
import de.bmtrading.stockfundamentals.keyfigures.KeyFiguresFragment
import de.bmtrading.stockfundamentals.sectors.SectorsFragment
import iex.IexApiController

class MainActivity : AppCompatActivity() {
    private var mBackPressedTimestamp: Long = 0

    companion object {
        @JvmStatic
        val mIexApiController: IexApiController = IexApiController()
        lateinit var mDrawerLayout: DrawerLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.content_frame, SectorsFragment(), SectorsFragment::class.java.simpleName).commit()
        }

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            mDrawerLayout.closeDrawers()

            if(menuItem.itemId == R.id.nav_sectors){
                supportFragmentManager.beginTransaction().add(R.id.content_frame, SectorsFragment(), SectorsFragment::class.java.simpleName).commit()
            }
            if(menuItem.itemId == R.id.nav_filter){
                supportFragmentManager.beginTransaction().add(R.id.content_frame, FilterFragment(), FilterFragment::class.java.simpleName).commit()
            }
            if(menuItem.itemId == R.id.nav_key_figures){
                supportFragmentManager.beginTransaction().add(R.id.content_frame, KeyFiguresFragment(), KeyFiguresFragment::class.java.simpleName).commit()
            }
            true
        }
    }

    override fun onBackPressed(){
        //check if key figures fragment is visible
        val frag = supportFragmentManager.findFragmentByTag(KeyFiguresFragment::class.java.simpleName)
        if(frag != null && frag.isVisible) {
            supportFragmentManager?.beginTransaction()?.remove(frag)?.commit()
        }else{
            if (System.currentTimeMillis() < mBackPressedTimestamp + 2000) System.exit(0)
            mBackPressedTimestamp = System.currentTimeMillis()
            Toast.makeText(this, getString(R.string.sure_exit), Toast.LENGTH_SHORT).show()
        }
    }
}