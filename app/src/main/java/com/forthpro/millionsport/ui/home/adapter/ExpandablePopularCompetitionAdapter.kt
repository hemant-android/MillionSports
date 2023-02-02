package com.forthpro.millionsport.ui.home.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.DashboardResponse
import com.google.android.material.imageview.ShapeableImageView

class ExpandablePopularCompetitionAdapter(
    private val _context: Context, _listDataHeader: ArrayList<DashboardResponse.PopularCompetition>,
) : BaseExpandableListAdapter() {


    private var _listDataHeaderFiltered: ArrayList<DashboardResponse.PopularCompetition> =
        _listDataHeader
    private var _listDataHeaderOriginal = ArrayList<DashboardResponse.PopularCompetition>()

    init {
        _listDataHeaderOriginal.addAll(_listDataHeader)
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): Any {
        return _listDataHeaderFiltered[groupPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup,
    ): View {
        var convertView = convertView

        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.row_popular_competition_item_inner, null)
        }
        val tvLeagueName = convertView!!.findViewById<TextView>(R.id.tvLeagueName)
        val tvLeagueCountry = convertView!!.findViewById<TextView>(R.id.tvLeagueCountry)
        val tvTime = convertView!!.findViewById<TextView>(R.id.tvTime)

        val box1 = convertView!!.findViewById<TextView>(R.id.box1)
        val box2 = convertView!!.findViewById<TextView>(R.id.box2)
        val box3 = convertView!!.findViewById<TextView>(R.id.box3)

        var mChild = (getChild(groupPosition,
            childPosition) as DashboardResponse.PopularCompetition).prediction[childPosition]

        if (!TextUtils.isEmpty(mChild.Home)) {
            tvLeagueName.visibility = View.VISIBLE
            tvLeagueName.text = mChild.Home
        } else {
            tvLeagueName.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(mChild.Away)) {
            tvLeagueCountry.visibility = View.VISIBLE
            tvLeagueCountry.text = mChild.Away
        } else {
            tvLeagueCountry.visibility = View.GONE
        }

        tvTime.text = mChild.prediction_time

        var mParent = getGroup(groupPosition) as DashboardResponse.PopularCompetition

        when (mParent.sport_id) {
            1 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            2 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            3 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.GONE

                box1.text = mChild.FT_2W_1
                box2.text = mChild.FT_2W_2
            }
            4 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            5 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            6 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.GONE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_2
            }
            7 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            8 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            9 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.GONE

                box1.text = mChild.Player_H_1
                box2.text = mChild.Player_H_2
            }
            10 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.GONE

                box1.text = mChild.FT_2W_1
                box2.text = mChild.FT_2W_2
            }
            11 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.GONE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_2
            }
            12 -> {
                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.VISIBLE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_X
                box3.text = mChild.FT_2
            }
            13 -> {

                box1.visibility = View.VISIBLE
                box2.visibility = View.VISIBLE
                box3.visibility = View.GONE

                box1.text = mChild.FT_1
                box2.text = mChild.FT_2
            }
            else -> {

            }
        }

        return convertView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return _listDataHeaderFiltered[groupPosition].prediction.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return this._listDataHeaderFiltered[groupPosition]
    }

    override fun getGroupCount(): Int {
        return this._listDataHeaderFiltered.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup,
    ): View {
        var convertView = convertView

        if (convertView == null) {
            val infalInflater =
                this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.row_popular_competition_item, null)
        }

        val imgFlag = convertView!!.findViewById<ShapeableImageView>(R.id.imgFlag)
        val imgNext = convertView!!.findViewById<ImageView>(R.id.imgNext)
        val tvLeagueName = convertView!!.findViewById<TextView>(R.id.tvLeagueName)

        var mTitle = getGroup(groupPosition) as DashboardResponse.PopularCompetition

        tvLeagueName.text = mTitle.name

        if (mTitle.country_logo != null) {
            Glide.with(_context)
                .load(BuildConfig.SERVER_URL + mTitle.country_logo)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(imgFlag)
        }

        if (isExpanded) {
            imgNext.rotation = 270F
        } else {
            imgNext.rotation = 90F
        }

        return convertView!!
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}