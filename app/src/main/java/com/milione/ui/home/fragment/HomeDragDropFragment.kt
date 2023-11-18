package com.milione.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.FragmentHomeBinding
import com.milione.model.RequestBodies
import com.milione.model.response.DashboardResponse
import com.milione.repository.AppRepository
import com.milione.ui.home.adapter.DateWiseAdapter
import com.milione.ui.home.viewmodel.DashboardViewModel
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory
import com.milione.ui.home.adapter.ExpandablePopularCompetitionAdapter
import com.milione.ui.home.adapter.ExpandablePopularCompetitionByCountryAdapter
import com.milione.ui.home.adapter.SportsDragDropAdapter

class HomeDragDropFragment : Fragment(), SportsDragDropAdapter.onClickListner, DateWiseAdapter.onClickListner {

    var sportId: String? = ""
    var chooseDate: String? = ""
    private var arrPopularCompetitionsCountry: ArrayList<DashboardResponse.PopularCompetitionsCountry>? =
        arrayListOf()
    private var arrPopularCompetitions: ArrayList<DashboardResponse.PopularCompetition>? =
        arrayListOf()
    private var arrDate: ArrayList<DashboardResponse.DateArray>? = arrayListOf()
    private var arrSports: ArrayList<DashboardResponse.Sport>? = arrayListOf()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: FragmentHomeBinding

    private val mSportAdapter: SportsDragDropAdapter by lazy { SportsDragDropAdapter(requireActivity()) }
    private val mDateWiseAdapter: DateWiseAdapter by lazy { DateWiseAdapter(requireActivity()) }

    private var popularCompetitionAdapter: ExpandablePopularCompetitionAdapter? = null
    private var popularCompetitionByCountryAdapter: ExpandablePopularCompetitionByCountryAdapter? =
        null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        setupViewModel()

//        itemTouchHelper.attachToRecyclerView(binding.rvGame)
//        mSportAdapter.differ.submitList(gameList)
//        binding.rvGame.adapter = mSportAdapter

        binding.rvDateWise.adapter = mDateWiseAdapter
        mDateWiseAdapter.setClickListner(this)



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

        val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId,"", "")
        viewModel.getDashboard(body)

        viewModel.getDashboardResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvCompetitionsByCountry.text =
                                response.data?.POPULAR_COMPETITIONS_BY_COUNTRY_LABEL
                            binding.tvPopularCompetitions.text =
                                response.data?.POPULAR_COMPETITIONS_LABEL

                            if (response.data?.sports != null && response.data?.sports.isNotEmpty()) {

                                arrSports = response.data?.sports

                                mSportAdapter.setSportIdData(sportId!!)

                                itemTouchHelper.attachToRecyclerView(binding.rvGame)
                                mSportAdapter.differ.submitList(arrSports!!)

                                binding.rvGame.adapter = mSportAdapter
                                mSportAdapter.setClickListner(this)
                            }

                            if (response.data?.dateArray != null && response.data?.dateArray.isNotEmpty()) {
                                arrDate = response.data?.dateArray

                                mDateWiseAdapter.setData(arrDate!!, chooseDate)
                            }

                            if (arrPopularCompetitions != null && arrPopularCompetitions!!.size > 0) {
                                arrPopularCompetitions!!.clear()
                            }

                            if (arrPopularCompetitionsCountry != null && arrPopularCompetitionsCountry!!.size > 0) {
                                arrPopularCompetitionsCountry!!.clear()
                            }

                            if (response.data?.popular_competitions != null && response.data?.popular_competitions.isNotEmpty()) {
                                arrPopularCompetitions = response.data?.popular_competitions

                                popularCompetitionAdapter = ExpandablePopularCompetitionAdapter(
                                    requireActivity(),
                                    arrPopularCompetitions!!)
                                binding.expendablePopularCompetitions.setAdapter(
                                    popularCompetitionAdapter)
                            } else {
                                if (popularCompetitionAdapter != null) {
                                    binding.expendablePopularCompetitions.setAdapter(
                                        popularCompetitionAdapter)
                                    popularCompetitionAdapter!!.notifyDataSetChanged()
                                }
                            }

                            if (response.data?.popular_competitions_country != null && response.data?.popular_competitions_country.isNotEmpty()) {
                                arrPopularCompetitionsCountry =
                                    response.data?.popular_competitions_country
                                popularCompetitionByCountryAdapter =
                                    ExpandablePopularCompetitionByCountryAdapter(requireActivity(),
                                        arrPopularCompetitionsCountry!!)
                                binding.expendableCompetitionsByCountry.setAdapter(
                                    popularCompetitionByCountryAdapter)
                            } else {
                                if (popularCompetitionByCountryAdapter != null) {
                                    binding.expendableCompetitionsByCountry.setAdapter(
                                        popularCompetitionByCountryAdapter)
                                    popularCompetitionByCountryAdapter!!.notifyDataSetChanged()
                                }
                            }

//                            binding.expendablePopularCompetitions.isNestedScrollingEnabled = true
//                            binding.expendableCompetitionsByCountry.isNestedScrollingEnabled = true

                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Data not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.e("error", message)
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }

    }

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : SimpleCallback(UP or DOWN or START or END, 0) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    val adapter = recyclerView.adapter as SportsDragDropAdapter
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


    private fun hideProgressBar() {
        binding.progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]

    }

    override fun clickSportItem(id: String) {
        sportId = id
        val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId,sportId!!, chooseDate!!)
        viewModel.getDashboard(body)
    }

    override fun clickDateItem(date: String) {
        chooseDate = date

        val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId,sportId!!, chooseDate!!)
        viewModel.getDashboard(body)
    }
}