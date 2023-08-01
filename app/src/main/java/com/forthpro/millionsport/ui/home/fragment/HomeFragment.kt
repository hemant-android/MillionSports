package com.forthpro.millionsport.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.databinding.FragmentHomeBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.DashboardResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.details.PredictionDetailActivity
import com.forthpro.millionsport.ui.home.adapter.DateWiseAdapter
import com.forthpro.millionsport.ui.home.adapter.ExpandablePopularCompetitionAdapter
import com.forthpro.millionsport.ui.home.adapter.ExpandablePopularCompetitionByCountryAdapter
import com.forthpro.millionsport.ui.home.adapter.SportsAdapter
import com.forthpro.millionsport.ui.home.viewmodel.DashboardViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class HomeFragment : Fragment(), SportsAdapter.onClickListner, DateWiseAdapter.onClickListner {

    private var arrPopularCompetitionsCountry: ArrayList<DashboardResponse.PopularCompetitionsCountry>? =
        arrayListOf()
    private var arrPopularCompetitions: ArrayList<DashboardResponse.PopularCompetition>? =
        arrayListOf()
    private var arrDate: ArrayList<DashboardResponse.DateArray>? = arrayListOf()
    private var arrSports: ArrayList<DashboardResponse.Sport>? = arrayListOf()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: FragmentHomeBinding

    private val mSportAdapter: SportsAdapter by lazy { SportsAdapter(requireActivity()) }
    private val mDateWiseAdapter: DateWiseAdapter by lazy { DateWiseAdapter(requireActivity()) }

    private var popularCompetitionAdapter: ExpandablePopularCompetitionAdapter? = null
    private var popularCompetitionByCountryAdapter: ExpandablePopularCompetitionByCountryAdapter? =
        null

    var playerImage: String? = ""
    var sportId: String? = ""
    var chooseDate: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        setupViewModel()

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

            var sportId =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.sport_id
            var countryFlag =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.country_logo
            var predictionName =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.prediction[childPosition].prediction_name
            var predictionId =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.prediction[childPosition].id
            var home =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.prediction[childPosition].Home
            var away =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.prediction[childPosition].Away
            var time =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.prediction[childPosition].prediction_time
            var date =
                (arrPopularCompetitions as ArrayList<DashboardResponse.PopularCompetition>)[groupPosition]!!.prediction[childPosition].prediction_date

            if (sportId.toString() == "13") {

            } else {
                Intent(requireActivity(), PredictionDetailActivity::class.java).also {
                    it.putExtra("sportId", sportId.toString())
                    it.putExtra("predictionId", predictionId)
                    it.putExtra("home", home)
                    it.putExtra("away", away)
                    it.putExtra("playerImage", playerImage)
                    it.putExtra("predictionName", predictionName)
                    it.putExtra("countryFlag", countryFlag)
                    it.putExtra("time", time)
                    it.putExtra("date", date)
                    startActivity(it)
                }
            }

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
            var sportId =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.sport_id
            var predictionId =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.prediction[childPosition].id
            var home =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.prediction[childPosition].Home
            var away =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.prediction[childPosition].Away

            var predictionName =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.prediction[childPosition].prediction_name

            var time =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.prediction[childPosition].prediction_time
            var date =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.prediction[childPosition].prediction_date

            var countryFlag =
                (arrPopularCompetitionsCountry as ArrayList<DashboardResponse.PopularCompetitionsCountry>)[groupPosition]!!.country_logo

            if (sportId.toString() == "13") {

            } else {
                Intent(requireActivity(), PredictionDetailActivity::class.java).also {
                    it.putExtra("sportId", sportId.toString())
                    it.putExtra("predictionId", predictionId)
                    it.putExtra("home", home)
                    it.putExtra("away", away)
                    it.putExtra("playerImage", playerImage)
                    it.putExtra("predictionName", predictionName)
                    it.putExtra("countryFlag", countryFlag)
                    it.putExtra("time", time)
                    it.putExtra("date", date)
                    startActivity(it)
                }
            }
            false
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (PreferenceHelper.isFavSport) {

            PreferenceHelper.isFavSport = false

            val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId, "", "")
            viewModel.getDashboard(body)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId, "", "")
        viewModel.getDashboard(body)

        viewModel.getDashboardResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvPopularCompetitions.text =
                                response.data?.POPULAR_COMPETITIONS_LABEL
                            binding.tvCompetitionsByCountry.text =
                                response.data?.POPULAR_COMPETITIONS_BY_COUNTRY_LABEL

                            if (arrSports != null && arrSports!!.size > 0) {
                                arrSports!!.clear()
                            }

                            if (arrDate != null && arrDate!!.size > 0) {
                                arrDate!!.clear()
                            }

                            if (response.data?.sports != null && response.data?.sports.isNotEmpty()) {
                                arrSports = response.data.sports

                                if (arrSports != null && arrSports!!.isNotEmpty()) {
                                    for (i in arrSports!!.indices) {
                                        if (i == 0) {
                                            playerImage = arrSports!![i].light_logo
                                            sportId = arrSports!![i].id
                                        }
                                        arrSports!![i].isSelect = i == 0
                                    }
                                }
                                mSportAdapter.setSportIdData(arrSports!!)

                                binding.rvGame.adapter = mSportAdapter
                                mSportAdapter.setClickListner(this)
                            }

                            if (response.data?.dateArray != null && response.data?.dateArray.isNotEmpty()) {
                                arrDate = response.data.dateArray

                                if (arrDate != null && arrDate!!.isNotEmpty()) {
                                    for (i in arrDate!!.indices) {
                                        if (i == 0) {
                                            chooseDate = arrDate!![i].date_value
                                            arrDate!![i].isSelect = i == 0
                                        }
                                    }
                                }
                                mDateWiseAdapter.setData(arrDate!!, chooseDate)
                            }

                            if (arrPopularCompetitions != null && arrPopularCompetitions!!.size > 0) {
                                arrPopularCompetitions!!.clear()
                            }

                            if (arrPopularCompetitionsCountry != null && arrPopularCompetitionsCountry!!.size > 0) {
                                arrPopularCompetitionsCountry!!.clear()
                            }

                            if ((response.data?.popular_competitions != null && response.data?.popular_competitions.isNotEmpty()) || (response.data?.popular_competitions_country != null && response.data?.popular_competitions_country.isNotEmpty())) {
                                binding.llNoRecordFound.visibility = View.GONE
                            } else {
                                binding.llNoRecordFound.visibility = View.VISIBLE
                                binding.tvNoRecordFound.text = response.data?.message

                                Glide.with(requireActivity())
                                    .load(BuildConfig.SERVER_URL + playerImage)
                                    .placeholder(R.drawable.progress_animation)
                                    .into(binding.imgSport!!)
                            }

                            if (response.data?.popular_competitions != null && response.data?.popular_competitions.isNotEmpty()) {
                                binding.tvPopularCompetitions.visibility = View.VISIBLE
                                arrPopularCompetitions = response.data?.popular_competitions

                                popularCompetitionAdapter = ExpandablePopularCompetitionAdapter(
                                    requireActivity(),
                                    arrPopularCompetitions!!
                                )
                                binding.expendablePopularCompetitions.setAdapter(
                                    popularCompetitionAdapter
                                )
                            } else {
                                if (popularCompetitionAdapter != null) {
                                    binding.expendablePopularCompetitions.setAdapter(
                                        popularCompetitionAdapter
                                    )
                                    popularCompetitionAdapter!!.notifyDataSetChanged()
                                }

                                binding.tvPopularCompetitions.visibility = View.GONE
                            }

                            if (response.data?.popular_competitions_country != null && response.data?.popular_competitions_country.isNotEmpty()) {
                                binding.tvCompetitionsByCountry.visibility = View.VISIBLE
                                arrPopularCompetitionsCountry =
                                    response.data?.popular_competitions_country

                                popularCompetitionByCountryAdapter =
                                    ExpandablePopularCompetitionByCountryAdapter(
                                        requireActivity(),
                                        arrPopularCompetitionsCountry!!
                                    )
                                binding.expendableCompetitionsByCountry.setAdapter(
                                    popularCompetitionByCountryAdapter
                                )
                            } else {
                                if (popularCompetitionByCountryAdapter != null) {
                                    binding.expendableCompetitionsByCountry.setAdapter(
                                        popularCompetitionByCountryAdapter
                                    )
                                    popularCompetitionByCountryAdapter!!.notifyDataSetChanged()
                                }
                                binding.tvCompetitionsByCountry.visibility = View.GONE
                            }

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

        viewModel.getDashboardResponseFilter.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvPopularCompetitions.text =
                                response.data?.POPULAR_COMPETITIONS_LABEL
                            binding.tvCompetitionsByCountry.text =
                                response.data?.POPULAR_COMPETITIONS_BY_COUNTRY_LABEL

                            if (arrSports != null && arrSports!!.size > 0) {
                                arrSports!!.clear()
                            }

                            if (response.data?.sports != null && response.data?.sports.isNotEmpty()) {
                                arrSports = response.data.sports

                                if (arrSports != null && arrSports!!.isNotEmpty()) {
                                    for (i in arrSports!!.indices) {
                                        arrSports!![i].isSelect = arrSports!![i].id == sportId
                                    }
                                    mSportAdapter.setSportIdData(arrSports!!)
                                }
                            }

                            if (arrPopularCompetitions != null && arrPopularCompetitions!!.size > 0) {
                                arrPopularCompetitions!!.clear()
                            }

                            if (arrPopularCompetitionsCountry != null && arrPopularCompetitionsCountry!!.size > 0) {
                                arrPopularCompetitionsCountry!!.clear()
                            }

                            if ((response.data?.popular_competitions != null && response.data?.popular_competitions.isNotEmpty()) || (response.data?.popular_competitions_country != null && response.data?.popular_competitions_country.isNotEmpty())) {
                                binding.llNoRecordFound.visibility = View.GONE
                            } else {
                                binding.llNoRecordFound.visibility = View.VISIBLE
                                binding.tvNoRecordFound.text = response.data?.message
                                Glide.with(requireActivity())
                                    .load(BuildConfig.SERVER_URL + playerImage)
                                    .placeholder(R.drawable.progress_animation)
                                    .into(binding.imgSport!!)
                            }

                            if (response.data?.popular_competitions != null && response.data?.popular_competitions.isNotEmpty()) {
                                binding.tvPopularCompetitions.visibility = View.VISIBLE
                                arrPopularCompetitions = response.data?.popular_competitions

                                popularCompetitionAdapter = ExpandablePopularCompetitionAdapter(
                                    requireActivity(),
                                    arrPopularCompetitions!!
                                )
                                binding.expendablePopularCompetitions.setAdapter(
                                    popularCompetitionAdapter
                                )
                            } else {
                                if (popularCompetitionAdapter != null) {
                                    binding.expendablePopularCompetitions.setAdapter(
                                        popularCompetitionAdapter
                                    )
                                    popularCompetitionAdapter!!.notifyDataSetChanged()
                                }

                                binding.tvPopularCompetitions.visibility = View.GONE
                            }

                            if (response.data?.popular_competitions_country != null && response.data?.popular_competitions_country.isNotEmpty()) {
                                binding.tvCompetitionsByCountry.visibility = View.VISIBLE
                                arrPopularCompetitionsCountry =
                                    response.data?.popular_competitions_country

                                popularCompetitionByCountryAdapter =
                                    ExpandablePopularCompetitionByCountryAdapter(
                                        requireActivity(),
                                        arrPopularCompetitionsCountry!!
                                    )
                                binding.expendableCompetitionsByCountry.setAdapter(
                                    popularCompetitionByCountryAdapter
                                )
                            } else {
                                if (popularCompetitionByCountryAdapter != null) {
                                    binding.expendableCompetitionsByCountry.setAdapter(
                                        popularCompetitionByCountryAdapter
                                    )
                                    popularCompetitionByCountryAdapter!!.notifyDataSetChanged()
                                }
                                binding.tvCompetitionsByCountry.visibility = View.GONE
                            }

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

    override fun clickSportItem(id: String, icon: String) {
        sportId = id
        playerImage = icon

        if (arrSports != null && arrSports!!.isNotEmpty()) {
            for (i in arrSports!!.indices) {
                arrSports!![i].isSelect = arrSports!![i].id == sportId
            }
            mSportAdapter.setSportIdData(arrSports!!)
        }
        val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId, sportId!!, chooseDate!!)
        viewModel.getDashboardFilter(body)
    }

    override fun clickDateItem(date: String) {
        chooseDate = date

        if (arrDate != null && arrDate!!.isNotEmpty()) {
            for (i in arrDate!!.indices) {
                arrDate!![i].isSelect = arrDate!![i].date_value == chooseDate
            }
            mDateWiseAdapter.setData(arrDate!!, chooseDate)
        }

        val body = RequestBodies.DashboardBody(PreferenceHelper.deviceId, sportId!!, chooseDate!!)
        viewModel.getDashboardFilter(body)
    }
}