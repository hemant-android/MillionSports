package com.forthpro.millionsport.ui.details.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.R
import com.forthpro.millionsport.model.response.PredictionDetailResponse

class ExpandablePredictionDetailAdapter(
    private val _context: Context, _listDataHeader: ArrayList<PredictionDetailResponse.PredictionTab>,
) : BaseExpandableListAdapter() {


    private var _listDataHeaderFiltered: ArrayList<PredictionDetailResponse.PredictionTab> =
        _listDataHeader
    private var _listDataHeaderOriginal = ArrayList<PredictionDetailResponse.PredictionTab>()

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
            convertView = infalInflater.inflate(R.layout.row_prediction_item_inner, null)
        }
        val tvLeagueName = convertView!!.findViewById<TextView>(R.id.tvLeagueName)
        val rvBox = convertView!!.findViewById<RecyclerView>(R.id.rvBox)

        var adapter = PredictionBoxAdapter(_context)
        rvBox.adapter = adapter

        var mChild = (getChild(groupPosition,childPosition) as PredictionDetailResponse.PredictionTab).label_array[childPosition]

        if (!TextUtils.isEmpty(mChild.label_name)) {
            tvLeagueName.visibility = View.VISIBLE
            tvLeagueName.text = mChild.label_name
        } else {
            tvLeagueName.visibility = View.GONE
        }

        adapter.setData(mChild.label1_array)

        var mParent = getGroup(groupPosition) as PredictionDetailResponse.PredictionTab

        return convertView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return _listDataHeaderFiltered[groupPosition].label_array.size
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
            convertView = infalInflater.inflate(R.layout.row_prediction_detail_item, null)
        }

        val imgNext = convertView!!.findViewById<ImageView>(R.id.imgNext)
        val tvLeagueName = convertView!!.findViewById<TextView>(R.id.tvPredictionName)

        var mTitle = getGroup(groupPosition) as PredictionDetailResponse.PredictionTab

        tvLeagueName.text = mTitle.label_name

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