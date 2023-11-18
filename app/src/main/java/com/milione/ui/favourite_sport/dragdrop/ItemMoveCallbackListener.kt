package com.milione.ui.favourite_sport.dragdrop

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.milione.model.response.FavouriteSportResponse
import com.milione.ui.favourite_sport.adapter.FavSportsDragDropAdapter

class ItemMoveCallbackListener(val adapter: FavSportsDragDropAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }
    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }
    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is FavSportsDragDropAdapter.ViewHolder) {
                adapter.onRowSelected(viewHolder)
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.requestLayout()
        adapter.onUpdateArr(adapter.items)

        val itemViewHolder = viewHolder as FavSportsDragDropAdapter.ViewHolder
//        itemViewHolder.onItemMovedCallback(adapter.items)


        /*if (viewHolder is FavSportsDragDropAdapter.ViewHolder) {
            adapter.onRowClear(viewHolder)
        }*/
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
    interface Listener {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowSelected(itemViewHolder: FavSportsDragDropAdapter.ViewHolder)
        fun onRowClear(itemViewHolder: FavSportsDragDropAdapter.ViewHolder)
        fun onUpdateArr(items: ArrayList<FavouriteSportResponse.SportsArray>?)
    }
}