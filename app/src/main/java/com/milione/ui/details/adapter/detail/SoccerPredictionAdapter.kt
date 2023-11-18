package com.milione.ui.details.adapter.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.milionesports.de.R
import com.milione.model.response.PredictionDetailResponse
import com.milione.ui.details.adapter.eight.SoccerPredictionEightAdapter
import com.milione.ui.details.adapter.eleven.SoccerPredictionElevenAdapter
import com.milione.ui.details.adapter.five.SoccerPredictionFiveAdapter
import com.milione.ui.details.adapter.four.SoccerPredictionFourAdapter
import com.milione.ui.details.adapter.nine.SoccerPredictionNineAdapter
import com.milione.ui.details.adapter.one.SoccerPredictionOneAdapter
import com.milione.ui.details.adapter.seven.SoccerPredictionSevenAdapter
import com.milione.ui.details.adapter.six.SoccerPredictionSixAdapter
import com.milione.ui.details.adapter.ten.SoccerPredictionTenAdapter
import com.milione.ui.details.adapter.thirteen.SoccerPredictionThirteenAdapter
import com.milione.ui.details.adapter.three.SoccerPredictionThreeAdapter
import com.milione.ui.details.adapter.twelve.SoccerPredictionTwelveAdapter
import com.milione.ui.details.adapter.two.SoccerPredictionTwoAdapter


class SoccerPredictionAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<PredictionDetailResponse.PredictionTab> = arrayListOf()
    lateinit var onclick: onClickListner
    private var sportId: String? = ""
    private var sets: String? = ""

    interface onClickListner {
        fun clickHeader(position: Int)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(
        item: ArrayList<PredictionDetailResponse.PredictionTab>,
        mSportId: String,
        mSets: String?,
    ) {
        items!!.clear()
        items.addAll(item)
        sportId = mSportId
        sets = mSets
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

        when (sportId) {
            "1" -> {
                var adapter = SoccerPredictionOneAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "2" -> {
                var adapter = SoccerPredictionTwoAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "3" -> {
                var adapter = SoccerPredictionThreeAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "4" -> {
                var adapter = SoccerPredictionFourAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "5" -> {
                var adapter = SoccerPredictionFiveAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "6" -> {
                var adapter = SoccerPredictionSixAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "7" -> {
                var adapter = SoccerPredictionSevenAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "8" -> {
                var adapter = SoccerPredictionEightAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "9" -> {
                var adapter = SoccerPredictionNineAdapter(mContext, position,sets)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "10" -> {
                var adapter = SoccerPredictionTenAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }

            "11" -> {
                var adapter = SoccerPredictionElevenAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "12" -> {
                var adapter = SoccerPredictionTwelveAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            "13" -> {
                var adapter = SoccerPredictionThirteenAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
            else -> {
                var adapter = SoccerPredictionOneAdapter(mContext, position)
                holder.rvBox!!.adapter = adapter
                adapter.setData(labelArray)
            }
        }



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
}

