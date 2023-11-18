package com.milione.ui.favourite.adapter

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import app.milionesports.de.BuildConfig
import app.milionesports.de.R
import com.milione.model.response.FavouriteCommonResponse


class SportsFavAdapter(private val mContext: Activity) :
    RecyclerView.Adapter<SportsFavAdapter.ViewHolder>() {
    private val items: ArrayList<FavouriteCommonResponse.Sport>? = arrayListOf()

    private var sportId = ""

    private var selectedItemPos = 0
    lateinit var onclick: onClickListner
    private var width = 0

    interface onClickListner {
        fun clickSportItem(sportId: String,icon: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setSportIdData(SportId: String, arrSports: ArrayList<FavouriteCommonResponse.Sport>) {
        sportId = SportId
        items!!.clear()
        items.addAll(arrSports)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val displayMetrics = DisplayMetrics()
        mContext.windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        val mWidth = (width / 5)

        holder.itemView!!.layoutParams = LinearLayout.LayoutParams(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)

        if (items!![position].isSelect) {
            if (items!![position].light_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + items[position].light_logo)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imgSport!!)
            }
        } else {
            if (items!![position].grey_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + items!![position].grey_logo)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imgSport!!)
            }
        }


        holder.tvSportName!!.text = items!![position].title
        holder.tvSportCount!!.text = items[position].sportsCount

        holder.itemView.setOnClickListener {
            onclick.clickSportItem(items[position].id,items[position].light_logo)
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

