package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.ui.common.GeoLocationListAdapter
import fr.julocorp.jenisassistant.ui.calendar.CalendarEstimationViewModel
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
import fr.julocorp.jenisassistant.infrastructure.repository.ApiGeoGouvAdresseSearcher
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import java.text.SimpleDateFormat

class CalendarEstimationFragment : Fragment() {

    private lateinit var viewModel: CalendarEstimationViewModel
    private lateinit var dateTimePickerViewModel: DateTimePickerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_estimation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CalendarEstimationViewModel::class.java)
        dateTimePickerViewModel = ViewModelProvider(requireActivity()).get(DateTimePickerViewModel::class.java)

        with(view.findViewById<AppCompatAutoCompleteTextView>(R.id.address)) {
            val addresseSearcher = ApiGeoGouvAdresseSearcher()
            val adapter =
                GeoLocationListAdapter(context, android.R.layout.simple_dropdown_item_1line)
            setAdapter(adapter)
            viewModel.addressesPropositions.observe(viewLifecycleOwner) {
                    propositions -> adapter.proposeNewAddresses(propositions)
            }

            addTextChangedListener {
                if (it != null && it.length >= THRESHOLD) {
                    viewModel.findAddress(it.toString(), addresseSearcher)
                }
            }
        }

        view.findViewById<TextView>(R.id.datetime_picker).run {
            setOnClickListener {
                DatePickerDialogFragment.newInstance().show(parentFragmentManager, TAG)
            }
            dateTimePickerViewModel.dateTimePicked.observe(viewLifecycleOwner) {
                SimpleDateFormat("EE d MMM y à H:mm").apply {
                    text = format(it.time)
                }
            }
        }
    }

    companion object {
        const val TAG = "CalendarEstimationFragment"
        private const val THRESHOLD = 3

        @JvmStatic
        fun newInstance() = CalendarEstimationFragment()
    }
}