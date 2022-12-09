package com.forthpro.millionsport.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.google.android.material.imageview.ShapeableImageView

class PopularCompetitionAdapter(val mContext: Context) :
    RecyclerView.Adapter<PopularCompetitionAdapter.ViewHolder>() {
    private val items: ArrayList<String>? = arrayListOf()

    fun setData(item: ArrayList<String>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_popular_competition_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLeagueName: TextView? = view.findViewById(R.id.tvLeagueName)
        val imgFlag: ShapeableImageView? = view.findViewById(R.id.imgFlag)
        val imgNext: ImageView? = view.findViewById(R.id.imgNext)
    }

}

