package com.milione.ui.details.adapter.nine

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.milionesports.de.R
import com.milione.model.response.PredictionDetailResponse


class SoccerPredictionNineInnerAdapter(
    private val mContext: Context,
    private val position: Int,
    private val sets: String?,
) :
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
            holder.box1!!.text = "" + items[position].label_value1
            holder.box2!!.text = "" + items[position].label_value2
        } else if (holder is ViewHolder1) {
            var holder = holder as ViewHolder1

            if (sets != null && !TextUtils.isEmpty(sets) && sets == "2") {
                holder.llKey!!.weightSum = 2F
                holder.llValue!!.weightSum = 2F

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
                if (items[position].label_value4 != null && !TextUtils.isEmpty(items[position].label_value4)) {
                    holder.box4Value!!.visibility = View.VISIBLE
                    holder.box4Value!!.text = "" + items[position].label_value4
                } else {
                    holder.box4Value!!.visibility = View.GONE
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
                if (items[position].label_key4 != null && !TextUtils.isEmpty(items[position].label_key4)) {
                    holder.box4Key!!.visibility = View.VISIBLE
                    holder.box4Key!!.text = "" + items[position].label_key4
                } else {
                    holder.box4Key!!.visibility = View.GONE
                }
                holder.box5Key!!.visibility = View.GONE
                holder.box6Key!!.visibility = View.GONE
                holder.box5Value!!.visibility = View.GONE
                holder.box6Value!!.visibility = View.GONE

            } else {
                holder.llKey!!.weightSum = 3F
                holder.llValue!!.weightSum = 3F

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
                if (items[position].label_value4 != null && !TextUtils.isEmpty(items[position].label_value4)) {
                    holder.box4Value!!.visibility = View.VISIBLE
                    holder.box4Value!!.text = "" + items[position].label_value4
                } else {
                    holder.box4Value!!.visibility = View.GONE
                }
                if (items[position].label_value5 != null && !TextUtils.isEmpty(items[position].label_value5)) {
                    holder.box5Value!!.visibility = View.VISIBLE
                    holder.box5Value!!.text = "" + items[position].label_value5
                } else {
                    holder.box5Value!!.visibility = View.GONE
                }
                if (items[position].label_value6 != null && !TextUtils.isEmpty(items[position].label_value6)) {
                    holder.box6Value!!.visibility = View.VISIBLE
                    holder.box6Value!!.text = "" + items[position].label_value6
                } else {
                    holder.box6Value!!.visibility = View.GONE
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
                if (items[position].label_key4 != null && !TextUtils.isEmpty(items[position].label_key4)) {
                    holder.box4Key!!.visibility = View.VISIBLE
                    holder.box4Key!!.text = "" + items[position].label_key4
                } else {
                    holder.box4Key!!.visibility = View.GONE
                }
                if (items[position].label_key5 != null && !TextUtils.isEmpty(items[position].label_key5)) {
                    holder.box5Key!!.visibility = View.VISIBLE
                    holder.box5Key!!.text = "" + items[position].label_key5
                } else {
                    holder.box5Key!!.visibility = View.GONE
                }
                if (items[position].label_key6 != null && !TextUtils.isEmpty(items[position].label_key6)) {
                    holder.box6Key!!.visibility = View.VISIBLE
                    holder.box6Key!!.text = "" + items[position].label_key6
                } else {
                    holder.box6Key!!.visibility = View.GONE
                }
            }
        } else if (holder is ViewHolder2) {
            var holder = holder as ViewHolder2
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1Key!!.text = "" + items[position].label_key
                holder.box2Key!!.text = "" + items[position].label_key1
                holder.box1Value!!.text = "" + items[position].label_value
                holder.box2Value!!.text = "" + items[position].label_value1
            }
        } else if (holder is ViewHolder3) {
            var holder = holder as ViewHolder3
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_key_up
                holder.box3!!.text = "" + items[position].label_key_down
            }
        } else if (holder is ViewHolder6) {
            var holder = holder as ViewHolder6
            holder.box1Key!!.text = "" + items[position].label_key1
            holder.box2Key!!.text = "" + items[position].label_key2
            holder.box1Value!!.text = "" + items[position].label_value
            holder.box2Value!!.text = "" + items[position].label_value2
        } else if (holder is ViewHolder9) {
            var holder = holder as ViewHolder9
            if (items[position].label_value != null && !TextUtils.isEmpty(items[position].label_value)) {
                holder.box1!!.text = "" + items[position].label_key
                holder.box2!!.text = "" + items[position].label_value
                holder.box3!!.text = "" + items[position].label_key1
                holder.box4!!.text = "" + items[position].label_value1
            }
        } else {
            var holder = holder as ViewHolder0
            holder.box1!!.text = "" + items[position].label_key
            holder.box2!!.text = "" + items[position].label_value
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view: View?
        var viewHolder: RecyclerView.ViewHolder?

        when (viewType) {
            0 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_nine_inner, parent, false);
                viewHolder = ViewHolder0(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four_inner_sport_9, parent, false);
                viewHolder = ViewHolder1(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four_inner_sport_10, parent, false);
                viewHolder = ViewHolder2(view)
            }
            3, 4, 5 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_two_inner, parent, false);
                viewHolder = ViewHolder3(view)
            }
            6, 7, 8 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_four_inner_sport_10, parent, false);
                viewHolder = ViewHolder6(view)
            }
            9 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_prediction_tab_ten_inner, parent, false);
                viewHolder = ViewHolder9(view)
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
        val box2: TextView? = view.findViewById(R.id.box2)
    }

    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box2Key: TextView? = view.findViewById(R.id.box2Key)
        val box3Key: TextView? = view.findViewById(R.id.box3Key)
        val box4Key: TextView? = view.findViewById(R.id.box4Key)
        val box5Key: TextView? = view.findViewById(R.id.box5Key)
        val box6Key: TextView? = view.findViewById(R.id.box6Key)
        val box1Value: TextView? = view.findViewById(R.id.box1Value)
        val box2Value: TextView? = view.findViewById(R.id.box2Value)
        val box3Value: TextView? = view.findViewById(R.id.box3Value)
        val box4Value: TextView? = view.findViewById(R.id.box4Value)
        val box5Value: TextView? = view.findViewById(R.id.box5Value)
        val box6Value: TextView? = view.findViewById(R.id.box6Value)
        val llKey: LinearLayout? = view.findViewById(R.id.llKey)
        val llValue: LinearLayout? = view.findViewById(R.id.llValue)
    }

    class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box2Key: TextView? = view.findViewById(R.id.box2Key)
        val box1Value: TextView? = view.findViewById(R.id.box1Value)
        val box2Value: TextView? = view.findViewById(R.id.box2Value)
    }

    class ViewHolder3(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
    }

    class ViewHolder6(view: View) : RecyclerView.ViewHolder(view) {
        val box1Key: TextView? = view.findViewById(R.id.box1Key)
        val box2Key: TextView? = view.findViewById(R.id.box2Key)
        val box1Value: TextView? = view.findViewById(R.id.box1Value)
        val box2Value: TextView? = view.findViewById(R.id.box2Value)
    }

    class ViewHolder9(view: View) : RecyclerView.ViewHolder(view) {
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
        val box4: TextView? = view.findViewById(R.id.box4)
    }

}

