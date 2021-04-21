package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.GeoLocationListAdapter
import fr.julocorp.jenisassistant.infrastructure.calendar.CalendarEstimationViewModel
import fr.julocorp.jenisassistant.infrastructure.repository.ApiGeoGouvAdresseSearcher

class CalendarEstimationFragment : Fragment() {

    private lateinit var viewModel: CalendarEstimationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_estimation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CalendarEstimationViewModel::class.java)

        with(view.findViewById<AppCompatAutoCompleteTextView>(R.id.address)) {
            val addresseSearcher = ApiGeoGouvAdresseSearcher()
            val adapter =
                GeoLocationListAdapter(context, android.R.layout.simple_dropdown_item_1line)
            setAdapter(adapter)
            viewModel.addressesPropositions.observe(viewLifecycleOwner) { propositions ->
                adapter.proposeNewAddresses(propositions)
            }
            addTextChangedListener {
                if (it != null && it.length >= THRESHOLD) {
                    viewModel.findAdress(it.toString(), addresseSearcher)
                }
            }
        }
    }

    companion object {
        const val TAG = "CalendarEstimationFragment"
        private const val THRESHOLD = 3

        fun newInstance() = CalendarEstimationFragment()
    }
}