package fr.julocorp.jenisassistant.ui.calendar.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.infrastructure.ViewType
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RendezVousListAdapter(
    private val adapters: Map<ViewType, RendezVousAdapter>,
    private var calendarRows: List<CalendarRow> = listOf()
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    suspend fun updateRows(rows: List<CalendarRow>, coroutineContext: CoroutineContext) {
        val diffResult = withContext(coroutineContext) {
            val diffCallback = CalendarRowDiffCallback(calendarRows, rows)
            DiffUtil.calculateDiff(diffCallback)
        }

        calendarRows = rows
        diffResult.dispatchUpdatesTo(this)
    }

    fun remove(position: Int) {
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapters.getOrDefault(viewType, SeparatorVousAdapter()).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapters.getOrDefault(getItemViewType(position), SeparatorVousAdapter()).onBindViewHolder(
            holder,
            calendarRows[position],
            position,
            this
        )

    override fun getItemCount(): Int = calendarRows.size

    override fun getItemViewType(position: Int): Int = calendarRows[position].getViewType()

    interface RendezVousAdapter {
        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            calendarRow: CalendarRow,
            position: Int,
            adapter: RendezVousListAdapter
        )
    }
}