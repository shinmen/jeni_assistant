package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarFragment
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class ReminderFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var dateTimePickerViewModel: DateTimePickerViewModel
    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reminder_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTimePickerViewModel = ViewModelProvider(this, viewModelFactory).get(
            DateTimePickerViewModel::class.java)
        reminderViewModel = ViewModelProvider(this, viewModelFactory).get(ReminderViewModel::class.java)

        with(view) {
            val reminderObject = findViewById<EditText>(R.id.reminder_object)

            findViewById<TextView>(R.id.datetime_picker).run {
                setOnClickListener {
                    DatePickerDialogFragment.newInstance().show(parentFragmentManager, TAG)
                }
                dateTimePickerViewModel.dateTimePicked.observe(viewLifecycleOwner) {
                    text = it.format(DateTimeFormatter.ofPattern("EE d MMM y à H:mm"))
                }
            }
            findViewById<Button>(R.id.save_button).setOnClickListener {
                reminderViewModel.schedule(
                    Rappel(UUID.randomUUID(), LocalDateTime.now(), reminderObject.text.toString())
                )
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.calendar_container, CalendarFragment.newInstance(), CalendarFragment.TAG)
                    .commit()
            }
        }
    }

    companion object {
        const val TAG = "reminderFragment"

        @JvmStatic
        fun newInstance() = ReminderFragment()
    }
}