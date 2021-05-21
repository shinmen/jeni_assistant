package fr.julocorp.jenisassistant.ui.common.datetimePicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import java.util.*
import javax.inject.Inject


class DatePickerDialogFragment : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = DatePickerDialog(
        requireActivity(),
        this,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.setDate(Calendar.getInstance().apply { set(year, month, dayOfMonth) })

        TimePickerDialogFragment.newInstance().show(parentFragmentManager, TAG)
    }

    companion object {
        private const val TAG = "datePickerDialog"

        @JvmStatic
        fun newInstance() =
            DatePickerDialogFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}