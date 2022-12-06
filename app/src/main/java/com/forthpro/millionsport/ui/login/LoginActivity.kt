package com.forthpro.millionsport.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.demoapp.ui.viewmodel.LoginViewModel
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.databinding.ActivityLoginBinding
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.tvEntry.setOnClickListener(View.OnClickListener {
            val body = RequestBodies.LoginBody("456112", "14", "15")
            viewModel.loginDetail(body)

            viewModel.loginResponse.observe(this) { event ->
                event?.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            if (response.data?.status!!) {
                                Toast.makeText(
                                    this,
                                    response.data?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    response.data?.message,
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
        })
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
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

    }
}