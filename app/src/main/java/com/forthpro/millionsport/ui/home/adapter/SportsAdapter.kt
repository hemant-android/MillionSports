package com.forthpro.millionsport.ui.home.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
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

    fun setSportIdData(SportId: String) {
        sportId = SportId
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (!TextUtils.isEmpty(sportId) && sportId == differ.currentList!![position].id) {
            if (differ.currentList[position].light_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + differ.currentList[position].light_logo)
                    .centerCrop()
                    .placeholder(R.mipmap.player)
                    .into(holder.imgSport!!)
            }
        } else {
            if (differ.currentList[position].grey_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + differ.currentList[position].grey_logo)
                    .centerCrop()
                    .placeholder(R.mipmap.player)
                    .into(holder.imgSport!!)
            }
        }

        holder.tvSportName!!.text = differ.currentList[position].title

        holder.itemView.setOnClickListener {
            onclick.clickSportItem(differ.currentList[position].id)
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

    private val differCallBack = object : DiffUtil.ItemCallback<DashboardResponse.Sport>() {

        override fun areItemsTheSame(
            oldItem: DashboardResponse.Sport,
            newItem: DashboardResponse.Sport,
        ): Boolean {
            return oldItem.id.contentEquals(newItem.id)
        }

        override fun areContentsTheSame(
            oldItem: DashboardResponse.Sport,
            newItem: DashboardResponse.Sport,
        ): Boolean {
            return oldItem.id.contentEquals(newItem.id)
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun moveItem(from: Int, to: Int) {

        val list = differ.currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1, fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }
        differ.submitList(list)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgSport: ImageView? = view.findViewById(R.id.imgSport)
        val tvSportName: TextView? = view.findViewById(R.id.tvSportName)
    }

    private fun selectClickedAndUnselectPrevious(holder: ViewHolder) {
        differ.currentList!![selectedItemPos].isSelect = false
        differ.currentList!![holder.adapterPosition].isSelect = true
        notifyItemChanged(selectedItemPos)
        notifyItemChanged(holder.adapterPosition)
        selectedItemPos = holder.adapterPosition
    }
}

