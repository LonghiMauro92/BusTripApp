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
import androidx.fragment.app.FragmentTransaction
import com.example.baseproyect.MainActivity
import com.example.baseproyect.R
import com.example.baseproyect.adapter.CustomInfoWindowAdapter
import com.example.baseproyect.ui.Address
import com.example.baseproyect.ui.Event
import com.example.baseproyect.ui.MapUtils
import com.example.domain.response.ListLineBus
import com.example.domain.response.RecorridoBaseInformation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_map_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment(), OnMapReadyCallback, OnMyLocationButtonClickListener,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMapClickListener {
    private lateinit var mMap: GoogleMap
    private val mMapView: MapView by lazy { map }
    private val clearMapButton by lazy { fragment_map_clear_markers }

    private val baseRouteButton1 by lazy { accion_bus_1 }
    private val baseRouteButton2 by lazy { accion_bus_2 }
    private val baseRouteButton3 by lazy { accion_bus_3 }
    private val layoutBottomSheet by lazy { bottom_sheet }
    private val containerDropSheetImage by lazy { bottom_sheet_drop_image }

    private val btmSheetImageOrigin by lazy { label_origin_imageView }
    private val btmSheetImageDestino by lazy { label_destino_image }
    private val btmSheetImageDelete by lazy { label_delete_imageView }

    private val btmSheetTextOrigin by lazy { label_origin_value }
    private val btmSheetTextDestino by lazy { label_destino_value }
    private val btmSheetProceedSearch by lazy { btn_buscar }

    private val LOCATION_REQUEST_CODE = 1
    private var manualFlag = false
    private var manualPoint = ""

    private lateinit var adapter: CustomInfoWindowAdapter
    private lateinit var sheetBehavior: BottomSheetBehavior<View>

    private val mapFragmentViewModel by viewModel<MapFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mapFragmentViewModel.liveData.observe(::getLifecycle, ::updateUI)
        mapFragmentViewModel.setLoading()

        mMapView.onCreate(null)
        mMapView.onResume()
        mMapView.getMapAsync(this)


        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         */
        sheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(
                bottomSheet: View,
                newState: Int
            ) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(
                bottomSheet: View,
                slideOffset: Float
            ) {
            }
        })

        //------------------------------------------------------------------------
        // ------------------ setting listeners ----------------------------------
        //------------------------------------------------------------------------

        clearMapButton.setOnClickListener {
            mMap.clear()
            mapFragmentViewModel.cleanMarkers()
        }
        containerDropSheetImage.setOnClickListener {
            onClickOriginDestinoButton()
            toggleBottomSheet()
        }
        btmSheetProceedSearch.setOnClickListener {
            mapFragmentViewModel.proceedSearching()
        }
    }


    fun toggleBottomSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

    fun onClickOriginDestinoButton() {
        btmSheetImageOrigin.setOnClickListener {

            manualFlag = true
            manualPoint = "ORIGIN"
            btmSheetImageDelete.visibility = View.VISIBLE
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        btmSheetImageDestino.setOnClickListener {
            manualFlag = true
            manualPoint = "DESTINO"
            btmSheetImageDelete.visibility = View.VISIBLE
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        btmSheetImageDelete.setOnClickListener {
            manualFlag = false
            manualPoint = ""
            btmSheetTextOrigin.text = " - "
            btmSheetTextDestino.text = " - "
            btmSheetImageDelete.visibility = View.GONE
            mMap.clear()
            mapFragmentViewModel.cleanMarkers()
        }
    }

    fun searchOperation() {

        btmSheetTextOrigin.text = " - "
        btmSheetTextDestino.text = " - "
        btmSheetImageDelete.visibility = View.GONE
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        goToFragTravelPrediction()
    }
    fun goToFragTravelPrediction(){

        val ft: FragmentTransaction =
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
        ft.setCustomAnimations(
            R.anim.slide_in,
            R.anim.face_out,
            R.anim.face_in,
            R.anim.slide_out
        )
        ft.replace(
            R.id.account,
            FragmentTravelPrediction.newInstance(
                100.0,
                100.0
            )
        )
        ft.addToBackStack(null)
        ft.commit()
    }

    fun setManualPoint(valor: Any?) {
        val address = valor as Address?
        if (manualPoint == "ORIGIN") {
            btmSheetTextOrigin.text = address?.name
        } else {

            btmSheetTextDestino.text = address?.name
        }

        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun updateUI(data: Event<MapFragmentViewModel.Data>) {
        val pokemonCardDetailData = data.getContentIfNotHandled()
        when (pokemonCardDetailData?.status) {
            MapFragmentViewModel.Status.LOADING -> {
                setBusLines(data.peekContent().data as MutableList<ListLineBus>)
            }

            MapFragmentViewModel.Status.SHOW_ROUTES -> setVisibilityMenuButton(data.peekContent().data)
            MapFragmentViewModel.Status.MANUAL_POINT -> setManualPoint(data.peekContent().data)
            MapFragmentViewModel.Status.PROCEED_SEARCHING -> searchOperation()

            MapFragmentViewModel.Status.ACTIVATE_BUTTON -> btmSheetProceedSearch.isEnabled = true
            MapFragmentViewModel.Status.DEACTIVATE_BUTTON -> btmSheetProceedSearch.isEnabled = false
        }
    }

    private fun setBusLines(busLines: MutableList<ListLineBus>) {
        when (busLines.size) {
            1 -> {

                accion_bus_1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
            }
            2 -> {

                accion_bus_1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
                accion_bus_2.visibility =
                    if (busLines[1].linea.contains("501")) View.VISIBLE else View.GONE
            }
            3 -> {
                accion_bus_1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
                accion_bus_2.visibility =
                    if (busLines[1].linea.contains("501")) View.VISIBLE else View.GONE
                accion_bus_3.visibility =
                    if (busLines[1].linea.contains("503")) View.VISIBLE else View.GONE
            }
        }

    }

    private fun setRoutes(listLatLong: MutableList<RecorridoBaseInformation>) {

        val rec =
            listLatLong[0].coordenadas

        val listLatLng = mutableListOf<LatLng>()
        for (i in rec) {
            val lat = LatLng(i.lat, i.lng)
            listLatLng.add(lat)

            val polylineBlueRute = mMap.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .addAll(
                        listLatLng
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

            mapFragmentViewModel.showBaseRoute(500)
        }
        baseRouteButton2.setOnClickListener {

            mapFragmentViewModel.showBaseRoute(500)
        }
        baseRouteButton3.setOnClickListener {

            mapFragmentViewModel.showBaseRoute(500)
        }
        mMap.setOnMapClickListener(this)

        mMap.setOnMapLongClickListener(this)

        adapter = CustomInfoWindowAdapter(LayoutInflater.from(activity), requireContext())

        mMap.setInfoWindowAdapter(adapter)
        googleMap.setOnInfoWindowClickListener { marker ->
            val ft: FragmentTransaction =
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
            ft.setCustomAnimations(
                R.anim.slide_in,
                R.anim.face_out,
                R.anim.face_in,
                R.anim.slide_out
            )
            ft.replace(
                R.id.account,
                FragmentTravelPrediction.newInstance(
                    marker.position.latitude,
                    marker.position.longitude
                )
            )
            ft.addToBackStack(null)
            ft.commit()
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
//        baseRouteButton.setImageResource(drawable)
    }

    private fun setVisibilityMenuButton(listLatLong: Any?) {

        if (!mapFragmentViewModel.visibleOptions) {
            mapFragmentViewModel.visibleOptions = true
            setRoutes(listLatLong as MutableList<RecorridoBaseInformation>)
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

        var mMarkerTest: Marker = mMap.addMarker(
            MarkerOptions()
                .position(p0)
//                .title("Conductor")
//                .snippet(snippet)
        )
        mapFragmentViewModel.addMarker(mMarkerTest)

        if (manualFlag) {
            manualFlag = false
            val address =
                MapUtils.getAddress(requireContext(), mMarkerTest)
            if (manualPoint == "ORIGIN") {
                mapFragmentViewModel.setManualOriginPoint(mMarkerTest,address)
            } else {
                mapFragmentViewModel.setManualDestPoint(mMarkerTest, address)

            }
        }
    }
}