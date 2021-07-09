package fr.julocorp.jenisassistant.ui.calendar.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.infrastructure.di.ViewModelFactory
import fr.julocorp.jenisassistant.infrastructure.error
import fr.julocorp.jenisassistant.ui.calendar.CalendarActionFragment
import fr.julocorp.jenisassistant.ui.calendar.OnCalendarActionListener
import fr.julocorp.jenisassistant.ui.calendar.list.adapter.*
import fr.julocorp.jenisassistant.ui.calendar.schedule.CalendarEstimationFragment
import fr.julocorp.jenisassistant.ui.calendar.schedule.RappelFragment
import java.util.*
import javax.inject.Inject

class CalendarFragment : Fragment(), OnCalendarActionListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var calendarViewModel: CalendarViewModel
    private val rappelDeleteListener = { adapter: RendezVousListAdapter, position: Int, id: UUID ->
        adapter.remove(position)
        calendarViewModel.markRappelAsDone(id)
    }

    private val rendezvousEstimationDeleteListener = { adapter: RendezVousListAdapter, position: Int, id: UUID ->
        adapter.remove(position)
        calendarViewModel.markRendezvousEstimationAsDone(id)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calendar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarViewModel =
            ViewModelProvider(this, viewModelFactory).get(CalendarViewModel::class.java)

        calendarViewModel.fetchCalendarRow()

        val action = view.findViewById<FloatingActionButton>(R.id.add_calendar_action)
        val loaderView = view.findViewById<ProgressBar>(R.id.loader)
        val rendezVousRecyclerView = view.findViewById<RecyclerView>(R.id.rendezvous_list)

        action.setOnClickListener {
            val calendarActionFragment = CalendarActionFragment.newInstance(this)
            calendarActionFragment.show(parentFragmentManager, CalendarActionFragment.FRAGMENT_TAG)
        }

        with(rendezVousRecyclerView) {
            layoutManager = LinearLayoutManager(activity)
            val adapters = mapOf(
                DayRow.VIEW_TYPE to DayAdapter(),
                RappelRow.VIEW_TYPE to RappelAdapter(rappelDeleteListener),
                VisiteRow.VIEW_TYPE to VisiteRendezVousAdapter(),
                EstimationRow.VIEW_TYPE to EstimationRendezVousAdapter(rendezvousEstimationDeleteListener),
                SeparatorRow.VIEW_TYPE to SeparatorVousAdapter()
            )
            val rendezVousListAdapter = RendezVousListAdapter(adapters)
            adapter = rendezVousListAdapter

            calendarViewModel.calendarRows.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is Loading -> loaderView.visibility = View.VISIBLE
                    is Failure -> {
                        loaderView.visibility = View.GONE
                        Snackbar.make(
                            view.rootView.findViewById(R.id.content),
                            state.error.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .error(requireContext())
                    }
                    is Success<List<CalendarRow>> -> {
                        loaderView.visibility = View.GONE
                        val rows = state.result
                        rendezVousListAdapter.updateRows(rows)
                        val todayPosition = rows.indexOfFirst { it is DayRow && it.isToday }
                        (layoutManager as LinearLayoutManager).scrollToPosition(todayPosition)
                    }
                }
            }
        }
    }

    override fun selectCalendarAction(indexOfSelection: Int) {
        val actions = listOf(
            { CalendarEstimationFragment.newInstance() },
            { CalendarEstimationFragment.newInstance() },
            { RappelFragment.newInstance() }
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

        @JvmStatic
        fun newInstance() = CalendarFragment()
    }
}
