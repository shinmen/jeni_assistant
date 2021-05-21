package fr.julocorp.jenisassistant.ui.calendar.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.ui.calendar.CalendarViewModel
import fr.julocorp.jenisassistant.ui.calendar.CalendarActionFragment
import fr.julocorp.jenisassistant.ui.calendar.OnCalendarActionListener
import fr.julocorp.jenisassistant.ui.calendar.list.adapter.*
import fr.julocorp.jenisassistant.ui.calendar.schedule.CalendarEstimationFragment
import fr.julocorp.jenisassistant.ui.calendar.schedule.ReminderFragment

class CalendarFragment : Fragment(), OnCalendarActionListener {

    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)

        val action = root.findViewById<FloatingActionButton>(R.id.add_calendar_action)
        action.setOnClickListener {
            val calendarActionFragment = CalendarActionFragment.newInstance(this)
            calendarActionFragment.show(parentFragmentManager, CalendarActionFragment.FRAGMENT_TAG)
        }

        val rendezVousRecyclerView = root.findViewById<RecyclerView>(R.id.rendezvous_list)
        with(rendezVousRecyclerView) {
            layoutManager = LinearLayoutManager(activity)
            val adapters = mapOf(
                DayRow.VIEW_TYPE to DayAdapter(),
                TaskRow.VIEW_TYPE to TaskAdapter(),
                VisiteRow.VIEW_TYPE to VisiteAdapter(),
                EstimationRow.VIEW_TYPE to EstimationAdapter(),
                SeparatorRow.VIEW_TYPE to SeparatorAdapter()
            )
            adapter = RendezVousListAdapter(adapters)
        }
        calendarViewModel.text.observe(viewLifecycleOwner) {

        }

        return root
    }

    override fun selectCalendarAction(indexOfSelection: Int) {
        val actions = listOf(
            { CalendarEstimationFragment.newInstance() },
            { CalendarEstimationFragment.newInstance() },
            { ReminderFragment.newInstance() }
        )
        val action = actions.getOrNull(indexOfSelection)
        action?.run {
            parentFragmentManager.beginTransaction()
                .replace(R.id.calendar_container, this.invoke(), SCHEDULER_TAG)
                .addToBackStack(SCHEDULER_TAG)
                .commit()
        }
    }

    companion object {
        private const val SCHEDULER_TAG = "schedulerTag"
    }
}