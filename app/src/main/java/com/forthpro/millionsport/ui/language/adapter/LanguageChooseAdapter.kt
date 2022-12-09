package com.forthpro.millionsport.ui.language.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.forthpro.millionsport.R
import com.google.android.material.imageview.ShapeableImageView


class LanguageChooseAdapter(val mContext: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<LanguageChooseAdapter.ViewHolder>() {
    private val items: ArrayList<String> = arrayListOf()
    lateinit var onclick: onClickListner
    var selectedPosition = -1

    interface onClickListner {
        fun clickItem(addressId: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setData(item: ArrayList<String>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*if (items!![position].vehicle_image != null && items!![position].vehicle_image.isNotEmpty()) {
            Glide.with(mContext).load(items!![position].vehicle_image[0]).centerCrop()
                .into(holder.imgPark!!)
        }*/

        holder.tvLanguage!!.text = "" + position

        holder.itemView!!.setOnClickListener {
            /*Intent(mContext, ChooseTimeFormatActivity::class.java).also {
                mContext.startActivity(it)
            }*/
            onclick.clickItem("" + position)
        }

    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_language_choose, parent, false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val imgFlag: ShapeableImageView? = view.findViewById(R.id.imgFlag)
        val tvLanguage: TextView? = view.findViewById(R.id.tvLanguage)
        val imgCheck: ImageView? = view.findViewById(R.id.imgCheck)
    }
}

