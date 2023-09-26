package com.forthpro.millionsport.ui.favourite.fragment.team

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
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
import com.google.android.material.imageview.ShapeableImageView

class TeamFragment(var sportId: String?, var chooseDate: String?) : Fragment(),
    TeamAdapter.onClickListner {
    private lateinit var binding: FragmentTeamBinding

    private lateinit var viewModel: TeamViewModel

    private val adapter: TeamAdapter by lazy { TeamAdapter(requireActivity()) }

    private var arrMatches: ArrayList<TeamResponse.FavTeam>? = arrayListOf()

    private var label: String? = ""
    private var yes: String? = ""
    private var no: String? = ""

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
                startActivityForResult(it, 101)
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

                            label = response.data?.REMOVE_LABEL
                            yes = response.data?.YES_LABEL
                            no = response.data?.NO_LABEL

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
                                binding.llNoRecord.visibility = View.GONE
                                binding.tvNoRecord.text = response.data?.message
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
    }

    internal fun callTeamDetail(sport_Id: String, choose_Date: String) {
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
        viewModel = ViewModelProvider(this, factory)[TeamViewModel::class.java]
    }

    override fun clickFavUnFav(
        position: Int,
        sportId: Int,
        country_id: Int,
        team_name: String,
        favourite: Int,
        flag: String,
    ) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_confirmation)

        val window: Window = dialog.window!!
        val wlp = window.attributes
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp.gravity = Gravity.CENTER
        window.attributes = wlp
        dialog.show()


        val imgFlag = dialog.findViewById(R.id.imgFlag) as ShapeableImageView
        val tvLeagueName = dialog.findViewById(R.id.tvLeagueName) as TextView
        val tvLabel = dialog.findViewById(R.id.tvLabel) as TextView
        val tvYes = dialog.findViewById(R.id.tvYes) as TextView
        val tvNo = dialog.findViewById(R.id.tvNo) as TextView

        if (flag != null && !TextUtils.isEmpty(flag)) {
            Glide.with(this)
                .load(BuildConfig.SERVER_URL + flag)
                .placeholder(R.drawable.progress_animation)
                .into(imgFlag)
        }

        tvLeagueName.text = team_name

        if (label != null && !TextUtils.isEmpty(label)) {
            tvLabel.text = label
        } else {
            tvLabel.text = "Remove from your favorites?"
        }
        if (yes != null && !TextUtils.isEmpty(yes)) {
            tvYes.text = yes
        } else {
            tvYes.text = "Yes"
        }
        if (no != null && !TextUtils.isEmpty(no)) {
            tvNo.text = no
        } else {
            tvNo.text = "NO"
        }




        tvNo.setOnClickListener {
            dialog.dismiss()
        }
        tvYes.setOnClickListener {
            dialog.dismiss()

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

                                PreferenceHelper.isFav = true

                                val body = RequestBodies.FavBody(
                                    PreferenceHelper.deviceId,
                                    sportId!!.toString(),
                                    chooseDate!!
                                )
                                viewModel.getFav(body)

                            } else {
//                                Toast.makeText(requireActivity(),"Data not found",Toast.LENGTH_SHORT).show()
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    101 -> {
                        var chooseDate = data!!.getStringExtra("chooseDate")
                        val body = RequestBodies.FavBody(
                            PreferenceHelper.deviceId,
                            sportId!!,
                            chooseDate!!
                        )
                        viewModel.getFav(body)
                    }
                }
            }
        }
    }
}