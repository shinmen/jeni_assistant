package fr.julocorp.jenisassistant.ui.calendar

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import fr.julocorp.jenisassistant.R

class CalendarActionFragment(private val onCalendarActionListener: OnCalendarActionListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = activity?.let {
        val builder = AlertDialog.Builder(it)
        builder.setTitle(R.string.which_calendar_event)
            .setItems(R.array.calendar_actions) { _, which ->
                onCalendarActionListener.selectCalendarAction(which)
            }
        builder.create()
    } ?: throw IllegalStateException("Activity cannot be null")


    companion object {
        const val FRAGMENT_TAG = "CalendarActionFragment"

        fun newInstance(onCalendarActionListener: OnCalendarActionListener) =
            CalendarActionFragment(onCalendarActionListener)
    }
}