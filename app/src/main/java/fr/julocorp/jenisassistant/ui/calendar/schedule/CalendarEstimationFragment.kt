package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.ui.common.adresseGeolocation.GeoLocationListAdapter
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarEstimationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CalendarEstimationViewModel
    private lateinit var dateTimePickerViewModel: DateTimePickerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_estimation_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory).get(CalendarEstimationViewModel::class.java)
        dateTimePickerViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(DateTimePickerViewModel::class.java)

        with(view.findViewById<AppCompatAutoCompleteTextView>(R.id.address)) {
            val adapter = GeoLocationListAdapter(context, android.R.layout.simple_dropdown_item_1line)
            setAdapter(adapter)
            viewModel.addressesPropositions.observe(viewLifecycleOwner) { propositions ->
                adapter.proposeNewAddresses(propositions)
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    val fullAddress = propositions[position]
                }
            }

            addTextChangedListener {
                if (it != null && it.length >= THRESHOLD) {
                    viewModel.findAddress(it.toString())
                }
            }
        }

        view.findViewById<TextView>(R.id.datetime_picker).run {
            setOnClickListener {
                DatePickerDialogFragment.newInstance().show(parentFragmentManager, TAG)
            }
            dateTimePickerViewModel.dateTimePicked.observe(viewLifecycleOwner) {
                text = it.format(DateTimeFormatter.ofPattern("EE d MMM y à H:mm"))
            }
        }
    }

    companion object {
        const val TAG = "CalendarEstimationFragment"
        private const val THRESHOLD = 6

        @JvmStatic
        fun newInstance() = CalendarEstimationFragment()
    }
}