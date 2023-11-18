package com.milione.ui.favourite.fragment.match

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.FragmentMatchBinding
import com.milione.model.RequestBodies
import com.milione.model.response.MatchResponse
import com.milione.repository.AppRepository
import com.milione.ui.favourite.fragment.match.adapter.MatchAdapter
import com.milione.ui.favourite.fragment.match.viewmodel.MatchViewModel
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory

class MatchFragment(val sportId: String?, val chooseDate: String?) : Fragment() {
    private lateinit var binding: FragmentMatchBinding
    private lateinit var viewModel: MatchViewModel

    private val adapter: MatchAdapter by lazy { MatchAdapter(requireActivity()) }

    private var arrMatches: ArrayList<MatchResponse.Matche>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMatchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        binding.rvMatch.adapter = adapter

        viewModel.getFavResponse.observe(requireActivity()) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            if (arrMatches != null && arrMatches!!.isNotEmpty()) {
                                arrMatches!!.clear()
                            }

                            if (response.data?.matches != null && response.data?.matches.isNotEmpty()) {
                                binding.llNoRecord.visibility = View.GONE

                                arrMatches = response.data.matches

                                adapter.setData(arrMatches!!)
                            } else {
                                binding.llNoRecord.visibility = View.VISIBLE
                                binding.tvNoRecord.text =response.data?.message
                            }

                        } else {
//                            Toast.makeText(requireActivity(), "Data not found", Toast.LENGTH_SHORT).show()
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
//        val body = RequestBodies.FavBody(PreferenceHelper.deviceId, sportId!!, chooseDate!!)
//        viewModel.getFav(body)
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
        viewModel = ViewModelProvider(this, factory)[MatchViewModel::class.java]
    }
}