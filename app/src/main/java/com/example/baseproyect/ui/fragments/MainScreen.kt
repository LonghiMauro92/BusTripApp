package com.example.baseproyect.ui.fragments

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.baseproyect.R

enum class MainScreen(@IdRes val menuItemId: Int,
                      @DrawableRes val menuItemIconId: Int,
                      @StringRes val titleStringId: Int,
                      val fragment: Fragment
) {
    LOGS(R.id.bottom_navigation_item_logs, R.drawable.ic_mapas_color, R.string.home_id, MapFragment()),
    PROGRESS(R.id.bottom_navigation_item_progress, R.drawable.ic_configuraciones_color, R.string.settings_id, SettingsFragment()),
}

fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}