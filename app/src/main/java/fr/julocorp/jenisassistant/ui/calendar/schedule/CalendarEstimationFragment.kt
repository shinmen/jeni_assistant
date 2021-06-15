package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.calendar.exception.InvalidInput
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.infrastructure.debounce
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.infrastructure.error
import fr.julocorp.jenisassistant.infrastructure.success
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarFragment
import fr.julocorp.jenisassistant.ui.common.adresseGeolocation.GeoLocationListAdapter
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarEstimationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var calendarViewModel: CalendarEstimationViewModel
    private lateinit var dateTimePickerViewModel: DateTimePickerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.calendar_estimation_fragment, container, false)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(CalendarEstimationViewModel::class.java)
        dateTimePickerViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(DateTimePickerViewModel::class.java)

        calendarViewModel.rendezVousEstimationInput.observe(viewLifecycleOwner) { rendezVousEstimation ->
            syncAddress(view, rendezVousEstimation)
            syncRendezvousDate(view, rendezVousEstimation)

            view.findViewById<Button>(R.id.save_button).setOnClickListener {
                rendezVousEstimation.fullName = view.findViewById<TextView>(R.id.proprietaire).text.toString()
                rendezVousEstimation.email = view.findViewById<TextView>(R.id.contact_email).text.toString()
                rendezVousEstimation.phoneNumber = view.findViewById<TextView>(R.id.contact_phone).text.toString()
                rendezVousEstimation.comment = view.findViewById<TextView>(R.id.contact_commentaire).text.toString()
                calendarViewModel.updateRendezvousInput(rendezVousEstimation)

                calendarViewModel.schedule()
            }
        }



        dateTimePickerViewModel.dateTimePicked.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.datetime_picker).text =
                it.format(DateTimeFormatter.ofPattern("EE d MMM y à H:mm"))
        }

        val loaderView = view.findViewById<ProgressBar>(R.id.loader)

        calendarViewModel.rendezVousEstimationSave.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> loaderView.visibility = View.VISIBLE
                is Success<Boolean> -> {
                    loaderView.visibility = View.GONE
                    Snackbar.make(
                        view.rootView.findViewById(R.id.content),
                        getString(R.string.rendezvous_save),
                        Snackbar.LENGTH_SHORT
                    )
                        .success(requireContext())
                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.content,
                            CalendarFragment.newInstance(),
                            CalendarFragment.TAG
                        )
                        .commit()
                }
                is Failure -> {
                    loaderView.visibility = View.GONE
                    when (state.error) {
                        is InvalidInput -> {
                            val validationsErrors = state.error.validationErrors.map { error ->
                                getString(
                                    R.string.error_map,
                                    getString(error.key),
                                    getString(error.value)
                                )
                            }.joinToString(",\n")
                            Snackbar.make(
                                view.rootView.findViewById(R.id.content),
                                validationsErrors,
                                Snackbar.LENGTH_SHORT
                            ).error(requireContext())
                        }
                        else -> Snackbar.make(
                            view.rootView.findViewById(R.id.content),
                            getString(R.string.generic_error),
                            Snackbar.LENGTH_SHORT
                        ).error(requireContext())
                    }
                }
            }
        }
    }

    private fun syncRendezvousDate(
        view: View,
        rendezVousEstimationInput: RendezVousEstimationInput
    ) = with(view.findViewById<TextView>(R.id.datetime_picker)) {
        setOnClickListener {
            DatePickerDialogFragment.newInstance().show(parentFragmentManager, TAG)
        }

        val updateInput = debounce<String>(delayMillis = 1000L, scope = lifecycleScope) { date ->
            rendezVousEstimationInput.rendezVousDate =
                LocalDateTime.parse(date, DateTimeFormatter.ofPattern("EE d MMM y à H:mm"))
            calendarViewModel.updateRendezvousInput(rendezVousEstimationInput)
        }

        doAfterTextChanged { updateInput(it.toString()) }
    }

    private fun syncAddress(view: View, rendezVousEstimationInput: RendezVousEstimationInput) =
        with(view.findViewById<AppCompatAutoCompleteTextView>(R.id.address)) {
            val adapter =
                GeoLocationListAdapter(context, android.R.layout.simple_dropdown_item_1line)
            setAdapter(adapter)
            autoCompleteAddresses(view, adapter, this, rendezVousEstimationInput)
            searchForAddress(this)
        }

    private fun autoCompleteAddresses(
        view: View,
        adapter: GeoLocationListAdapter,
        autoCompleteTextView: AppCompatAutoCompleteTextView,
        rendezVousEstimationInput: RendezVousEstimationInput
    ) {
        val progressBar = view.findViewById<ProgressBar>(R.id.loader_address)
        calendarViewModel.addressesPropositions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> progressBar.visibility = View.VISIBLE
                is Failure -> {
                    Snackbar.make(
                        view.rootView.findViewById(R.id.content),
                        getString(R.string.generic_error),
                        Snackbar.LENGTH_SHORT
                    ).error(requireContext())
                    progressBar.visibility = View.GONE
                }
                is Success<List<FullAddress>> -> {
                    progressBar.visibility = View.GONE
                    val propositions = state.result
                    adapter.proposeNewAddresses(propositions)
                    autoCompleteTextView.onItemClickListener =
                        AdapterView.OnItemClickListener { parent, _, position, _ ->
                            val fullAddress = propositions[position]
                            rendezVousEstimationInput.address = fullAddress
                            calendarViewModel.updateRendezvousInput(rendezVousEstimationInput)
                        }
                }
            }
        }
    }

    private fun searchForAddress(autoCompleteTextView: AppCompatAutoCompleteTextView) {
        with(autoCompleteTextView) {
            val search =
                debounce<String>(delayMillis = 1000L, scope = lifecycleScope) { partialAddress ->
                    calendarViewModel.findAddress(partialAddress)
                }
            doAfterTextChanged {
                if (it != null && it.length >= THRESHOLD) {
                    search(it.toString())
                }
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
