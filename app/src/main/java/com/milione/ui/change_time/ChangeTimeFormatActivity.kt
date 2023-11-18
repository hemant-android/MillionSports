package com.milione.ui.change_time

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.milione.BaseActivity
import app.milionesports.de.R
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivityChangeTimeFormatBinding
import com.milione.repository.AppRepository
import com.milione.ui.change_time.viewmodel.ChangeTimeFormatViewModel
import com.milione.ui.home.HomeActivity
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory

class ChangeTimeFormatActivity : BaseActivity() {
    private lateinit var viewModel: ChangeTimeFormatViewModel
    lateinit var binding: ActivityChangeTimeFormatBinding

    private var selectTimeFormat = ""

    var selectTimeFormat24 = ""
    var selectTimeFormat12 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeTimeFormatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.imgBack.setOnClickListener {
            finish()
        }

        viewModel.getAllTimeFormatList()

        viewModel.getAllTimeFormatResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.llLeader.visibility = View.VISIBLE
                            binding.tvTitle.text = response.data?.label1
                            binding.tv24.text = response.data?.label2
                            binding.tv12.text = response.data?.label3

                            selectTimeFormat24 = response.data?.label21.toString()
                            selectTimeFormat12 = response.data?.label31.toString()

                            if (PreferenceHelper.timeFormat == "2") {
                                binding.imgCheck24.setImageResource(R.drawable.ic_radio_button_checked)
                                binding.imgCheck12.setImageResource(R.drawable.ic_radio_button_unchecked)

                                selectTimeFormat = selectTimeFormat24

                                PreferenceHelper.timeFormat = selectTimeFormat24

                            } else {
                                binding.imgCheck12.setImageResource(R.drawable.ic_radio_button_checked)
                                binding.imgCheck24.setImageResource(R.drawable.ic_radio_button_unchecked)

                                selectTimeFormat = selectTimeFormat12

                                PreferenceHelper.timeFormat = selectTimeFormat12
                            }

                        } else {
                            binding.llLeader.visibility = View.GONE
                            Toast.makeText(
                                this,
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

        binding.rl24.setOnClickListener {
            binding.imgCheck24.setImageResource(R.drawable.ic_radio_button_checked)
            binding.imgCheck12.setImageResource(R.drawable.ic_radio_button_unchecked)

            selectTimeFormat = selectTimeFormat24

            PreferenceHelper.timeFormat = selectTimeFormat24

            Intent(this, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        binding.rl12.setOnClickListener {
            binding.imgCheck12.setImageResource(R.drawable.ic_radio_button_checked)
            binding.imgCheck24.setImageResource(R.drawable.ic_radio_button_unchecked)

            selectTimeFormat = selectTimeFormat12

            PreferenceHelper.timeFormat = selectTimeFormat12

            Intent(this, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
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
        viewModel = ViewModelProvider(this, factory)[ChangeTimeFormatViewModel::class.java]

    }
}