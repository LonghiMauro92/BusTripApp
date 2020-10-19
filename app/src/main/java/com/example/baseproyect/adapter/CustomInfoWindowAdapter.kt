package com.example.baseproyect.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.baseproyect.R
import com.example.baseproyect.ui.MapUtils
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(
    private val inflater: LayoutInflater,
    private val context: Context
) : InfoWindowAdapter {

    var onUserClickListener: ((latLng: LatLng) -> Unit)? = null
    override fun getInfoContents(m: Marker): View {
        //Carga layout personalizado.
        val v = inflater.inflate(R.layout.infowindow_layout, null)

        val address =
            MapUtils.getAddress(context, m)
        (v.findViewById<View>(R.id.info_window_nombre) as TextView).text = "Place"
        (v.findViewById<View>(R.id.info_window_address) as TextView).text = address.name
        (v.findViewById<View>(R.id.info_window_lat) as TextView).text =
            "LAT: " + address.marker?.position?.latitude
        (v.findViewById<View>(R.id.info_window_lng) as TextView).text =
            "LNG: " + address.marker?.position?.longitude
        (v.findViewById<View>(R.id.info_window_estado) as TextView).text = "Estado: Activo"

        (v.findViewById<View>(R.id.info_window_action_button) as ImageButton).background =
            ContextCompat.getDrawable(v.context, R.drawable.ic_ir_a_ubicacion)
        (v.findViewById<View>(R.id.info_window_image) as ImageView).background =
            ContextCompat.getDrawable(v.context, R.drawable.ic_ubicacion_color)
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