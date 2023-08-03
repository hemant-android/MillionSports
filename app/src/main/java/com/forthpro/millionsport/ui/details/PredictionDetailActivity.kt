package com.forthpro.millionsport.ui.details

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.databinding.ActivityDetailBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.PredictionDetailResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.details.adapter.detail.SoccerPredictionAdapter
import com.forthpro.millionsport.ui.details.viewmodel.PredictionDetailViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory


class PredictionDetailActivity : BaseActivity(), SoccerPredictionAdapter.onClickListner {

    private lateinit var viewModel: PredictionDetailViewModel
    private lateinit var binding: ActivityDetailBinding

    var sportId: String? = ""
    var predictionId: String? = ""
    var playerImage: String? = ""
    var homeTeam: String? = ""
    var awayTeam: String? = ""
    var homeFavourite: String? = ""
    var awayFavourite: String? = ""
    var countryId: String? = ""

    private var arrPrediction: ArrayList<PredictionDetailResponse.PredictionTab>? = arrayListOf()

    private val mAdapter: SoccerPredictionAdapter by lazy { SoccerPredictionAdapter(this) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        val bundle = intent.extras
        if (bundle != null) {
            sportId = bundle.getString("sportId")
            predictionId = bundle.getString("predictionId")
        }

        when (sportId) {
            "1" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_soccer)
            }

            "2" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_hockey)
            }

            "3" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_basketball)
            }

            "4" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_handball)
            }

            "5" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_futsal)
            }

            "6" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_volleyball)
            }

            "7" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_rugby_league)
            }

            "8" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_rugby_union)
            }

            "9" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_tennis)

                binding.imgFavHome.visibility = View.GONE
                binding.imgFavAway.visibility = View.GONE
            }

            "10" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_american_football)
            }

            "11" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_baseball)
            }

            "12" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.mipmap.ic_pespall)
            }

            else -> {
                binding.imgBackground.visibility = View.GONE
            }
        }


        /*if (countryFlag != null) {
            Glide.with(this)
                .load(BuildConfig.SERVER_URL + countryFlag)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(binding.imgFlag)
        }*/

        if (playerImage != null) {
            Glide.with(this)
                .load(BuildConfig.SERVER_URL + playerImage)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(binding.imgPlayer)
        }

        binding.rvSoccerPredication.adapter = mAdapter
        mAdapter.setClickListner(this)

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.imgFavHome.setOnClickListener {

            if (homeFavourite == "1") {
                viewModel.favAddRemoveData(
                    RequestBodies.FavAddRemoveBody(
                        PreferenceHelper.deviceId,
                        sportId.toString(),
                        countryId!!,
                        homeTeam!!,
                        "2"
                    )
                )
            } else {
                viewModel.favAddRemoveData(
                    RequestBodies.FavAddRemoveBody(
                        PreferenceHelper.deviceId,
                        sportId.toString(),
                        countryId!!,
                        homeTeam!!,
                        "1"
                    )
                )
            }

            viewModel.favAddRemoveResponse.observe(this) { event ->
                event?.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            if (response.data?.status == 1) {

                                val body = RequestBodies.PredictionDetailsBody(
                                    PreferenceHelper.deviceId,
                                    sportId!!,
                                    predictionId!!
                                )
                                viewModel.getPredictionDetailList(body, sportId!!)

                            } else {
                                Toast.makeText(
                                    this,
                                    "Data not found",
                                    Toast.LENGTH_SHORT
                                )
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

        binding.imgFavAway.setOnClickListener {
            if (awayFavourite == "1") {
                viewModel.favAddRemoveData(
                    RequestBodies.FavAddRemoveBody(
                        PreferenceHelper.deviceId,
                        sportId.toString(),
                        countryId!!,
                        awayTeam!!,
                        "2"
                    )
                )
            } else {
                viewModel.favAddRemoveData(
                    RequestBodies.FavAddRemoveBody(
                        PreferenceHelper.deviceId,
                        sportId.toString(),
                        countryId!!,
                        awayTeam!!,
                        "1"
                    )
                )
            }

            viewModel.favAddRemoveResponse.observe(this) { event ->
                event?.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            if (response.data?.status == 1) {

                                val body = RequestBodies.PredictionDetailsBody(
                                    PreferenceHelper.deviceId,
                                    sportId!!,
                                    predictionId!!
                                )
                                viewModel.getPredictionDetailList(body, sportId!!)

                            } else {
                                Toast.makeText(
                                    this,
                                    "Data not found",
                                    Toast.LENGTH_SHORT
                                )
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

        val body = RequestBodies.PredictionDetailsBody(
            PreferenceHelper.deviceId,
            sportId!!,
            predictionId!!
        )
        viewModel.getPredictionDetailList(body, sportId!!)

        viewModel.getDetailResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvCountryName.text = response.data?.prediction_name

                            homeTeam = response.data?.homeTeam
                            awayTeam = response.data?.awayTeam
                            countryId = response.data?.country_id
                            homeFavourite = response.data?.homeFavourite
                            awayFavourite = response.data?.awayFavourite


                            if (sportId == "9") {
                                if (response.data?.player == "1") {
                                    binding.tvHome.text = response.data?.Player_H_1
                                    binding.tvAway.text = response.data?.Player_A_1
                                } else {
                                    binding.tvHome.text = response.data?.Player_H_1+"\n"+response.data?.Player_H_2
                                    binding.tvAway.text = response.data?.Player_A_1+"\n"+response.data?.Player_A_2
                                }

                            } else {
                                binding.tvHome.text = response.data?.homeTeam
                                binding.tvAway.text = response.data?.awayTeam
                            }
                            if (response.data?.homeFavourite == "1") {
                                binding.imgFavHome.setImageResource(R.drawable.ic_fav_select)
                            } else {
                                binding.imgFavHome.setImageResource(R.drawable.ic_fav_un_select)
                            }

                            if (response.data?.awayFavourite == "1") {
                                binding.imgFavAway.setImageResource(R.drawable.ic_fav_select)
                            } else {
                                binding.imgFavAway.setImageResource(R.drawable.ic_fav_un_select)
                            }

                            Glide.with(this)
                                .load(BuildConfig.SERVER_URL + response.data?.country_logo)
                                .centerCrop()
                                .placeholder(R.drawable.progress_animation)
                                .into(binding.imgFlag)

                            var date = response.data?.prediction_date
                            var time = response.data?.prediction_time

                            var timing = Utils.convertPredictionTimeCurrentTimeZone(
                                "$date $time",
                                PreferenceHelper.timeFormat
                            )

                            if (timing!!.contains(" ")) {
                                var time = timing.split(" ")[0]
                                var ampm = timing.split(" ")[1]

                                binding.tvTime.text = time + "\n" + ampm.uppercase()
                            } else {
                                binding.tvTime.text = Utils.convertPredictionTimeCurrentTimeZone(
                                    "$date $time",
                                    PreferenceHelper.timeFormat
                                )
                            }

                            if (arrPrediction != null && arrPrediction!!.size > 0) {
                                arrPrediction!!.clear()
                            }

                            var sets = response.data?.sets

                            if (response.data?.predictionTab != null && response.data?.predictionTab.isNotEmpty()) {
                                arrPrediction = response.data?.predictionTab
                                mAdapter.setData(arrPrediction!!, sportId!!, sets)
                            } else {
                                if (mAdapter != null) {
                                    if (arrPrediction != null && arrPrediction!!.size > 0) {
                                        arrPrediction!!.clear()
                                    }
                                    mAdapter.setData(arrPrediction!!, sportId!!, sets)
                                    mAdapter.notifyDataSetChanged()
                                }
                            }
                        } else {

                            Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show()
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

                    else -> {}
                }
            }
        }
    }

    override fun clickHeader(position: Int) {
        if (arrPrediction != null && arrPrediction!!.size > 0) {
            arrPrediction!![position].isSelect = !arrPrediction!![position].isSelect
            mAdapter.notifyDataSetChanged()
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
        viewModel = ViewModelProvider(this, factory)[PredictionDetailViewModel::class.java]

    }
}