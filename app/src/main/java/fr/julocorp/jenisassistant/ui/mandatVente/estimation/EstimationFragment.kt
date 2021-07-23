package fr.julocorp.jenisassistant.ui.mandatVente.estimation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.Slide
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.ui.mandatVente.propriete.ProprieteFragment
import java.util.*
import javax.inject.Inject

class EstimationFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: EstimationViewModel
    private lateinit var mapView: MapView
    private val fillProprieteListener = {
            propriete: Propriete -> parentFragmentManager.beginTransaction()
                    .replace(R.id.content, ProprieteFragment.newInstance(propriete.id), ProprieteFragment.TAG)
                    .addToBackStack(null)
                    .commit()
            Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireActivity(), getString(R.string.mapbox_access_token))

        return inflater.inflate(R.layout.fragment_estimation, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EstimationViewModel::class.java)
        val rendezVousEstimationId = requireArguments().getString(RENDEZ_VOUS_ESTIMATION_ID)
        viewModel.findRendezVousEstimation(UUID.fromString(rendezVousEstimationId))
        val loaderView = view.findViewById<ProgressBar>(R.id.loader)

        mapView = view.findViewById<MapView>(R.id.mapView).apply {
            transitionName = MAP_SHARED_ELEMENT_TRANSITION_NAME
        }
        mapView.onCreate(savedInstanceState)

        with(view.findViewById<RecyclerView>(R.id.estimation_list)) {
            val contactAdapter = ContactAdapter(mutableListOf()).apply { setHasFixedSize(true) }
            val proprieteAdapter = ProprieteAdapter(fillProprieteListener).apply { setHasFixedSize(true) }
            val mergeAdapter = ConcatAdapter(contactAdapter, proprieteAdapter)
            layoutManager = GridLayoutManager(activity, ESTIMATION_LIST_SPAN_COUNT)
            adapter = mergeAdapter
            observeRendezVousEstimation(loaderView, contactAdapter)
            observePropriete(loaderView, proprieteAdapter)
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

    private fun createMarker(mapboxMap: MapboxMap, style: Style, rendezVousLocation: Geolocation) {
        val symbolManager = SymbolManager(mapView, mapboxMap, style).apply {
            iconAllowOverlap = true
            textAllowOverlap = true
        }

        val symbolOptions = SymbolOptions()
            .withLatLng(LatLng(rendezVousLocation.latitude, rendezVousLocation.longitude))
            .withIconImage(MARKER_ICON_SPRITE)
            .withIconSize(MARKER_ICON_SIZE)

        symbolManager.create(symbolOptions)
    }

    private fun observeRendezVousEstimation(loaderView: ProgressBar, contactAdapter: ContactAdapter) {
        viewModel.rendezVousEstimationWithProprietaire.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> loaderView.visibility = View.VISIBLE
                is Success -> {
                    contactAdapter.addContact(state.result.prospect)
                    val rendezVousLocation = state.result.addresseBien.geolocation
                    loaderView.visibility = View.GONE
                    mapView.getMapAsync { mapboxMap ->
                        mapboxMap.setStyle(
                            Style.MAPBOX_STREETS
                        ) { style ->
                            moveCameraToLocation(mapboxMap, rendezVousLocation)
                            createMarker(mapboxMap, style, rendezVousLocation)
                        }
                    }
                }
                is Failure -> {
                    loaderView.visibility = View.GONE
                }
            }
        }
    }

    private fun observePropriete(loaderView: ProgressBar, proprieteAdapter: ProprieteAdapter) {
        viewModel.propriete.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Loading -> loaderView.visibility = View.VISIBLE
                is Success -> {
                    proprieteAdapter.setPropriete(state.result)
                    loaderView.visibility = View.GONE
                }
                is Failure -> {
                    loaderView.visibility = View.GONE
                }
            }
        }
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
        private const val RENDEZ_VOUS_ESTIMATION_ID = "rendezVousEstimationId"
        private const val ESTIMATION_LIST_SPAN_COUNT = 2
        private const val MAP_TILT = 10.0
        private const val MAP_ZOOM = 14.0
        private const val MARKER_ICON_SPRITE = "lodging-15"
        private const val MARKER_ICON_SIZE = 1.5f
        const val MAP_SHARED_ELEMENT_TRANSITION_NAME = "MAP_TRANSITION"

        @JvmStatic
        fun newInstance(rendezVousEstimationId: UUID) = EstimationFragment().apply {
            sharedElementEnterTransition = AutoTransition()
            enterTransition = Slide()
            exitTransition = Slide()
            sharedElementReturnTransition = AutoTransition()
            arguments = Bundle().apply {
                putString(RENDEZ_VOUS_ESTIMATION_ID, rendezVousEstimationId.toString())
            }
        }
    }
}
