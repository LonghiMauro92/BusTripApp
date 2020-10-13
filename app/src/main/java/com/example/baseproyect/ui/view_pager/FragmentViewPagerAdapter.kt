package com.example.baseproyect.ui.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseproyect.ui.fragments.MapFragment
import com.example.baseproyect.ui.fragments.SettingsFragment

class FragmentViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    companion object {
        const val TAB_ALL = "all"
        const val TAB_LIKE = "like"
        const val TAB_LIFT = "lift"
        const val MAX_TABS = 2
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> {
                MapFragment()
            }
//            1 -> {
//                SettingsFragment()
//            }
            else -> {
                SettingsFragment()
            }
        }

    override fun getItemCount(): Int = MAX_TABS
}