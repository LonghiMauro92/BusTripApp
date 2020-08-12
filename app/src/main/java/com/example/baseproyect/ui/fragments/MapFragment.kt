package com.example.baseproyect.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.baseproyect.R
import com.example.baseproyect.ViewUtils
import com.example.baseproyect.ui.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map_fragment.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MapFragment : Fragment(), OnMapReadyCallback, OnMyLocationButtonClickListener,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnInfoWindowClickListener,GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMapClickListener {
    private lateinit var mMap: GoogleMap
    private val mMapView: MapView by lazy { map }
    private val baseRouteButton by lazy { fragment_map_base_route }

    private val baseRouteButton1 by lazy { accion_bus_1 }
    private val baseRouteButton2 by lazy { accion_bus_2 }
    private val baseRouteButton3 by lazy { accion_bus_3 }
    private val LOCATION_REQUEST_CODE = 1

    private val mapFragmentViewModel by viewModel<MapFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mapFragmentViewModel.liveData.observe(::getLifecycle, ::updateUI)
        mapFragmentViewModel.setLoading()

        if (mMapView != null) {
            mMapView.onCreate(null)
            mMapView.onResume()
            mMapView.getMapAsync(this)
        }
    }

    private fun updateUI(data: Event<MapFragmentViewModel.Data>) {
        val pokemonCardDetailData = data.getContentIfNotHandled()
        when (pokemonCardDetailData?.status) {
            MapFragmentViewModel.Status.LOADING -> { setBusLines(data.peekContent().data as MutableList<String>)
            }

            MapFragmentViewModel.Status.SHOW_ROUTES -> setVisibilityMenuButton(data.peekContent().data)
        }
    }

    private fun setBusLines(busLines : MutableList<String>) {
        accion_bus_1.visibility = if (busLines.contains("500")) View.VISIBLE else View.GONE
        accion_bus_2.visibility = if (busLines.contains("501")) View.VISIBLE else View.GONE
        accion_bus_3.visibility = if (busLines.contains("502")) View.VISIBLE else View.GONE
    }

    private fun setRoutes(listLatLong: MutableList<LatLng>) {

        val polylineBlueRute = mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    listLatLong
                ).color(Color.BLUE)
        )
//        val polylineRedRute = mMap.addPolyline(
//            PolylineOptions()
//                .clickable(true)
//                .addAll(
//                    ViewUtils.RECORRIDO_ROJO
//                ).color(Color.RED)
//        )

        mMap.setOnPolylineClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_fragment, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                );
            }
        }

        mMap.uiSettings.isZoomControlsEnabled = true;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-37.330472, -59.112383), 13f))

        baseRouteButton1.setOnClickListener {

            mapFragmentViewModel.showBaseRoute()
        }
        baseRouteButton2.setOnClickListener {

//            mapFragmentViewModel.showBaseRoute()
        }
        baseRouteButton3.setOnClickListener {

//            mapFragmentViewModel.showBaseRoute()
        }
        mMap.setOnMapClickListener(this)

        mMap.setOnMapLongClickListener(this)

    }

    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    override fun onPolylineClick(p0: Polyline?) {
    }
//--- viewmodel

    private fun configureDrawablesButton(@DrawableRes drawable: Int, @DrawableRes drawable2: Int) {
        mapFragmentViewModel.imageOpenButton = drawable
        mapFragmentViewModel.imageCloseButton = drawable2
        setIconFloatingButton(mapFragmentViewModel.imageOpenButton)
    }

    private fun setIconFloatingButton(@DrawableRes drawable: Int) {
//        baseRouteButton.setImageResource(drawable)
    }

    private fun setVisibilityMenuButton(listLatLong:Any?) {

        if (!mapFragmentViewModel.visibleOptions) {
            mapFragmentViewModel.visibleOptions = true
            setRoutes(listLatLong as MutableList<LatLng>)
//            setIconFloatingButton(mapFragmentViewModel.imageCloseButton)

        } else {
            mapFragmentViewModel.visibleOptions = false
//            setIconFloatingButton(mapFragmentViewModel.imageOpenButton)
            mMap.clear()
        }
    }

    override fun onMapClick(puntoPulsado: LatLng) {

        mMap.setOnMapClickListener {
            mMap.addMarker(MarkerOptions().position(puntoPulsado).title("Marker in Sydney"))

        }
    }

    override fun onInfoWindowClick(p0: Marker) {

    }

    override fun onMapLongClick(p0: LatLng) {
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.5f, Long: %2$.5f",
            p0.latitude,
            p0.longitude
        )
        // marker común

        var mMarkerTest: Marker = mMap.addMarker(
            MarkerOptions()
                .position(p0)
                .title("Conductor")
                .snippet(snippet)
        )
    }
}