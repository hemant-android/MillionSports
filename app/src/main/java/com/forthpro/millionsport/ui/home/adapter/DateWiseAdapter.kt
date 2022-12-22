package com.forthpro.millionsport.ui.home.adapter

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.DashboardResponse
import com.forthpro.millionsport.util.Utils

class DateWiseAdapter(val mContext: Context) :
    RecyclerView.Adapter<DateWiseAdapter.ViewHolder>() {
    private val items: ArrayList<DashboardResponse.DateArray>? = arrayListOf()
    private var chooseDate = ""

    private var selectedItemPos = 0
    lateinit var onclick: onClickListner

    interface onClickListner {
        fun clickDateItem(dateValue: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<DashboardResponse.DateArray>, chooseDate: String?) {
        this.chooseDate = chooseDate.toString()
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (!TextUtils.isEmpty(chooseDate) && chooseDate == items!![position].date_value)
        {
            holder.tvWeekName!!.setTextColor(ContextCompat.getColor(mContext, R.color.red))
            holder.tvDateName!!.setTextColor(ContextCompat.getColor(mContext, R.color.red))
        }else{
            holder.tvWeekName!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            holder.tvDateName!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        }

        holder.tvWeekName!!.text = Utils.getWeekName(items!![position].date_value)
        holder.tvDateName!!.text = Utils.getDayMonth(items!![position].date_value)

        holder.itemView.setOnClickListener {
            onclick.clickDateItem(items!![position].date_value)
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_date_wise_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWeekName: TextView? = view.findViewById(R.id.tvWeekName)
        val tvDateName: TextView? = view.findViewById(R.id.tvDateName)
    }

    private fun selectClickedAndUnselectPrevious(holder: ViewHolder) {
        items!![selectedItemPos].isSelect = false
        items!![holder.adapterPosition].isSelect = true
        notifyItemChanged(selectedItemPos)
        notifyItemChanged(holder.adapterPosition)
        selectedItemPos = holder.adapterPosition
    }

}

