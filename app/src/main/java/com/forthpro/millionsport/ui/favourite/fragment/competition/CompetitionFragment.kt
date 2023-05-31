package com.forthpro.millionsport.ui.favourite.fragment.competition

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

class CompetitionFragment(val sportId: String?, val chooseDate: String?) : Fragment() {
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

        binding.tvAddTeam.setOnClickListener {
            Intent(requireActivity(), GetCompetitionListActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.rvCompetition.adapter = adapter

        viewModel.getFavResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

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
                                binding.llNoRecord.visibility = View.VISIBLE
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
        val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sportId!!, chooseDate!!)
        viewModel.getFav(body)
    }

    internal fun callMatchDetail(sportId: String, chooseDate: String) {
        val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sportId, chooseDate)
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
}
