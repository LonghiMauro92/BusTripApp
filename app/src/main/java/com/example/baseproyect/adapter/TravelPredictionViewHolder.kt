package com.example.baseproyect.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproyect.ViewUtils
import com.example.baseproyect.ui.MapUtils
import com.example.domain.response.TravelBody
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_ride_data.view.*

class TravelPredictionViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(item: TravelBody, algoritmo: String) = with(itemView) {
        val latlngOrigin = LatLng(item.coordenadaOrigen.latitude, item.coordenadaOrigen.longitude)
        val latlngDestino = LatLng(
            item.coordenadaDestino.latitude,
            item.coordenadaDestino.longitude
        )

        itemView.fragment_card_linea_text_value.text = item.linea
        itemView.fragment_card_algorithm_text_value.text = algoritmo
        itemView.fragment_card_time_text_value.text = item.tiempo.toString()
        itemView.fragment_card_distance_text_value.text = item.distancia.toString()
        itemView.fragment_card_recorrido_text_value_origin.text = MapUtils.getAddressByLatLng(
            itemView.context,
            latlngOrigin
        ).name
        itemView.fragment_card_recorrido_text_value_destino.text = MapUtils.getAddressByLatLng(
            itemView.context,
            latlngDestino
        ).name
        itemView.fragment_card_info.setCardBackgroundColor(
            ContextCompat.getColor(
                itemView.context, ViewUtils.getBusCard(
                    item.linea
                )
            )
        )
        itemView.fragment_card_title_text_view.setTextColor(
            ContextCompat.getColor(
                itemView.context, ViewUtils.getBusColorRoute(
                    item.linea
                )
            )
        )

    }

}