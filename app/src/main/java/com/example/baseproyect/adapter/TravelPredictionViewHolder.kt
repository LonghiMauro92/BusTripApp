package com.example.baseproyect.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproyect.ui.TravelInfoLineItem
import kotlinx.android.synthetic.main.fragment_ride_data.view.*

class TravelPredictionViewHolder (itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(item: TravelInfoLineItem) = with(itemView) {
        itemView.fragment_card_time_text_value.text = item.label.tiempo.toString()
        itemView.fragment_card_distance_text_value.text = item.label.distancia.toString()
//        setOnClickListener {
//            item.action()
//        }
    }
}