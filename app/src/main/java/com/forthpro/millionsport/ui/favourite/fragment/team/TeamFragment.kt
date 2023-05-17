package com.forthpro.millionsport.ui.favourite.fragment.team

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.forthpro.millionsport.databinding.FragmentTeamBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.TeamResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.favourite.fragment.team.adapter.TeamAdapter
import com.forthpro.millionsport.ui.favourite.fragment.team.viewmodel.TeamViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class TeamFragment(val sportId: String?, val chooseDate: String?) : Fragment() {
    private lateinit var binding: FragmentTeamBinding

    private lateinit var viewModel: TeamViewModel

    private val adapter: TeamAdapter by lazy { TeamAdapter(requireActivity()) }

    private var arrMatches: ArrayList<TeamResponse.FavTeam>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        binding.rvTeam.adapter = adapter

        val body = RequestBodies.FavBody("123456", sportId!!, chooseDate!!)

        viewModel.getFav(body)

        viewModel.getFavResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            if (arrMatches != null && arrMatches!!.isNotEmpty()) {
                                arrMatches!!.clear()
                            }

                            if (response.data?.favTeams != null && response.data?.favTeams.isNotEmpty()) {
                                binding.llNoRecord.visibility = View.GONE

                                arrMatches = response.data.favTeams

                                adapter.setData(arrMatches!!)
                            } else {
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

    internal fun callMatchDetail(sportId: String, chooseDate: String) {
        val body = RequestBodies.FavBody("123456", sportId, chooseDate)
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
        viewModel = ViewModelProvider(this, factory)[TeamViewModel::class.java]
    }
}