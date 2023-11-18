package com.milione.ui.timing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import app.milionesports.de.R
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivityChooseTimeFormatBinding
import com.milione.repository.AppRepository
import com.milione.ui.home.HomeActivity
import com.milione.ui.timing.viewmodel.TimeFormatViewModel
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory

class ChooseTimeFormatActivity : AppCompatActivity() {
    private lateinit var viewModel: TimeFormatViewModel
    lateinit var binding: ActivityChooseTimeFormatBinding

    private var selectTimeFormat = ""

    var selectTimeFormat24 = ""
    var selectTimeFormat12 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseTimeFormatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        viewModel.getAllTimeFormatList()

        viewModel.getAllTimeFormatResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            binding.tvTitle.text = response.data?.label1
                            binding.tv24.text = response.data?.label2
                            binding.tv12.text = response.data?.label3

                            selectTimeFormat24 = response.data?.label21.toString()
                            selectTimeFormat12 = response.data?.label31.toString()

//                            selectTimeFormat = selectTimeFormat24


                        } else {
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
                PreferenceHelper.loggedIn = true
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
                PreferenceHelper.loggedIn = true
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
        viewModel = ViewModelProvider(this, factory)[TimeFormatViewModel::class.java]

    }
}