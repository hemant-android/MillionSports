package com.forthpro.millionsport.ui.favourite_sport.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.model.response.FavouriteSportResponse
import com.forthpro.millionsport.ui.favourite_sport.dragdrop.ItemMoveCallbackListener
import com.forthpro.millionsport.ui.favourite_sport.dragdrop.OnStartDragListener
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections


class FavSportsDragDropAdapter(
    private val mContext: Context,
    private val startDragListener: OnStartDragListener,
) : RecyclerView.Adapter<FavSportsDragDropAdapter.ViewHolder>(), ItemMoveCallbackListener.Listener {
    val items: ArrayList<FavouriteSportResponse.SportsArray>? = arrayListOf()

    private var sportId = ""

    lateinit var onclick: onClickListner

    interface onClickListner {
        fun dragAndDropSportItem(fromPosition: Int, toPosition: Int)
        fun updatePosition(updateItem: String)
    }

    fun setClickListner(onclick: onClickListner) {
        this.onclick = onclick;
    }

    fun setSportIdData(SportId: String) {
        sportId = SportId
        notifyDataSetChanged()
    }

    fun setData(item: ArrayList<FavouriteSportResponse.SportsArray>) {
        items!!.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (!TextUtils.isEmpty(sportId) && sportId == items!![position].id) {
            if (items!![position].light_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + items!![position].light_logo)
                    .into(holder.imgSport!!)
            }
        } else {
            if (items!![position].grey_logo != null) {
                Glide.with(mContext)
                    .load(BuildConfig.SERVER_URL + items!![position].light_logo)
                    .into(holder.imgSport!!)
            }
        }

        holder.tvSportName!!.text = items!![position].title

        holder.itemView.setOnLongClickListener {
            this.startDragListener.onStartDrag(holder)
            true
        }

        /*holder.itemView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
            }
            return@setOnTouchListener true
        }*/
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_fav_sport_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llMain: LinearLayout? = view.findViewById(R.id.llMain)
        val imgSport: ImageView? = view.findViewById(R.id.imgSport)
        val tvSportName: TextView? = view.findViewById(R.id.tvSportName)
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)


//        onclick.dragAndDropSportItem(fromPosition, toPosition)

    }

    override fun onRowSelected(itemViewHolder: ViewHolder) {
    }

    override fun onRowClear(itemViewHolder: ViewHolder) {
    }

    override fun onUpdateArr(items: ArrayList<FavouriteSportResponse.SportsArray>?) {
        val array = JSONArray()
        for(item in items!!){
            val jsonObject = JSONObject()
            jsonObject.put("device_id", PreferenceHelper.deviceId)
            jsonObject.put("sport_id", item.id)
            jsonObject.put("position", item.position)
            array.put(jsonObject)
        }
        array.toString()
        onclick.updatePosition(array.toString())

    }
}

