package com.forthpro.millionsport.ui.favourite.fragment.competition

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.databinding.FragmentCompetitionBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CompetitionResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.favourite.GetCompetitionListActivity
import com.forthpro.millionsport.ui.favourite.GetTeamListActivity
import com.forthpro.millionsport.ui.favourite.fragment.competition.adapter.CompetitionAdapter
import com.forthpro.millionsport.ui.favourite.fragment.competition.viewmodel.CompetitionViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class CompetitionFragment(var sportId: String?, var chooseDate: String?) : Fragment(), CompetitionAdapter.onClickListner {
    private lateinit var binding: FragmentCompetitionBinding

    private lateinit var viewModel: CompetitionViewModel

    private val adapter: CompetitionAdapter by lazy { CompetitionAdapter(requireActivity()) }

    private var arrMatches: ArrayList<CompetitionResponse.Competition>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCompetitionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        binding.tvAddCompetition.setOnClickListener {
            Intent(requireActivity(), GetCompetitionListActivity::class.java).also {
                it.putExtra("sportId", sportId.toString())
                it.putExtra("chooseDate", chooseDate)
                startActivityForResult(it,101)
            }
        }

        binding.rvCompetition.adapter = adapter
        adapter.setClickListner(this)

        viewModel.getFavResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvAddCompetition.text = response.data?.ADD_COMPETITIONS_LABEL

                            if (arrMatches != null && arrMatches!!.isNotEmpty()) {
                                arrMatches!!.clear()
                            }

                            if (response.data?.competitions != null && response.data?.competitions.isNotEmpty()) {
                                binding.llNoRecord.visibility = View.GONE
                                binding.rvCompetition.visibility = View.VISIBLE

                                arrMatches = response.data.competitions

                                adapter.setData(arrMatches!!)
                            } else {
                                binding.rvCompetition.visibility = View.GONE
                                binding.llNoRecord.visibility = View.GONE

                                binding.tvNoRecord.text =response.data?.message
                            }

                        } else {
                            Toast.makeText(requireActivity(), "Data not found", Toast.LENGTH_SHORT)
                                .show()
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

    override fun onResume() {
        super.onResume()

    }

    internal fun callCompetitionDetail(sport_Id: String, choose_Date: String) {
        chooseDate = choose_Date
        sportId = sport_Id
        val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sport_Id, choose_Date)
        viewModel.getFav(body)
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
        viewModel = ViewModelProvider(this, factory)[CompetitionViewModel::class.java]
    }

    override fun clickFavUnFav(
        position: Int,
        sportId: Int,
        country_id: Int,
        team_name: String,
        favourite: Int,
    ) {
        viewModel.favAddRemoveData(
            RequestBodies.FavAddRemoveCompetitionBody(
                PreferenceHelper.deviceId,
                sportId.toString(),
                country_id.toString(),
                team_name,
                "2"
            )
        )

        viewModel.favAddRemoveResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sportId!!.toString(), chooseDate!!)
                            viewModel.getFav(body)

                        } else {
                            Toast.makeText(requireActivity(), "Data not found", Toast.LENGTH_SHORT)
                                .show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    101 -> {
                        var chooseDate =data!!.getStringExtra("chooseDate")
                        val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sportId!!, chooseDate!!)
                        viewModel.getFav(body)
                    }
                }
            }
        }
    }
}
