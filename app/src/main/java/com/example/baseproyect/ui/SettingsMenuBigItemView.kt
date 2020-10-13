package com.example.baseproyect.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproyect.R
import com.example.baseproyect.adapter.SettingsBigItemMenuListAdapter
import kotlinx.android.synthetic.main.view_item_menu.view.*

class SettingsMenuBigItemView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val imageViewIcon by lazy { view_settings_menu_big_item_image_view_icon }

    private val textViewLabel by lazy { view_settings_menu_big_item_text_view_label }

    private val imageViewDropDownMenu by lazy { view_settings_menu_big_item_image_arrow_down }

    val recyclerMenuList by lazy { view_settings_menu_big_item_down_list }

    private var listShown = false

    init {

        View.inflate(context, R.layout.view_item_menu, this)


        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.SettingsMenuBigItemView)

        attributes.getDrawable(R.styleable.SettingsMenuBigItemView_icon)?.let { drawable ->

            setIcon(drawable)
        }

        attributes.getString(R.styleable.SettingsMenuBigItemView_label)?.let { label ->

            setLabel(label)
        }

        val visibility = attributes.getBoolean(R.styleable.SettingsMenuBigItemView_uic_visibility, false)


        attributes.recycle()

        imageViewDropDownMenu.setOnClickListener { toggleListVisibility() }

    }


    fun setIcon(resId: Int) {

        imageViewIcon.setImageResource(resId)
    }

    fun setIcon(drawable: Drawable) {

        imageViewIcon.setImageDrawable(drawable)
    }

    fun setLabel(resId: Int) {

        textViewLabel.setText(resId)
    }

    fun setLabel(label: CharSequence) {

        textViewLabel.text = label
    }

    fun toggleListVisibility() {
        if (listShown) {
            listShown = false
            recyclerMenuList.visibility = View.GONE
            imageViewDropDownMenu.setImageDrawable(getDrawable(context, R.drawable.ic_arrow_right))
        } else {
            listShown = true
            recyclerMenuList.visibility = View.VISIBLE
            imageViewDropDownMenu.setImageDrawable(getDrawable(context, R.drawable.ic_flecha_correcta))
        }
    }

    fun setSubMenuList(menuList: List<MenuListItem>) {
        imageViewDropDownMenu.visibility = View.VISIBLE
        recyclerMenuList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerMenuList.adapter = SettingsBigItemMenuListAdapter(menuList)
    }
//
//    fun funsetAppSwitcherList(menuList: List<AppSwitcherItem>) {
//        imageViewDropDownMenu.visibility = View.VISIBLE
//        recyclerMenuList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//        recyclerMenuList.adapter = SettingsBigItemAppSwitcherAdapter(menuList)
//    }
}