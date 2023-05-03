package com.forthpro.millionsport.ui.details.adapter.one

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.PredictionDetailResponse

class SoccerPredictionOneAdapter(private val mContext: Context, private val position: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray> = arrayListOf()
    private var onclick: onClickListner?= null
    private var selectedItemPos = 0

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

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder2 -> {
                var holder = holder as ViewHolder2
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder3 -> {
                var holder = holder as ViewHolder3
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder4 -> {
                var holder = holder as ViewHolder4
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder9 -> {
                var holder = holder as ViewHolder9
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            is ViewHolder13 -> {
                var holder = holder as ViewHolder13
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
            else -> {
                var holder = holder as ViewHolder0
                holder.tvLeagueName!!.text = items[position].label_name

                var adapter = SoccerPredictionOneInnerAdapter(mContext, this.position)
                holder.rvInner!!.adapter = adapter
                adapter.setData(items[position].label1_array)
            }
        }


        holder.itemView!!.setOnClickListener {
//            onclick!!.clickHeader(position)
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
            2 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_two, parent, false);
                viewHolder = ViewHolder2(view)
            }
            4 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four, parent, false);
                viewHolder = ViewHolder4(view)
            }
            3, 5, 6, 7, 8,21 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_three, parent, false);
                viewHolder = ViewHolder3(view)
            }
            9, 10, 11, 12,15,16,17,18,19,20 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_nine, parent, false);
                viewHolder = ViewHolder9(view)
            }
            13,14 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_thirteen, parent, false);
                viewHolder = ViewHolder13(view)
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

    class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }

    class ViewHolder3(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }

    class ViewHolder4(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }

    class ViewHolder9(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }

    class ViewHolder13(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val rvInner: RecyclerView? = view.findViewById(R.id.rvInner)
    }
}

