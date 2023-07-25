package com.forthpro.millionsport.ui.favourite.fragment.team

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
import com.forthpro.millionsport.databinding.FragmentTeamBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.TeamResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.favourite.GetTeamListActivity
import com.forthpro.millionsport.ui.favourite.fragment.team.adapter.TeamAdapter
import com.forthpro.millionsport.ui.favourite.fragment.team.viewmodel.TeamViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class TeamFragment(val sportId: String?, var chooseDate: String?) : Fragment(), TeamAdapter.onClickListner {
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

        binding.tvAddTeam.setOnClickListener {
            Intent(requireActivity(), GetTeamListActivity::class.java).also {
                it.putExtra("sportId", sportId.toString())
                it.putExtra("chooseDate", chooseDate)
                startActivityForResult(it,101)
            }
        }

        binding.rvTeam.adapter = adapter
        adapter.setClickListner(this)

        viewModel.getFavResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvAddTeam.text = response.data?.ADDTEAMS_LABEL
                            if (arrMatches != null && arrMatches!!.isNotEmpty()) {
                                arrMatches!!.clear()
                            }

                            if (response.data?.favTeams != null && response.data?.favTeams.isNotEmpty()) {
                                binding.rvTeam.visibility = View.VISIBLE
                                binding.llNoRecord.visibility = View.GONE

                                arrMatches = response.data.favTeams

                                adapter.setData(arrMatches!!)
                            } else {
                                binding.rvTeam.visibility = View.GONE
                                binding.llNoRecord.visibility = View.VISIBLE
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

    internal fun callTeamDetail(sportId: String, choose_Date: String) {
        chooseDate = choose_Date
        val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sportId, choose_Date)
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

    override fun clickFavUnFav(
        position: Int,
        sportId: Int,
        country_id: Int,
        team_name: String,
        favourite: Int,
    ) {
        viewModel.favAddRemoveData(
            RequestBodies.FavAddRemoveBody(
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