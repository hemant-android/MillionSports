package com.forthpro.millionsport.ui.main.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.model.response.GetMovieList
import com.forthpro.millionsport.R


class MainAdapter(
    val mContext: Context,
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val items: ArrayList<GetMovieList.Result>? = arrayListOf()

    fun setData(item: ArrayList<GetMovieList.Result>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId!!.text = items!![position].id
        holder.tvOriginalLanguage!!.text = items[position].original_language
        holder.tvPopularity!!.text = items[position].popularity.toString()
        holder.tvTitle!!.text = items[position].title
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_main_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView? = view.findViewById(R.id.tvId)
        val tvOriginalLanguage: TextView? = view.findViewById(R.id.tvOriginalLanguage)
        val tvPopularity: TextView? = view.findViewById(R.id.tvPopularity)
        val tvTitle: TextView? = view.findViewById(R.id.tvTitle)
    }

}

