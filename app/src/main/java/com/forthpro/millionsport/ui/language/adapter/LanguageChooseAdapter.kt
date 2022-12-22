package com.forthpro.millionsport.ui.language.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.GetAllLanguageResponse
import com.google.android.material.imageview.ShapeableImageView


class LanguageChooseAdapter(private val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<LanguageChooseAdapter.ViewHolder>() {
    private val items: ArrayList<GetAllLanguageResponse.Language> = arrayListOf()
    lateinit var onclick: onClickListner
    private var selectedItemPos = 0

    interface onClickListner {
        fun clickItem(languageId: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<GetAllLanguageResponse.Language>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items!![position].isSelect != null && items!![position].isSelect) {
            holder.imgCheck!!.setImageResource(R.drawable.ic_radio_button_checked)
        } else {
            holder.imgCheck!!.setImageResource(R.drawable.ic_radio_button_unchecked)
        }

        if (items!![position].language_logo != null && items!![position].language_logo.isNotEmpty()) {
            Glide.with(mContext).load(BuildConfig.SERVER_URL + items!![position].language_logo)
                .centerCrop()
                .into(holder.imgFlag!!)
        }

        holder.tvLanguage!!.text = "" + items[position].name

        holder.itemView!!.setOnClickListener {
            selectClickedAndUnselectPrevious(holder)
            onclick.clickItem(items[position].id)
        }

    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_language_choose, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val imgFlag: ShapeableImageView? = view.findViewById(R.id.imgFlag)
        val tvLanguage: TextView? = view.findViewById(R.id.tvLanguage)
        val imgCheck: ImageView? = view.findViewById(R.id.imgCheck)
    }

    private fun selectClickedAndUnselectPrevious(holder: ViewHolder) {
        items[selectedItemPos].isSelect = false
        items[holder.adapterPosition].isSelect = true
        notifyItemChanged(selectedItemPos)
        notifyItemChanged(holder.adapterPosition)
        selectedItemPos = holder.adapterPosition
    }
}

