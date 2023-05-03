package com.forthpro.millionsport.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityDetailBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.PredictionDetailResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.details.adapter.detail.SoccerPredictionAdapter
import com.forthpro.millionsport.ui.details.viewmodel.PredictionDetailViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory


class PredictionDetailActivity : BaseActivity(), SoccerPredictionAdapter.onClickListner {

    private lateinit var viewModel: PredictionDetailViewModel
    private lateinit var binding: ActivityDetailBinding

    var sportId: String? = ""
    var predictionId: String? = ""
    var home: String? = ""
    var away: String? = ""
    var playerImage: String? = ""
    var countryName: String? = ""
    var countryFlag: String? = ""
    var time: String? = ""

    private var arrPrediction: ArrayList<PredictionDetailResponse.PredictionTab>? = arrayListOf()

    private val mAdapter: SoccerPredictionAdapter by lazy { SoccerPredictionAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        val bundle = intent.extras
        if (bundle != null) {
            sportId = bundle.getString("sportId")
            predictionId = bundle.getString("predictionId")
            home = bundle.getString("home")
            away = bundle.getString("away")
            playerImage = bundle.getString("playerImage")
            time = bundle.getString("time")
            countryName = bundle.getString("countryName")
            countryFlag = bundle.getString("countryFlag")
        }

        binding.tvHome.text = home
        binding.tvAway.text = away
        binding.tvTime.text = time
        binding.tvCountryName.text = countryName

        when (sportId) {
            "1" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.drawable.soccer)
            }
            "4" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.drawable.handball)
            }
            "10" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.drawable.american_football)
            }
            "11" -> {
                binding.imgBackground.visibility = View.VISIBLE
                binding.imgBackground.setImageResource(R.drawable.baseball)
            }
            else -> {
                binding.imgBackground.visibility = View.GONE
            }
        }


        if (countryFlag != null) {
            Glide.with(this)
                .load(BuildConfig.SERVER_URL + countryFlag)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(binding.imgFlag)
        }

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

        val body = RequestBodies.PredictionDetailsBody(sportId!!, predictionId!!)
        viewModel.getPredictionDetailList(body, sportId!!)

        viewModel.getDetailResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            if (arrPrediction != null && arrPrediction!!.size > 0) {
                                arrPrediction!!.clear()
                            }
                            if (response.data?.predictionTab != null && response.data?.predictionTab.isNotEmpty()) {
                                arrPrediction = response.data?.predictionTab
                                mAdapter.setData(arrPrediction!!, sportId!!)
                            } else {
                                if (mAdapter != null) {
                                    if (arrPrediction != null && arrPrediction!!.size > 0) {
                                        arrPrediction!!.clear()
                                    }
                                    mAdapter.setData(arrPrediction!!, sportId!!)
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