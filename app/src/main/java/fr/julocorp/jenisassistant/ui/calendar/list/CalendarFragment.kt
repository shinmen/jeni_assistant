package fr.julocorp.jenisassistant.ui.calendar.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.ui.calendar.CalendarActionFragment
import fr.julocorp.jenisassistant.ui.calendar.OnCalendarActionListener
import fr.julocorp.jenisassistant.ui.calendar.list.adapter.*
import fr.julocorp.jenisassistant.ui.calendar.schedule.CalendarEstimationFragment
import fr.julocorp.jenisassistant.ui.calendar.schedule.ReminderFragment
import javax.inject.Inject

class CalendarFragment : Fragment(), OnCalendarActionListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var calendarViewModel: CalendarViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarViewModel =
            ViewModelProvider(this, viewModelFactory).get(CalendarViewModel::class.java)
        calendarViewModel.fetchCalendarRow()

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
                RappelRow.VIEW_TYPE to RappelAdapter(),
                VisiteRow.VIEW_TYPE to VisiteVousAdapter(),
                EstimationRow.VIEW_TYPE to EstimationVousAdapter(),
                SeparatorRow.VIEW_TYPE to SeparatorVousAdapter()
            )
            val rendezVousListAdapter = RendezVousListAdapter(adapters)
            adapter = rendezVousListAdapter
            calendarViewModel.calendarRows.observe(viewLifecycleOwner) { rows ->
                rendezVousListAdapter.updateRows(rows)
                val todayPosition = rows.indexOfFirst { it is DayRow && it.isToday }
                (layoutManager as LinearLayoutManager).scrollToPosition(todayPosition)
            }
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
                .replace(R.id.content, this.invoke(), SCHEDULER_TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        private const val SCHEDULER_TAG = "schedulerTag"
        const val TAG = "calendarFragment"

        fun newInstance() = CalendarFragment()
    }
}