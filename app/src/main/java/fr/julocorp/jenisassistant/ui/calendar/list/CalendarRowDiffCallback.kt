package fr.julocorp.jenisassistant.ui.calendar.list

import androidx.recyclerview.widget.DiffUtil

class CalendarRowDiffCallback(private val oldList: List<CalendarRow>, private val newList: List<CalendarRow>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].getViewType() == newList[newItemPosition].getViewType() &&
                oldList[oldItemPosition].getId() == newList[newItemPosition].getId()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}