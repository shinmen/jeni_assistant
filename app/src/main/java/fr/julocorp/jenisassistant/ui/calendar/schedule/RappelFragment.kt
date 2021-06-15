package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.infrastructure.error
import fr.julocorp.jenisassistant.infrastructure.success
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarFragment
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class RappelFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var dateTimePickerViewModel: DateTimePickerViewModel
    private lateinit var rappelViewModel: RappelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rappel, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTimePickerViewModel = ViewModelProvider(this, viewModelFactory).get(
            DateTimePickerViewModel::class.java
        )
        rappelViewModel =
            ViewModelProvider(this, viewModelFactory).get(RappelViewModel::class.java)

        with(view) {
            val reminderObjectView = findViewById<EditText>(R.id.reminder_object)
            val dateTimeView = findViewById<TextView>(R.id.datetime_picker)
            val loaderView = findViewById<ProgressBar>(R.id.loader)

            dateTimeView.run {
                setOnClickListener {
                    DatePickerDialogFragment.newInstance().show(parentFragmentManager, TAG)
                }
                dateTimePickerViewModel.dateTimePicked.observe(viewLifecycleOwner) {
                    text =
                        it.format(DateTimeFormatter.ofPattern(context.getString(R.string.datetime_pattern_full)))
                }
            }
            findViewById<Button>(R.id.save_button).setOnClickListener {
                val rappelDatetime = LocalDateTime.parse(
                    dateTimeView.text,
                    DateTimeFormatter.ofPattern(context.getString(R.string.datetime_pattern_full))
                )
                rappelViewModel.schedule(
                    Rappel(UUID.randomUUID(), rappelDatetime, reminderObjectView.text.toString())
                )
            }

            rappelViewModel.scheduleRappelResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is Loading -> loaderView.visibility = View.VISIBLE
                    is Success -> {
                        loaderView.visibility = View.GONE
                        Snackbar.make(
                            this.rootView.findViewById(R.id.content),
                            getString(R.string.rappel_save),
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
                        Snackbar.make(
                            this.rootView.findViewById(R.id.content),
                            state.error.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .error(requireContext())
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "reminderFragment"

        @JvmStatic
        fun newInstance() = RappelFragment()
    }
}
