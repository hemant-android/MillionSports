package com.forthpro.millionsport.ui.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.NotificationResponse


class NotificationAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private val items: ArrayList<NotificationResponse.NotificationArray> = arrayListOf()
    lateinit var onclick: onClickListner
    private var selectedItemPos = 0

    interface onClickListner {
        fun clickItem(position: Int,notificationId: Int,isToggle: Int)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<NotificationResponse.NotificationArray>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items!![position].notification == 1) {
            holder.toggleNotification!!.setImageResource(R.drawable.ic_on)
        } else {
            holder.toggleNotification!!.setImageResource(R.drawable.ic_off)
        }

        holder.tvTitle!!.text = "" + items[position].label_value

        when (items[position].unique_key) {
            "push_notifications" -> {
                holder.imgNotification!!.setImageResource(R.drawable.ic_push_notification)
            }
            "notifications_sound" -> {
                holder.imgNotification!!.setImageResource(R.drawable.ic_sound_notification)
            }
            "notifications_vibration" -> {
                holder.imgNotification!!.setImageResource(R.drawable.ic_vibration)
            }
            "notifications_mute" -> {
                holder.imgNotification!!.setImageResource(R.drawable.ic_mute)
            }
        }

        holder.itemView!!.setOnClickListener {
            selectClickedAndUnselectPrevious(holder)
            onclick.clickItem(holder.adapterPosition,items[position].id,items[position].notification)
        }

    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_notification_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val imgNotification: ImageView? = view.findViewById(R.id.imgNotification)
        val tvTitle: TextView? = view.findViewById(R.id.tvTitle)
        val toggleNotification: ImageView? = view.findViewById(R.id.toggleNotification)
    }

    private fun selectClickedAndUnselectPrevious(holder: ViewHolder) {
        items[selectedItemPos].isSelect = false
        items[holder.adapterPosition].isSelect = true
        notifyItemChanged(selectedItemPos)
        notifyItemChanged(holder.adapterPosition)
        selectedItemPos = holder.adapterPosition
    }
}

