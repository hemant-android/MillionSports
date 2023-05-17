package com.forthpro.millionsport.ui.favourite.fragment.match.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.MatchResponse
import com.google.android.material.imageview.ShapeableImageView


class MatchAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    private val items: ArrayList<MatchResponse.Matche> = arrayListOf()
    lateinit var onclick: onClickListner

    interface onClickListner {
        fun clickItem(languageId: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<MatchResponse.Matche>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items!![position].country.country_logo != null && items!![position].country.country_logo.isNotEmpty()) {
            Glide.with(mContext).load(BuildConfig.SERVER_URL + items!![position].country.country_logo)
                .centerCrop()
                .into(holder.imgFlag!!)
        }

        holder.tvCountryName!!.text = "" + items[position].country.name
        holder.tvTime!!.text = "" + items[position].prediction_time


        if (!TextUtils.isEmpty(items[position].Home)) {
            holder.tvLeagueHome!!.visibility = View.VISIBLE
            holder.tvLeagueHome!!.text = "" + items[position].Home
        } else {
            holder.tvLeagueHome!!.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(items[position].Away)) {
            holder.tvLeagueAway!!.visibility = View.VISIBLE
            holder.tvLeagueAway!!.text = "" + items[position].Away
        } else {
            holder.tvLeagueAway!!.visibility = View.GONE
        }

        when (items[position].sport_id) {
            1 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1!!.text = items[position].FT_1
                holder.box2!!.text = items[position].FT_X
                holder.box3!!.text = items[position].FT_2
            }

            2 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_X
                holder.box3.text = items[position].FT_2
            }

            3 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.GONE

                holder.box1.text = items[position].FT_2W_1
                holder.box2.text = items[position].FT_2W_2
            }

            4 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_X
                holder.box3.text = items[position].FT_2
            }

            5 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_X
                holder.box3.text = items[position].FT_2
            }

            6 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.GONE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_2
            }

            7 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_X
                holder.box3.text = items[position].FT_2
            }

            8 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_X
                holder.box3.text = items[position].FT_2
            }

            9 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.GONE

                holder.box1.text = items[position].Player_H_1
                holder.box2.text = items[position].Player_H_2
            }

            10 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.GONE

                holder.box1.text = items[position].FT_2W_1
                holder.box2.text = items[position].FT_2W_2
            }

            11 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.GONE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_2
            }

            12 -> {
                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.VISIBLE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_X
                holder.box3.text = items[position].FT_2
            }

            13 -> {

                holder.box1!!.visibility = View.VISIBLE
                holder.box2!!.visibility = View.VISIBLE
                holder.box3!!.visibility = View.GONE

                holder.box1.text = items[position].FT_1
                holder.box2.text = items[position].FT_2
            }

            else -> {

            }
        }

    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_match_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val imgFlag: ShapeableImageView? = view.findViewById(R.id.imgFlag)
        val tvCountryName: TextView? = view.findViewById(R.id.tvCountryName)
        val tvTime: TextView? = view.findViewById(R.id.tvTime)
        val tvLeagueHome: TextView? = view.findViewById(R.id.tvLeagueHome)
        val tvLeagueAway: TextView? = view.findViewById(R.id.tvLeagueAway)
        val box1: TextView? = view.findViewById(R.id.box1)
        val box2: TextView? = view.findViewById(R.id.box2)
        val box3: TextView? = view.findViewById(R.id.box3)
    }

}

