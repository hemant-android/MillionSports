package com.forthpro.millionsport.ui.favourite.fragment.competition.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.CompetitionResponse
import com.forthpro.millionsport.model.response.TeamResponse
import com.google.android.material.imageview.ShapeableImageView


class CompetitionAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CompetitionAdapter.ViewHolder>() {
    private val items: ArrayList<CompetitionResponse.Competition> = arrayListOf()
    lateinit var onclick: onClickListner

    interface onClickListner {
        fun clickFavUnFav(position: Int,sportId: Int,country_id: Int,team_name: String,fav: Int)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<CompetitionResponse.Competition>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items!![position].country_logo != null && items!![position].country_logo.isNotEmpty()) {
            Glide.with(mContext)
                .load(BuildConfig.SERVER_URL + items!![position].country_logo)
                .centerCrop()
                .into(holder.imgFlag!!)
        }

        holder.tvCountryName!!.text = "(" + items[position].country_name + ")"
        holder.tvTeamName!!.text = "" + items[position].name

        holder.imgFav!!.setOnClickListener {
            onclick.clickFavUnFav(position,items[position].sport_id,items[position].country_id,items[position].name,items[position].favourite)
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_competition_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val imgFlag: ShapeableImageView? = view.findViewById(R.id.imgFlag)
        val tvCountryName: TextView? = view.findViewById(R.id.tvCountryName)
        val tvTeamName: TextView? = view.findViewById(R.id.tvTeamName)
        val imgFav: ImageView? = view.findViewById(R.id.imgFav)
    }

}

