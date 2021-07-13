package fr.julocorp.jenisassistant.ui.mandatVente

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import java.util.*
import javax.inject.Inject

class EstimationFragment : Fragment() {



    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: EstimationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.estimation_fragment, container, false)
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
        viewModel.rendezVousEstimationWithProprietaire.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Loading -> loaderView.visibility = View.VISIBLE
                is Success -> {
                    loaderView.visibility = View.GONE
                }
                is Failure -> {
                    loaderView.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val RENDEZ_VOUS_ESTIMATION_ID = "rendezVousEstimationId"

        @JvmStatic
        fun newInstance(rendezVousEstimationId: UUID) = EstimationFragment().apply {
            arguments = Bundle().apply {
                putString(RENDEZ_VOUS_ESTIMATION_ID, rendezVousEstimationId.toString())
            }
        }
    }
}