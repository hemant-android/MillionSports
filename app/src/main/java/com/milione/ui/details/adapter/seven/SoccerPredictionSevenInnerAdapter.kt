package com.milione.ui.details.adapter.seven

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.milionesports.de.R
import com.milione.model.response.PredictionDetailResponse


class SoccerPredictionSevenInnerAdapter(private val mContext: Context, private val position: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray.Label1Array> =
        arrayListOf()
    lateinit var onclick: onClickListner
    private var selectedItemPos = 0

    interface onClickListner {
        fun clickHeader(position: Int)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<PredictionDetailResponse.PredictionTab.LabelArray.Label1Array>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder0) {
            var holder = holder as ViewHolder0
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.visibility = View.VISIBLE
                holder.view!!.visibility = View.VISIBLE
                holder.box1!!.text = "" + items[position].label_value
            } else {
                holder.box1!!.visibility = View.GONE
                holder.view!!.visibility = View.GONE
            }
        } else if (holder is ViewHolder1) {
            var holder = holder as ViewHolder1
            holder.box1!!.text = "" + items[position].label_value1
            holder.box2!!.text = "" + items[position].label_value2
        } else if (holder is ViewHolder2) {
            var holder = holder as ViewHolder2
            holder.box1Key!!.text = "" + items[position].label_key
            holder.box1!!.text = "" + items[position].label_value
        } else if (holder is ViewHolder3) {
            var holder = holder as ViewHolder3
            holder.box1!!.text = "" + items[position].label_key
            holder.box2!!.text = "" + items[position].label_key_up
            holder.box3!!.text = "" + items[position].label_key_down
        } else if (holder is ViewHolder5) {
            var holder = holder as ViewHolder5
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1Value!!.visibility = View.VISIBLE
                holder.box1Value!!.text = "" + items[position].label_value
            } else {
                holder.box1Value!!.visibility = View.GONE
            }
            if (items[position].label_value2 != null && !TextUtils.isEmpty(items[position].label_value2)) {
                holder.box2Value!!.visibility = View.VISIBLE
                holder.box2Value!!.text = "" + items[position].label_value2
            } else {
                holder.box2Value!!.visibility = View.GONE
            }

            if (items[position].label_key1 != null && !TextUtils.isEmpty(items[position].label_key1)) {
                holder.box1Key!!.visibility = View.VISIBLE
                holder.box1Key!!.text = "" + items[position].label_key1
            } else {
                holder.box1Key!!.visibility = View.GONE
            }
            if (items[position].label_key2 != null && !TextUtils.isEmpty(items[position].label_key2)) {
                holder.box2Key!!.visibility = View.VISIBLE
                holder.box2Key!!.text = "" + items[position].label_key2
            } else {
                holder.box2Key!!.visibility = View.GONE
            }
        } else if (holder is ViewHolder7) {
            var holder = holder as ViewHolder7
            holder.box1Key!!.text = "" + items[position].label_key
            holder.box1!!.text = "" + items[position].label_value
        } else if (holder is ViewHolder8) {
            var holder = holder as ViewHolder8
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_value
                holder.box3!!.text = "" + items[position].label_key1
                holder.box4!!.text = "" + items[position].label_value1

            }
        } else {
            var holder = holder as ViewHolder0
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.visibility = View.VISIBLE
                holder.view!!.visibility = View.VISIBLE
                holder.box1!!.text = "" + items[position].label_value
            } else {
                holder.box1!!.visibility = View.GONE
                holder.view!!.visibility = View.GONE
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
                    .inflate(R.layout.row_prediction_tab_zero_inner, parent, false);
                viewHolder = ViewHolder0(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_nine_inner, parent, false);
                viewHolder = ViewHolder1(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_one_inner, parent, false);
                viewHolder = ViewHolder2(view)
            }
            3, 4 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_two_inner, parent, false);
                viewHolder = ViewHolder3(view)
            }
            5, 6 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four_inner_sport_10, parent, false);
                viewHolder = ViewHolder5(view)
            }
            7 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_three_inner, parent, false);
                viewHolder = ViewHolder7(view)
            }
            8 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_ten_inner, parent, false);
                viewHolder = ViewHolder8(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_zero_inner, parent, false);
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
        val box1: TextView? = view.findViewById(R.id.box1)
        val view: View? = view.findViewById(R.id.view)
    }

    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
    }

    class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box1: TextView? = view.findViewById(R.id.box1)
        val view: View? = view.findViewById(R.id.view)
    }

    class ViewHolder3(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
    }

    class ViewHolder5(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box2Key: TextView? = view.findViewById(R.id.box2Key)
        val box1Value: TextView? = view.findViewById(R.id.box1Value)
        val box2Value: TextView? = view.findViewById(R.id.box2Value)
    }

    class ViewHolder7(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box1: TextView? = view.findViewById(R.id.box1)
        val view: View? = view.findViewById(R.id.view)
    }

    class ViewHolder8(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
        val box4: TextView? = view.findViewById(R.id.box4)
    }

}

