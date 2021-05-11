package fr.julocorp.jenisassistant.ui.calendar.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection.inject
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.calendar.ReminderViewModel
import fr.julocorp.jenisassistant.infrastructure.common.DateTimePickerViewModel
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DatePickerDialogFragment
import java.text.SimpleDateFormat
import javax.inject.Inject

class ReminderFragment : Fragment() {
    private lateinit var viewModel: ReminderViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var dateTimePickerViewModel: DateTimePickerViewModel

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
        dateTimePickerViewModel = ViewModelProvider(this, viewModelFactory).get(DateTimePickerViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(ReminderViewModel::class.java)

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
        const val TAG = "reminderFragment"

        @JvmStatic
        fun newInstance() = ReminderFragment()
    }
}