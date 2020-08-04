package com.example.baseproyect.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.baseproyect.R
import com.example.baseproyect.ViewUtils
import com.example.baseproyect.ui.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.fragment_map_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment(), OnMapReadyCallback, OnMyLocationButtonClickListener,
    GoogleMap.OnPolylineClickListener {
    private lateinit var mMap: GoogleMap
    private val mMapView: MapView by lazy { map }
    private val baseRouteButton by lazy { fragment_map_base_route }
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
            MapFragmentViewModel.Status.LOADING -> {
                configureDrawablesButton(
                    R.drawable.smu_os_ui_components_ic_plus,
                    R.drawable.ic_close
                )
                setIconFloatingButton(mapFragmentViewModel.imageOpenButton)
            }

            MapFragmentViewModel.Status.SHOW_ROUTES -> setVisibilityMenuButton()
        }
    }

    private fun setRoutes() {

        val centerPoint = LatLng(-37.328030, -59.137375)
        mMap.addMarker(MarkerOptions().position(centerPoint).title("Marker in Sydney"))

        val polylineBlueRute = mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    ViewUtils.RECORRIDO_AZUL
                ).color(Color.BLUE)
        )
        val polylineRedRute = mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    ViewUtils.RECORRIDO_ROJO
                ).color(Color.RED)
        )

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

        baseRouteButton.setOnClickListener {

            mapFragmentViewModel.showBaseRoute()
        }
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
        baseRouteButton.setImageResource(drawable)
    }

    private fun setVisibilityMenuButton() {

        if (!mapFragmentViewModel.visibleOptions) {
            mapFragmentViewModel.visibleOptions = true

            setRoutes()
            setIconFloatingButton(mapFragmentViewModel.imageCloseButton)

        } else {
            mapFragmentViewModel.visibleOptions = false
            setIconFloatingButton(mapFragmentViewModel.imageOpenButton)
            mMap.clear()
        }
    }
}