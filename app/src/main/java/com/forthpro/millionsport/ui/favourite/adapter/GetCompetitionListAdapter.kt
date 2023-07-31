package com.forthpro.millionsport.ui.favourite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.GetCompetitionListResponse
import com.google.android.material.imageview.ShapeableImageView


class GetCompetitionListAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<GetCompetitionListAdapter.ViewHolder>() {
    private val items: ArrayList<GetCompetitionListResponse.AllCompetiontion> = arrayListOf()
    lateinit var onclick: onClickListner

    interface onClickListner {
        fun clickFavUnFav(position: Int,id: Int,sportId: Int,country_id: Int,team_name: String,fav: Int,flag: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<GetCompetitionListResponse.AllCompetiontion>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items!![position].country.country_logo != null && items!![position].country.country_logo.isNotEmpty()) {
            Glide.with(mContext)
                .load(BuildConfig.SERVER_URL + items!![position].country.country_logo)
                .centerCrop()
                .into(holder.imgFlag!!)
        }

        if (items[position].favourite == 1) {
            holder.imgFav!!.setImageResource(R.drawable.ic_fav_select)
        } else {
            holder.imgFav!!.setImageResource(R.drawable.ic_fav_un_select)
        }

        holder.tvCountryName!!.text = "(" + items[position].country.name + ")"
        holder.tvTeamName!!.text = "" + items[position].name

        holder.imgFav!!.setOnClickListener {

            onclick.clickFavUnFav(position,items[position].id,items[position].sport_id,items[position].country_id,items[position].name,items[position].favourite,items[position].country.country_logo)
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
        val imgFav: ImageView? = view.findViewById(R.id.imgFav)
        val tvCountryName: TextView? = view.findViewById(R.id.tvCountryName)
        val tvTeamName: TextView? = view.findViewById(R.id.tvTeamName)
    }

}

