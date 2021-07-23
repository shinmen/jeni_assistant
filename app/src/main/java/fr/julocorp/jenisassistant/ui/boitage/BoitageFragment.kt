package fr.julocorp.jenisassistant.ui.boitage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.ui.mandatVente.estimation.EstimationFragment

class BoitageFragment : Fragment() {

    private lateinit var boitageViewModel: BoitageViewModel
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireActivity(), getString(R.string.mapbox_access_token))
        boitageViewModel =
            ViewModelProvider(this).get(BoitageViewModel::class.java)
        return inflater.inflate(R.layout.fragment_boitage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById<MapView>(R.id.mapView).apply {
            transitionName = EstimationFragment.MAP_SHARED_ELEMENT_TRANSITION_NAME
        }
        mapView.onCreate(savedInstanceState)

        boitageViewModel =
            ViewModelProvider(this).get(BoitageViewModel::class.java)
        boitageViewModel.text.observe(viewLifecycleOwner) {}

        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(
                Style.MAPBOX_STREETS
            ) { style ->
                moveCameraToLocation(mapboxMap, Geolocation(44.852131506325264, -0.6152385412874231))
            }
        }
    }

    private fun moveCameraToLocation(
        mapboxMap: MapboxMap,
        rendezVousLocation: Geolocation
    ) {
        val position = CameraPosition.Builder()
            .target(
                LatLng(
                    rendezVousLocation.latitude,
                    rendezVousLocation.longitude
                )
            )
            .zoom(MAP_ZOOM)
            .tilt(MAP_TILT)
            .build()
        mapboxMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                position
            )
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    companion object {
        private const val MAP_TILT = 12.0
        private const val MAP_ZOOM = 13.0

        fun newInstance() = BoitageFragment()
    }
}