package com.forthpro.millionsport.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.DashboardResponse


class SportsAdapter(private val mContext: Context) :
    RecyclerView.Adapter<SportsAdapter.ViewHolder>() {
    private val items: ArrayList<DashboardResponse.Sport>? = arrayListOf()

    private var sportId = ""

    private var selectedItemPos = 0
    lateinit var onclick: onClickListner

    interface onClickListner {
        fun clickSportItem(sportId: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setSportIdData(SportId: String, arrSports: ArrayList<DashboardResponse.Sport>) {
        sportId = SportId
        items!!.clear()
        items.addAll(arrSports)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items!![position].isSelect) {
            if (items!![position].light_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + items[position].light_logo)
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imgSport!!)
            }
        } else {
            if (items!![position].grey_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + items!![position].grey_logo)
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imgSport!!)
            }
        }
        /*if (position == 0){
            if (!TextUtils.isEmpty(sportId) && sportId == items!![position].id) {
                if (items!![position].light_logo != null) {
                    Glide.with(mContext)
                        .load(BuildConfig.SERVER_URL + items[position].light_logo)
                        .centerCrop()
                        .placeholder(R.mipmap.player)
                        .into(holder.imgSport!!)
                }
            } else if(TextUtils.isEmpty(sportId)){
                if (items!![position].light_logo != null) {
                    Glide.with(mContext)
                        .load(BuildConfig.SERVER_URL + items[position].light_logo)
                        .centerCrop()
                        .placeholder(R.mipmap.player)
                        .into(holder.imgSport!!)
                }
            } else{
                if (items!![position].grey_logo != null) {
                    Glide.with(mContext)
                        .load(BuildConfig.SERVER_URL + items!![position].grey_logo)
                        .centerCrop()
                        .placeholder(R.mipmap.player)
                        .into(holder.imgSport!!)
                }
            }
        }else{
            if (!TextUtils.isEmpty(sportId) && sportId == items!![position].id) {
                if (items!![position].light_logo != null) {
                    Glide.with(mContext)
                        .load(BuildConfig.SERVER_URL + items[position].light_logo)
                        .centerCrop()
                        .placeholder(R.mipmap.player)
                        .into(holder.imgSport!!)
                }
            } else {
                if (items!![position].grey_logo != null) {
                    Glide.with(mContext)
                        .load(BuildConfig.SERVER_URL + items!![position].grey_logo)
                        .centerCrop()
                        .placeholder(R.mipmap.player)
                        .into(holder.imgSport!!)
                }
            }
        }*/


        holder.tvSportName!!.text = items!![position].title
        holder.tvSportCount!!.text = items[position].sportsCount

        holder.itemView.setOnClickListener {
            onclick.clickSportItem(items[position].id)
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_game_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgSport: ImageView? = view.findViewById(R.id.imgSport)
        val tvSportName: TextView? = view.findViewById(R.id.tvSportName)
        val tvSportCount: TextView? = view.findViewById(R.id.tvSportCount)
    }
}

