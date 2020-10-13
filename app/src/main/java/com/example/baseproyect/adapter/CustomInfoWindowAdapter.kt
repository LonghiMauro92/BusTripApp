package com.example.baseproyect.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.baseproyect.R
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class CustomInfoWindowAdapter(private val inflater: LayoutInflater) : InfoWindowAdapter {

    var onUserClickListener: ((latLng: LatLng) -> Unit)? = null
    override fun getInfoContents(m: Marker): View {
        //Carga layout personalizado.
        val v = inflater.inflate(R.layout.infowindow_layout, null)
        (v.findViewById<View>(R.id.info_window_nombre) as TextView).text = "Place"
        (v.findViewById<View>(R.id.info_window_lat) as TextView).text = "LAT: " + m.position.latitude
        (v.findViewById<View>(R.id.info_window_lng) as TextView).text = "LNG: "+ m.position.longitude
        (v.findViewById<View>(R.id.info_window_estado) as TextView).text = "Estado: Activo"

        (v.findViewById<View>(R.id.info_window_action_button) as ImageButton).background = ContextCompat.getDrawable(v.context,R.drawable.ic_ir_a_ubicacion)
        (v.findViewById<View>(R.id.info_window_image) as ImageView).background = ContextCompat.getDrawable(v.context,R.drawable.ic_ubicacion_color)
        (v.findViewById<View>(R.id.info_window_action_button) as ImageButton).setOnClickListener {
            onUserClickListener?.invoke(m.position)
        }
        return v
    }

    override fun getInfoWindow(m: Marker): View? {
        return null
    }

    companion object {
        private const val TAG = "CustomInfoWindowAdapter"
    }

}