package com.example.baseproyect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.baseproyect.R
import com.example.baseproyect.ui.MenuListItem
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.view_item_menu.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : Fragment() {
    private val menuItemViewHistory by lazy { menu_item_view_history }
    private val menuItemSetBusLines by lazy { menu_item_set_bus_lines }
    private val menuItemAlgorithms by lazy { menu_item_view_algorithms }

    private var listShown = false
    lateinit var menuBussLinesList : List<MenuListItem>
    lateinit var menuAlgoritmosList : List<MenuListItem>

    private val mapViewModel by sharedViewModel<MapFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuItemViewHistory.setOnClickListener {

            Toast.makeText(
                context, "History",
                Toast.LENGTH_LONG
            ).show()
        }


        menuItemViewHistory.view_settings_menu_big_item_text_view_label.text = "Ver Historial"
        menuItemSetBusLines.view_settings_menu_big_item_text_view_label.text = "Ver Ruta Colectivos"
        menuItemAlgorithms.view_settings_menu_big_item_text_view_label.text = "Algoritmos"

        menuItemAlgorithms.view_settings_menu_big_item_image_view_icon.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_algoritmo)
        )

        menuItemViewHistory.view_settings_menu_big_item_image_view_icon.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_ir_a_ubicacion)
        )

        menuItemSetBusLines.view_settings_menu_big_item_image_view_icon.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_autobus_lines)
        )


        menuBussLinesList = listOf<MenuListItem>(
            MenuListItem("500 - AMARILLO") { lineBusActionItem(500) },
            MenuListItem("501 - BLANCO"){ lineBusActionItem(500) },
            MenuListItem("503 - AZUL"){ lineBusActionItem(503) },
            MenuListItem("504 - VERDE"){ lineBusActionItem(504) }
        )
        menuItemSetBusLines.setSubMenuList(menuBussLinesList)

        menuAlgoritmosList = listOf<MenuListItem>(
            MenuListItem("Alg - RegresionAcumulado"){ AlgorithmActionItem("RegresionAcumulado") },
            MenuListItem("Alg - RegresionDiferenciaDeCeldas"){ AlgorithmActionItem("RegresionDiferenciaDeCeldas") },
            MenuListItem("Alg - TiempoEntreCoordenadasComplejo"){ AlgorithmActionItem("TiempoEntreCoordenadasComplejo") }
        )

        menuItemAlgorithms.setSubMenuList(menuAlgoritmosList)
    }

    fun lineBusActionItem(linea:Int) {
        mapViewModel.showBaseRoute(linea)
    }

    fun AlgorithmActionItem(algorithm:String) {
        mapViewModel.activeAlgorithm = algorithm
    }

}