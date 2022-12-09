package com.forthpro.millionsport.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R


class GameAdapter(val mContext: Context) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    private val items: ArrayList<String>? = arrayListOf()

    fun setData(item: ArrayList<String>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGameName!!.text = differ.currentList[position]
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

    private val differCallBack = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    fun moveItem(from: Int, to: Int) {

        val list = differ.currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1 , fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }
        differ.submitList(list)


    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPlayer: ImageView? = view.findViewById(R.id.imgPlayer)
        val tvGameName: TextView? = view.findViewById(R.id.tvGameName)
    }

}

