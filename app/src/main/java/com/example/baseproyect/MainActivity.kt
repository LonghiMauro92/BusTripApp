package com.example.baseproyect

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.baseproyect.adapter.ViewPagerFragmentAdapter
import com.example.baseproyect.ui.view_pager.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_interactions_tab.view.*
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val viewPager by lazy {view_pager}
    private val tabLayout by lazy {bottom_tab_layout_view}

    private lateinit var mainPagerAdapter: ViewPagerFragmentAdapter

    private var currentTab:Int = 0

    companion object {
        const val FIRST_TAB = 0
        const val SECOND_TAB = 1
        const val THIRD_TAB = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        viewPager.adapter = FragmentViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        setCustomViewTabLayout()
        viewPager.currentItem = currentTab
    }


    private fun setCustomViewTabLayout() {

        val listOfFragmentNames: List<String> = listOf(
            "Map",
            "Profile",
            "Settings")
        val customViewTab1 = View.inflate(this, R.layout.view_interactions_tab, null)
        val customViewTab3 = View.inflate(this, R.layout.view_interactions_tab, null)

        customViewTab1.counter.text = listOfFragmentNames[0]
        customViewTab1.icon.background = ContextCompat.getDrawable(this, R.drawable.ic_mapas_color)
        customViewTab3.counter.text = listOfFragmentNames[2]
        customViewTab3.icon.background = ContextCompat.getDrawable(this, R.drawable.ic_configuraciones_color)

        tabLayout.getTabAt(FIRST_TAB)?.customView = customViewTab1
        tabLayout.getTabAt(SECOND_TAB)?.customView = customViewTab3
    }



}
