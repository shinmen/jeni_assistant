package fr.julocorp.jenisassistant.ui.common.datetimePicker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import java.time.LocalTime
import java.util.*
import javax.inject.Inject


class TimePickerDialogFragment : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DateTimePickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
            DateTimePickerViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = TimePickerDialog(
        requireActivity(),
        this,
        LocalTime.now().hour,
        LocalTime.now().minute,
        true
    )

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.setTime(LocalTime.of(hourOfDay, minute))
        dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimePickerDialogFragment()
    }
}