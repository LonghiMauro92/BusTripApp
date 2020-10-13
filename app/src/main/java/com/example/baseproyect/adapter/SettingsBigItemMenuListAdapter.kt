package com.example.baseproyect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproyect.R
import com.example.baseproyect.ui.MenuListItem


class SettingsBigItemMenuListAdapter(
    private val menuList: List<MenuListItem>
) : RecyclerView.Adapter<SettingsBigItemMenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SettingsBigItemMenuViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_settings_menu_big_item_recycler_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SettingsBigItemMenuViewHolder, position: Int) {
        holder.bind(menuList[position])
    }

    override fun getItemCount(): Int = menuList.size

}