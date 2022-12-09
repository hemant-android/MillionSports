package com.forthpro.millionsport.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.databinding.FragmentHomeBinding
import com.forthpro.millionsport.ui.home.adapter.GameAdapter
import com.forthpro.millionsport.ui.home.adapter.CustomExpandableListAdapter
import com.forthpro.millionsport.ui.home.adapter.PopularCompetitionAdapter
import com.forthpro.millionsport.util.ExpandableListData.data

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val mGameAdapter: GameAdapter by lazy { GameAdapter(requireActivity()) }
    private val mPopularCompetitionAdapter: PopularCompetitionAdapter by lazy {
        PopularCompetitionAdapter(requireActivity())
    }

    private var mExpandableListAdapter: ExpandableListAdapter? = null
    private var titleList: ArrayList<String>? = arrayListOf()

    private val gameList = mutableListOf("Soccer", "Hockey", "Basketball", "Tennis", "A.Football", "Baseball", "Handball")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        itemTouchHelper.attachToRecyclerView(binding.rvGame)
        mGameAdapter.differ.submitList(gameList)
        binding.rvGame.adapter = mGameAdapter

        binding.rvPopularCompetitions.adapter = mPopularCompetitionAdapter
        binding.rvCompetitionsByCountry.adapter = mPopularCompetitionAdapter

        var listData = data
        titleList = ArrayList(listData.keys)

        mExpandableListAdapter =
            CustomExpandableListAdapter(requireActivity(), titleList as ArrayList<String>, listData)

        binding.expendablePopularCompetitions.setAdapter(mExpandableListAdapter)
        binding.expendableCompetitionsByCountry.setAdapter(mExpandableListAdapter)

        // PopularCompetitions

        binding.expendablePopularCompetitions!!.setOnGroupExpandListener { groupPosition ->
//            Toast.makeText(requireActivity(),(titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show()
        }
        binding.expendablePopularCompetitions!!.setOnGroupCollapseListener { groupPosition ->
//            Toast.makeText(requireActivity(),(titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",Toast.LENGTH_SHORT).show()
        }
        binding.expendablePopularCompetitions!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
//            Toast.makeText(requireActivity(),"Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!![childPosition],Toast.LENGTH_SHORT).show()
            false
        }

        // CompetitionsByCountry

        binding.expendableCompetitionsByCountry!!.setOnGroupExpandListener { groupPosition ->
//            Toast.makeText(requireActivity(),(titleList as ArrayList<String>)[groupPosition] + " List Expanded.",Toast.LENGTH_SHORT).show()
        }
        binding.expendableCompetitionsByCountry!!.setOnGroupCollapseListener { groupPosition ->
//            Toast.makeText(requireActivity(),(titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",Toast.LENGTH_SHORT).show()
        }
        binding.expendableCompetitionsByCountry!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
//            Toast.makeText(requireActivity(),"Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!![childPosition],Toast.LENGTH_SHORT).show()
            false
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : SimpleCallback(UP or DOWN or START or END, 0) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    val adapter = recyclerView.adapter as GameAdapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter.moveItem(from, to)
                    adapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                }

                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int,
                ) {
                    super.onSelectedChanged(viewHolder, actionState)

                    if (actionState == ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.alpha = 0.5f
                    }
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                ) {
                    super.clearView(recyclerView, viewHolder)

                    viewHolder.itemView.alpha = 1.0f
                }
            }

        ItemTouchHelper(simpleItemTouchCallback)
    }
}