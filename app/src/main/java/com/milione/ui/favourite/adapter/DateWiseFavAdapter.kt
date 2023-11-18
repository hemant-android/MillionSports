package com.milione.ui.favourite.adapter

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.milionesports.de.R
import com.milione.model.response.FavouriteCommonResponse
import com.milione.util.Utils

class DateWiseFavAdapter(val mContext: Activity) :
    RecyclerView.Adapter<DateWiseFavAdapter.ViewHolder>() {
    private val items: ArrayList<FavouriteCommonResponse.DateArray>? = arrayListOf()
    private var chooseDate = ""

    private var selectedItemPos = 0
    lateinit var onclick: onClickListner
    private var width = 0

    interface onClickListner {
        fun clickDateItem(dateValue: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<FavouriteCommonResponse.DateArray>, chooseDate: String?) {
        this.chooseDate = chooseDate.toString()
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val displayMetrics = DisplayMetrics()
        mContext.windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels

        val mWidth = (width / 5)
        holder.itemView!!.layoutParams =
            LinearLayout.LayoutParams(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)

        if (items!![position].isSelect) {
            holder.tvWeekName!!.setTextColor(ContextCompat.getColor(mContext, R.color.red))
            holder.tvDateName!!.setTextColor(ContextCompat.getColor(mContext, R.color.red))
        }else{
            holder.tvWeekName!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            holder.tvDateName!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        }

        if (items!![position].date_value == Utils.getCurrentDate()) {
            holder.tvWeekName!!.text = items!![position].day_name
        } else {
            holder.tvWeekName!!.text = /*Utils.getWeekName(items!![position].date_value)*/items!![position].day_name
        }

        holder.tvDateName!!.text = items!![position].date_value1//Utils.convertTimeCurrentTimeZone(items!![position].date_value1,PreferenceHelper.timeFormat)

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

