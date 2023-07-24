package com.forthpro.millionsport.ui.favourite

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.databinding.ActivityGetTeamListBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.GetCompetitionListResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.favourite.adapter.GetCompetitionListAdapter
import com.forthpro.millionsport.ui.favourite.viewmodel.GetCompetitionListViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory
import com.google.android.material.imageview.ShapeableImageView


class GetCompetitionListActivity : BaseActivity(), GetCompetitionListAdapter.onClickListner {

    private lateinit var binding: ActivityGetTeamListBinding
    private lateinit var viewModel: GetCompetitionListViewModel
    private val adapter: GetCompetitionListAdapter by lazy { GetCompetitionListAdapter(this) }

    private var allTeams: ArrayList<GetCompetitionListResponse.AllCompetiontion>? = arrayListOf()

    private var label: String? = ""
    private var yes: String? = ""
    private var no: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetTeamListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.rvTeamList.adapter = adapter
        adapter.setClickListner(this)

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                localSearching(s.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        viewModel.getTeamListData(RequestBodies.GetTeamListBody(PreferenceHelper.deviceId, "1"))

        viewModel.getTeamListResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            if (allTeams != null && allTeams!!.isNotEmpty()) {
                                allTeams!!.clear()
                            }

                            binding.tvMatchName.text = response.data?.POPULAR_LABEL
                            binding.edtSearch.hint = response.data?.SEARCH_LABEL
                            label = response.data?.REMOVE_LABEL
                            yes = response.data?.YES_LABEL
                            no = response.data?.NO_LABEL

                            if (response.data?.allCompetiontion != null && response.data?.allCompetiontion.isNotEmpty()) {

                                allTeams = response.data.allCompetiontion

                                adapter.setData(allTeams!!)
                            }

                        } else {
                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT)
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

    private fun hideProgressBar() {
        binding.progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(this.application, repository)
        viewModel = ViewModelProvider(this, factory)[GetCompetitionListViewModel::class.java]
    }

    override fun clickFavUnFav(
        position: Int,
        sportId: Int,
        country_id: Int,
        team_name: String,
        fav: Int,
        flag: String
    ) {

        val dialog = Dialog(this)
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
        tvLabel.text = label
        tvYes.text = yes
        tvNo.text = no

        tvNo.setOnClickListener {
            dialog.dismiss()
        }

        tvYes.setOnClickListener {

            dialog.dismiss()

            var favourite: Int = if (fav == 0) {
                1
            } else {
                2
            }

            if (allTeams!![position].favourite == 0) {
                allTeams!![position].favourite = 1
            } else {
                allTeams!![position].favourite = 0
            }
            adapter.notifyDataSetChanged()

            viewModel.favAddRemoveData(
                RequestBodies.FavAddRemoveCompetitionBody(
                    PreferenceHelper.deviceId,
                    sportId.toString(),
                    country_id.toString(),
                    team_name,
                    favourite.toString()
                )
            )

            viewModel.favAddRemoveResponse.observe(this) { event ->
                event?.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            if (response.data?.status == 1) {
                                /*if (allTeams!![position].favourite == 0) {
                                    allTeams!![position].favourite = 1
                                } else {
                                    allTeams!![position].favourite = 0
                                }
                                adapter.notifyDataSetChanged()*/
//                            viewModel.getTeamListData(RequestBodies.GetTeamListBody(PreferenceHelper.deviceId, "1"))
                            } else {
                                Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT)
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
    }

    fun localSearching(text: String) {
        if (text!!.isNotEmpty()) {
            filter(text)
        } else {
            if (allTeams!!.isNotEmpty()) {
                adapter.setData(allTeams!!)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun filter(text: String) {
        val temp: MutableList<GetCompetitionListResponse.AllCompetiontion> =
            ArrayList()
        if (allTeams!!.isNotEmpty()) {
            for (d in allTeams!!) {
                if (d.name!!.lowercase().contains(text.lowercase())) {
                    temp!!.add(d)
                }
            }
            adapter.setData(temp as ArrayList<GetCompetitionListResponse.AllCompetiontion>)
            adapter.notifyDataSetChanged()
        }
    }
}