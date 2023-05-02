package com.forthpro.millionsport.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.PredictionDetailResponse


class SoccerPredictionAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<PredictionDetailResponse.PredictionTab> = arrayListOf()
    lateinit var onclick: onClickListner
    private var selectedItemPos = 0

    interface onClickListner {
        fun clickHeader(position: Int)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<PredictionDetailResponse.PredictionTab>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var holder = holder as ViewHolder0
        if (items[position].isSelect != null && items[position].isSelect) {
            holder.llTabOne!!.visibility = View.VISIBLE
            holder.imgNext!!.rotation = 270F
        } else {
            holder.llTabOne!!.visibility = View.GONE
            holder.imgNext!!.rotation = 90F
        }
        holder.tvPredictionName!!.text = items[position].label_name
        var labelArray = items[position].label_array
        var adapter = SoccerPredictionOneAdapter(mContext, position)
        holder.rvBox!!.adapter = adapter
        adapter.setData(labelArray)

        holder.itemView!!.setOnClickListener {
            onclick.clickHeader(position)
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view: View?
        var viewHolder: RecyclerView.ViewHolder?

        /*when (viewType) {
            0 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_soccer_prediction_title_one, parent, false);
                viewHolder = ViewHolder0(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_soccer_prediction_title_one, parent, false);
                viewHolder = ViewHolder1(view)
            }
        }*/
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_soccer_prediction_title_one, parent, false);
        viewHolder = ViewHolder0(view)
        return viewHolder!!

        /*if (viewType == 0) {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_soccer_prediction_title, parent, false)
            )
        }*/
        /*return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_soccer_prediction_title, parent, false)
        )*/
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder0(view: View) : RecyclerView.ViewHolder(view) {
        val tvPredictionName: TextView? = view.findViewById(R.id.tvPredictionName)
        val imgNext: ImageView? = view.findViewById(R.id.imgNext)
        val llTabOne: LinearLayout? = view.findViewById(R.id.llTabOne)
        val rvBox: RecyclerView? = view.findViewById(R.id.rvBox)
    }

    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val tvPredictionName: TextView? = view.findViewById(R.id.tvPredictionName)
        val imgNext: ImageView? = view.findViewById(R.id.imgNext)
        val llTabOne: LinearLayout? = view.findViewById(R.id.llTabOne)
        val rvBox: RecyclerView? = view.findViewById(R.id.rvBox)
    }

    private fun selectClickedAndUnselectPrevious(holder: ViewHolder1) {
        items[selectedItemPos].isSelect = false
        items[holder.adapterPosition].isSelect = true
        notifyItemChanged(selectedItemPos)
        notifyItemChanged(holder.adapterPosition)
        selectedItemPos = holder.adapterPosition
    }
}

