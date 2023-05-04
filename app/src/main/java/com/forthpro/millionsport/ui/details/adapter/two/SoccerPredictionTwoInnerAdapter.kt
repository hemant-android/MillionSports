package com.forthpro.millionsport.ui.details.adapter.two

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.PredictionDetailResponse


class SoccerPredictionTwoInnerAdapter(private val mContext: Context, private val position: Int) :
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
                holder.box1Key!!.text = "" + items[position].label_key
                holder.box1!!.text = "" + items[position].label_value
            }
        } else if (holder is ViewHolder4) {
            var holder = holder as ViewHolder4
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
        } else if (holder is ViewHolder4Any) {
            var holder = holder as ViewHolder4Any
            if (items[position].label_value1 != null && !TextUtils.isEmpty(items[position].label_value1)) {
                holder.box1Value!!.visibility = View.VISIBLE
                holder.box1Value!!.text = "" + items[position].label_value1
            } else {
                holder.box1Value!!.visibility = View.GONE
            }
            if (items[position].label_value2 != null && !TextUtils.isEmpty(items[position].label_value2)) {
                holder.box2Value!!.visibility = View.VISIBLE
                holder.box2Value!!.text = "" + items[position].label_value2
            } else {
                holder.box2Value!!.visibility = View.GONE
            }
            if (items[position].label_value3 != null && !TextUtils.isEmpty(items[position].label_value3)) {
                holder.box3Value!!.visibility = View.VISIBLE
                holder.box3Value!!.text = "" + items[position].label_value3
            } else {
                holder.box3Value!!.visibility = View.GONE
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
            if (items[position].label_key3 != null && !TextUtils.isEmpty(items[position].label_key3)) {
                holder.box3Key!!.visibility = View.VISIBLE
                holder.box3Key!!.text = "" + items[position].label_key3
            } else {
                holder.box3Key!!.visibility = View.GONE
            }

            if (items[position].name != null && !TextUtils.isEmpty(items[position].name)) {
                holder.llAny!!.visibility = View.VISIBLE
                holder.llMain!!.visibility = View.GONE
                holder.boxAnyKey!!.text = "" + items[position].name
                holder.boxAnyValue!!.text = "" + items[position].label_key3
            } else {
                holder.llAny!!.visibility = View.GONE
                holder.llMain!!.visibility = View.VISIBLE
            }
        } else if (holder is ViewHolder8) {
            var holder = holder as ViewHolder8
            holder.box1!!.text = "" + items[position].label_key
            holder.box2!!.text = "" + items[position].label_value
        } else if (holder is ViewHolder9) {
            var holder = holder as ViewHolder9
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_value
            }
        } else if (holder is ViewHolder10) {
            var holder = holder as ViewHolder10
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_value
                holder.box3!!.text = "" + items[position].label_key1
                holder.box4!!.text = "" + items[position].label_value1

            }
        } else if (holder is ViewHolder11) {
            var holder = holder as ViewHolder11
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_value
                holder.box3!!.text = "" + items[position].label_key1
                holder.box4!!.text = "" + items[position].label_value1

            }
        } else if (holder is ViewHolder14) {
            var holder = holder as ViewHolder14
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_key_up
                holder.box3!!.text = "" + items[position].label_key_down
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
            3, 11, 12 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_two_inner, parent, false);
                viewHolder = ViewHolder3(view)
            }
            4 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four_inner_sport_10, parent, false);
                viewHolder = ViewHolder4(view)
            }
            5, 6 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four_inner, parent, false);
                viewHolder = ViewHolder4Any(view)
            }
            7, 13, 14 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_three_inner, parent, false);
                viewHolder = ViewHolder5(view)
            }
            8 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_nine_inner, parent, false);
                viewHolder = ViewHolder8(view)
            }
            9 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_ten_inner, parent, false);
                viewHolder = ViewHolder10(view)
            }
            10-> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_11_inner, parent, false);
                viewHolder = ViewHolder11(view)
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

    class ViewHolder8(view: View) : RecyclerView.ViewHolder(view) {
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
        val box1: TextView? = view.findViewById(R.id.box1)
        val view: View? = view.findViewById(R.id.view)
    }

    class ViewHolder4(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box2Key: TextView? = view.findViewById(R.id.box2Key)
        val box1Value: TextView? = view.findViewById(R.id.box1Value)
        val box2Value: TextView? = view.findViewById(R.id.box2Value)
    }

    class ViewHolder4Any(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box2Key: TextView? = view.findViewById(R.id.box2Key)
        val box3Key: TextView? = view.findViewById(R.id.box3Key)
        val box1Value: TextView? = view.findViewById(R.id.box1Value)
        val box2Value: TextView? = view.findViewById(R.id.box2Value)
        val box3Value: TextView? = view.findViewById(R.id.box3Value)
        val boxAnyKey: TextView? = view.findViewById(R.id.boxAnyKey)
        val boxAnyValue: TextView? = view.findViewById(R.id.boxAnyValue)
        val llMain: LinearLayout? = view.findViewById(R.id.llMain)
        val llAny: LinearLayout? = view.findViewById(R.id.llAny)
    }

    class ViewHolder9(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
    }

    class ViewHolder10(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
        val box4: TextView? = view.findViewById(R.id.box4)
    }

    class ViewHolder11(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
        val box4: TextView? = view.findViewById(R.id.box4)
    }

    class ViewHolder14(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
    }

}

