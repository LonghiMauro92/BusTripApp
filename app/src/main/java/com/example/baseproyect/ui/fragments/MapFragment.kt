package com.example.baseproyect.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.baseproyect.MainActivity
import com.example.baseproyect.R
import com.example.baseproyect.ViewUtils
import com.example.baseproyect.ViewUtils.getBusIcon
import com.example.baseproyect.adapter.CustomInfoWindowAdapter
import com.example.baseproyect.ui.*
import com.example.domain.response.Coordinates
import com.example.domain.response.ListLineBus
import com.example.domain.response.RecorridoBaseInformation
import com.example.domain.response.RecorridoIntermedio
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_map_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment(), OnMapReadyCallback, OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMapClickListener {
    private lateinit var mMap: GoogleMap
    private val mMapView: MapView by lazy { map }
    private val clearMapButton by lazy { fragment_map_clear_markers }

    private val baseRouteButton1 by lazy { accion_bus_1 }
    private val baseRouteButton2 by lazy { accion_bus_2 }
    private val baseRouteButton3 by lazy { accion_bus_3 }
    private val baseRouteButton4 by lazy { accion_bus_4 }
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
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
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

//            mapFragmentViewModel.showAutoLocation()
        }
        containerDropSheetImage.setOnClickListener {
            onClickOriginDestinoButton()
            toggleBottomSheet()
        }
        btmSheetProceedSearch.setOnClickListener {
            mapFragmentViewModel.proceedSearching()
        }
    }


    private fun toggleBottomSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

    private fun onClickOriginDestinoButton() {
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
            btmSheetTextOrigin.text = ""
            btmSheetTextDestino.text = ""
            btmSheetImageDelete.visibility = View.GONE
            mMap.clear()
            mapFragmentViewModel.cleanMarkers()
        }
    }

    private fun searchOperation(data: Any?, dataAlternativa: Any?, extraData: Any?) {

        btmSheetTextOrigin.text = ""
        btmSheetTextDestino.text = ""
        btmSheetImageDelete.visibility = View.GONE
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        showIntermidateTravel(data, dataAlternativa, extraData)
    }

    private fun showIntermidateTravel(data: Any?, dataAlternativa: Any?, extraData: Any?) {
        val travel = data as RecorridoIntermedio

        val listLatLng = mutableListOf<LatLng>()
        for (i in travel.coordenadasIntermedias) {
            val lat = LatLng(i.latitude, i.longitude)
            listLatLng.add(lat)


        }
        mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    listLatLng
                ).color(if (travel.recorridoId == "1") Color.BLUE else Color.RED)
        )

        val markerParadaOrigen = mMap.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        travel.coordenadasIntermedias[0].latitude,
                        travel.coordenadasIntermedias[0].longitude
                    )
                )
                .icon(
                    ViewUtils.bitmapDescriptorFromVector(
                        requireContext(),
                        R.drawable.ic_parada_de_autobus
                    )
                )
        )
        val markerParadaDestino = mMap.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        travel.coordenadasIntermedias[travel.coordenadasIntermedias.size - 1].latitude,
                        travel.coordenadasIntermedias[travel.coordenadasIntermedias.size - 1].longitude
                    )
                )
                .icon(
                    ViewUtils.bitmapDescriptorFromVector(
                        requireContext(),
                        R.drawable.ic_parada_de_autobus
                    )
                )
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerParadaOrigen.position, 12F))

        goToFragTravelPrediction(
            MapUtils.getAddress(requireContext(), markerParadaOrigen),
            MapUtils.getAddress(requireContext(), markerParadaDestino),
            travel.recorridoId
        )

    }
    private fun goToFragTravelPrediction(
        data: Address,
        dataAlternativa: Address,
        recorridoId: String
    ) {

        val puntoOrigin = PuntoSeleccion( data)
        val puntoDest = PuntoSeleccion( dataAlternativa)

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
                puntoOrigin,
                puntoDest,
                mapFragmentViewModel.activeLine,
                recorridoId, // ver cual parametro enviarle
                mapFragmentViewModel.activeAlgorithm
            )
        )
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun setManualPoint(valor: Any?) {
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

            MapFragmentViewModel.Status.SHOW_ROUTES -> setVisibilityMenuButton(
                data.peekContent().data,
                data.peekContent().dataAlternativa
            )
            MapFragmentViewModel.Status.MANUAL_POINT -> setManualPoint(data.peekContent().data)
            MapFragmentViewModel.Status.PROCEED_SEARCHING -> searchOperation(
                data.peekContent().data,
                data.peekContent().dataAlternativa,
                data.peekContent().extraData
            )

            MapFragmentViewModel.Status.ACTIVATE_BUTTON -> btmSheetProceedSearch.isEnabled = true
            MapFragmentViewModel.Status.DEACTIVATE_BUTTON -> {
                btmSheetTextOrigin.text = ""
                btmSheetTextDestino.text = ""
                btmSheetProceedSearch.isEnabled = false
            }
            MapFragmentViewModel.Status.SHOW_LOC -> {
                val marcadorA = data.peekContent().data as Coordinates
                val marcadorB = data.peekContent().dataAlternativa as Coordinates

                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                mMap.clear()
                setRoutes(
                    mapFragmentViewModel.listRecorridoIda,
                    mapFragmentViewModel.listRecorridoVuelta
                )
                val mMarkerTest: Marker = mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(marcadorA.latitude, marcadorA.longitude))
                        .icon(
                            ViewUtils.bitmapDescriptorFromVector(
                                requireContext(),
                                getBusIcon(mapFragmentViewModel.activeLine)
                            )
                        )
                )
                val mMarkerBTest: Marker = mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(marcadorB.latitude, marcadorB.longitude))
                        .icon(
                            ViewUtils.bitmapDescriptorFromVector(
                                requireContext(),
                                getBusIcon(mapFragmentViewModel.activeLine)
                            )
                        )
                )
                mapFragmentViewModel.addSimBusMarker(mMarkerTest, mMarkerBTest)
                mMap.isMyLocationEnabled = true
                mapFragmentViewModel.showAutoLocation()
            }
            MapFragmentViewModel.Status.ERROR -> {

                invokeAlertDialog(
                    activity = requireActivity(),
                    message = data.peekContent().data.toString(),
                    positiveButtonS = data.peekContent().dataAlternativa.toString()
                )
            }
            null -> {
            }
        }
    }

    private fun setBusLines(busLines: MutableList<ListLineBus>) {
        when (busLines.size) {
            1 -> {

                baseRouteButton1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
            }
            2 -> {

                baseRouteButton1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
                baseRouteButton2.visibility =
                    if (busLines[1].linea.contains("501")) View.VISIBLE else View.GONE
            }
            3 -> {
                baseRouteButton1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
                baseRouteButton2.visibility =
                    if (busLines[1].linea.contains("501")) View.VISIBLE else View.GONE
                baseRouteButton3.visibility =
                    if (busLines[2].linea.contains("503")) View.VISIBLE else View.GONE
            }
            4 -> {
                baseRouteButton1.visibility =
                    if (busLines[0].linea.contains("500")) View.VISIBLE else View.GONE
                baseRouteButton2.visibility =
                    if (busLines[1].linea.contains("501")) View.VISIBLE else View.GONE
                baseRouteButton3.visibility =
                    if (busLines[2].linea.contains("503")) View.VISIBLE else View.GONE
                baseRouteButton4.visibility =
                    if (busLines[3].linea.contains("504")) View.VISIBLE else View.GONE
            }
        }

    }

    private fun setRoutes(
        listLatLong: MutableList<RecorridoBaseInformation>,
        mutableList: MutableList<RecorridoBaseInformation>
    ) {

        val recIda =
            listLatLong[0].coordenadas
        val recVuelta =
            mutableList[0].coordenadas
        val listLatLng = mutableListOf<LatLng>()
        val listLatLng2 = mutableListOf<LatLng>()
        for (i in recIda) {
            val lat = LatLng(i.latitude, i.longitude)
            listLatLng.add(lat)


        }
        for (i in recVuelta) {
            val lat = LatLng(i.latitude, i.longitude)
            listLatLng2.add(lat)


        }
        mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    listLatLng
                ).color(Color.BLUE)
        )

        mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    listLatLng2
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
            goToMyLocation()
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
                )
            }
        }

        mMap.uiSettings.isZoomControlsEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-37.330472, -59.112383), 13f))

        baseRouteButton1.setOnClickListener {

            mapFragmentViewModel.showBaseRoute(500)
        }
        baseRouteButton2.setOnClickListener {

            mapFragmentViewModel.showBaseRoute(501)
        }
        baseRouteButton3.setOnClickListener {

            mapFragmentViewModel.showBaseRoute(503)
        }
        baseRouteButton4.setOnClickListener {

            mapFragmentViewModel.showBaseRoute(504)
        }

        mMap.setOnMapClickListener(this)

        mMap.setOnMapLongClickListener(this)

        adapter = CustomInfoWindowAdapter(LayoutInflater.from(activity), requireContext())

        mMap.setInfoWindowAdapter(adapter)
        googleMap.setOnInfoWindowClickListener { marker ->
            //todo en el segundo parametro debo mandarle la posicion del usuario

            goToMyLocation()
            val address = MapUtils.getAddress(requireContext(), marker)
            val address2 = MapUtils.getAddressByLatLng(requireContext(), mapFragmentViewModel.myLocation)
            mapFragmentViewModel.checkLocation = false
            mapFragmentViewModel.addressOrigin=address2
            mapFragmentViewModel.addressDestination=address

            mapFragmentViewModel.proceedSearching()

//            goToFragTravelPrediction(address, address2)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    override fun onPolylineClick(p0: Polyline?) {
    }

    private fun setVisibilityMenuButton(listLatLong: Any?, dataAlternativa: Any?) {

        if (!mapFragmentViewModel.visibleOptions) {
            mapFragmentViewModel.visibleOptions = true
            setRoutes(
                listLatLong as MutableList<RecorridoBaseInformation>,
                dataAlternativa as MutableList<RecorridoBaseInformation>
            )

        } else {
            mapFragmentViewModel.visibleOptions = false
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

        val mMarkerTest: Marker = mMap.addMarker(
            MarkerOptions()
                .position(p0)
        )
        mapFragmentViewModel.addMarker(mMarkerTest)

        if (manualFlag) {
            manualFlag = false
            val address =
                MapUtils.getAddress(requireContext(), mMarkerTest)
            if (manualPoint == "ORIGIN") {
                mapFragmentViewModel.setManualOriginPoint(address)
            } else {
                mapFragmentViewModel.setManualDestPoint(address)

            }
        }
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
    }
    fun goToMyLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(requireContext()).getLastLocation()
                .addOnSuccessListener(
                    OnSuccessListener<Location?> {
                        mMap.isMyLocationEnabled = true
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it?.latitude?: 0.0, it?.longitude?: 0.0), 12F))
                    })
        }else{
            LocationServices.getFusedLocationProviderClient(requireContext()).getLastLocation()
                .addOnSuccessListener(
                    OnSuccessListener<Location?> {

                        mapFragmentViewModel.myLocation= LatLng(it?.latitude?: 0.0, it?.longitude?: 0.0)
                        mMap.isMyLocationEnabled = true
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it?.latitude?: 0.0, it?.longitude?: 0.0), 12F))
                    })
        }
    }
}