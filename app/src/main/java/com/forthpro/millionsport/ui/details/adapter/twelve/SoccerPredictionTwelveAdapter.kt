package com.forthpro.millionsport.ui.details.adapter.twelve

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.PredictionDetailResponse

class SoccerPredictionTwelveAdapter(private val mContext: Context, private val position: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray> = arrayListOf()
    private var onclick: onClickListner? = null

    interface onClickListner {
        fun clickHeader(position: Int)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ViewHolder0 -> {
                var holder = holder as ViewHolder0
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionTwelveInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder1 -> {
                var holder = holder as ViewHolder1
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionTwelveInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder2 -> {
                var holder = holder as ViewHolder2
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionTwelveInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            else -> {
                var holder = holder as ViewHolder0
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionTwelveInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view: View?
        var viewHolder: RecyclerView.ViewHolder?

        when (viewType) {
            0 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_zero, parent, false);
                viewHolder = ViewHolder0(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_nine, parent, false);
                viewHolder = ViewHolder1(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_zero, parent, false);
                viewHolder = ViewHolder2(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_zero, parent, false);
                viewHolder = ViewHolder0(view)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return this.position
    }

    class ViewHolder0(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }

    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }

    class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }
}

