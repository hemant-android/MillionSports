package com.forthpro.millionsport.ui.details.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.PredictionDetailResponse


class PredictionBoxAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PredictionBoxAdapter.ViewHolder>() {
    private val items: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray.Label1Array> =
        arrayListOf()
    lateinit var onclick: onClickListner

    interface onClickListner {
        fun clickItem(languageId: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray.Label1Array>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
            holder.box1!!.visibility = View.VISIBLE
            holder.view!!.visibility = View.VISIBLE
            holder.box1!!.text = "" + items[position].label_value
        } else {
            holder.box1!!.visibility = View.GONE
            holder.view!!.visibility = View.GONE
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_prediction_item_inner_box, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val view: View? = view.findViewById(R.id.view)
    }
}

