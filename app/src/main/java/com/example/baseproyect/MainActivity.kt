package com.example.baseproyect

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.baseproyect.adapter.ViewPagerFragmentAdapter
import com.example.baseproyect.ui.fragments.MainScreen
import com.example.baseproyect.ui.fragments.getMainScreenForMenuItem
import com.example.baseproyect.ui.view_pager.FragmentViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_interactions_tab.view.*
import com.google.android.material.tabs.TabLayoutMediator
//
//class MainActivity : AppCompatActivity() {
//    private val viewPager by lazy {view_pager}
//    private val tabLayout by lazy {bottom_tab_layout_view}
//
//    private lateinit var mainPagerAdapter: ViewPagerFragmentAdapter
//
//    private var currentTab:Int = 0
//
//    companion object {
//        const val FIRST_TAB = 0
//        const val SECOND_TAB = 1
//        const val THIRD_TAB = 2
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.AppTheme)
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main);
//
//        viewPager.adapter = FragmentViewPagerAdapter(supportFragmentManager, lifecycle)
//
//        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
//
//        setCustomViewTabLayout()
//        viewPager.currentItem = currentTab
//    }
//
//
//    private fun setCustomViewTabLayout() {
//
//        val listOfFragmentNames: List<String> = listOf(
//            "Map",
//            "Profile",
//            "Settings")
//        val customViewTab1 = View.inflate(this, R.layout.view_interactions_tab, null)
//        val customViewTab3 = View.inflate(this, R.layout.view_interactions_tab, null)
//
//        customViewTab1.counter.text = listOfFragmentNames[0]
//        customViewTab1.icon.background = ContextCompat.getDrawable(this, R.drawable.ic_mapas_color)
//        customViewTab3.counter.text = listOfFragmentNames[2]
//        customViewTab3.icon.background = ContextCompat.getDrawable(this, R.drawable.ic_configuraciones_color)
//
//        tabLayout.getTabAt(FIRST_TAB)?.customView = customViewTab1
//        tabLayout.getTabAt(SECOND_TAB)?.customView = customViewTab3
//    }
//
//
//
//}
class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {
    private val viewPager: ViewPager by lazy {view_pager}
    private val bottomNavigationView: BottomNavigationView by lazy {bottom_navigation_view}
    private lateinit var mainPagerAdapter: ViewPagerFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        mainPagerAdapter = ViewPagerFragmentAdapter(supportFragmentManager)

        // Set items to be displayed.
        mainPagerAdapter.setItems(arrayListOf(MainScreen.LOGS, MainScreen.PROGRESS))

        // Show the default screen.
        val defaultScreen = MainScreen.LOGS
        scrollToScreen(defaultScreen)
        selectBottomNavigationViewMenuItem(defaultScreen.menuItemId)

        // Set the listener for item selection in the bottom navigation view.
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // Attach an adapter to the view pager and make it select the bottom navigation
        // menu item and change the title to proper values when selected.
        viewPager.adapter = mainPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val selectedScreen = mainPagerAdapter.getItems()[position]
                selectBottomNavigationViewMenuItem(selectedScreen.menuItemId)
                supportActionBar?.setTitle(selectedScreen.titleStringId)
            }
        })
    }

    /**
     * Scrolls `ViewPager` to show the provided screen.
     */
    private fun scrollToScreen(mainScreen: MainScreen) {
        val screenPosition = mainPagerAdapter.getItems().indexOf(mainScreen)
        if (screenPosition != viewPager.currentItem) {
            viewPager.currentItem = screenPosition
        }
    }

    /**
     * Selects the specified item in the bottom navigation view.
     */
    private fun selectBottomNavigationViewMenuItem(@IdRes menuItemId: Int) {
        bottomNavigationView.setOnNavigationItemSelectedListener(null)
        bottomNavigationView.selectedItemId = menuItemId
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    /**
     * Listener implementation for registering bottom navigation clicks.
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        getMainScreenForMenuItem(menuItem.itemId)?.let {
            scrollToScreen(it)
            supportActionBar?.setTitle(it.titleStringId)
            return true
        }
        return false
    }



}
