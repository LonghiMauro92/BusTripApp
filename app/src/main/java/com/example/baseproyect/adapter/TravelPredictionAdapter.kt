package com.example.baseproyect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproyect.R
import com.example.baseproyect.ui.TravelInfoLineItem

class TravelPredictionAdapter() : RecyclerView.Adapter<TravelPredictionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TravelPredictionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_travel_prediction_recycler_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TravelPredictionViewHolder, position: Int) {
        holder.bind(TravelInfoLineItem())
    }

    override fun getItemCount(): Int = 0

}