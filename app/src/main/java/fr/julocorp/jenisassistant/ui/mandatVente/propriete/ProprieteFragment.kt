package fr.julocorp.jenisassistant.ui.mandatVente.propriete

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import java.util.*
import javax.inject.Inject

class ProprieteFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProprieteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_propriete, container, false)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProprieteViewModel::class.java)
        val proprieteId = requireArguments().getString(PROPRIETE_ID)
        viewModel.findPropriete(UUID.fromString(proprieteId))
        viewModel.findRequiredCaracteristiques(UUID.fromString(proprieteId))

        val loaderView = view.findViewById<ProgressBar>(R.id.loader)
        val recyclerListCaracteristiques = view.findViewById<RecyclerView>(R.id.propriete_caracteristique_list)
        val surfaceHabitableView = view.findViewById<TextView>(R.id.propriete_surface_habitable)
        val natureProprieteView = view.findViewById<Spinner>(R.id.propriete_nature)

        with(view.findViewById<Spinner>(R.id.propriete_nature)) {
            adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, listOf<String>("youhou", "youha","youpdidou"))
        }

        observeListCaracteristiques(loaderView, recyclerListCaracteristiques)
        observeRequiredCaracteristiqueFilled(view)
    }

    private fun observeListCaracteristiques(loaderView: ProgressBar, recyclerListCaracteristiques: RecyclerView) {
        viewModel.propriete.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Loading -> loaderView.visibility = View.VISIBLE
                is Success -> {
                    loaderView.visibility = View.GONE
                    with(recyclerListCaracteristiques) {
                        layoutManager = GridLayoutManager(requireActivity(), COLUMN_COUNT)
                        adapter = ProprieteCaracteristiquesAdapter(state.result.caracteristiques)
                    }
                }
                is Failure -> {
                    loaderView.visibility = View.GONE
                }
            }
        }
    }

    private fun observeRequiredCaracteristiqueFilled(view: View) {
        val natureProprieteLabelView = view.findViewById<TextView>(R.id.label_propriete_nature)
        val natureProprieteGroup = view.findViewById<Group>(R.id.propriete_nature_group)

        val surfaceHabitableLabelView = view.findViewById<TextView>(R.id.label_surface_habitable)
        val surfaceHabitableGroup = view.findViewById<Group>(R.id.propriete_surface_habitable_group)
        viewModel.requiredCaracteristiquesFilled.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Loading -> {}
                is Failure -> {}
                is Success -> {
                    state.result.forEach { caracteristique ->
                        when(caracteristique.definition.defaultLabel) {

                        }
                    }
                }
            }
        }
    }

    private fun adjustVisibilityIfCaracteristiqueIsFilled(isFilled: Boolean, group: Group) {
        if (isFilled) {
            group.visibility = View.GONE
        } else {
            group.visibility = View.VISIBLE
        }
    }

    companion object {
        const val TAG = "propriete_fragment"
        private const val PROPRIETE_ID = "proprieteId"
        private const val COLUMN_COUNT = 2

        @JvmStatic
        fun newInstance(proprieteId: UUID) = ProprieteFragment().apply {
            enterTransition = Slide()
            exitTransition = Slide()
            arguments = Bundle().apply {
                putString(PROPRIETE_ID, proprieteId.toString())
            }
        }
    }
}