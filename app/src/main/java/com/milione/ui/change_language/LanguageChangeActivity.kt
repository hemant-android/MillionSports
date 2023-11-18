package com.milione.ui.change_language

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.milione.BaseActivity
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivityLanguageChangeBinding
import com.milione.model.RequestBodies
import com.milione.model.response.GetAllLanguageResponse
import com.milione.repository.AppRepository
import com.milione.ui.change_language.adapter.LanguageChangeAdapter
import com.milione.ui.change_language.viewmodel.LanguageChangeViewModel
import com.milione.ui.home.HomeActivity
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory
import java.util.TimeZone


class LanguageChangeActivity : BaseActivity(), LanguageChangeAdapter.onClickListner {

    private lateinit var viewModel: LanguageChangeViewModel
    private lateinit var binding: ActivityLanguageChangeBinding

    private var allLanguageList: ArrayList<GetAllLanguageResponse.Language>? = arrayListOf()
    private val adapter: LanguageChangeAdapter by lazy { LanguageChangeAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        PreferenceHelper.languageHeader = ""

        setupViewModel()

        binding.imgBack.setOnClickListener {
            finish()
        }

        viewModel.getAllLanguageList()

        viewModel.getAllLanguageResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            val body = RequestBodies.LanguageLabelBody(
                                "Select Language",
                                PreferenceHelper.languageHeader
                            )
                            viewModel.getLanguageLabel(body)

                            if (allLanguageList != null && allLanguageList!!.size > 0) {
                                allLanguageList!!.clear()
                            }
                            if (response.data?.language != null && response.data?.language.isNotEmpty()) {
                                allLanguageList = response.data?.language

                                for (items in allLanguageList!!) {
//                                    items.isSelect = items.id == response.data?.defaultLanguage
                                    if (items.id == PreferenceHelper.languageHeader) {
                                        items.isSelect = true
                                    }
                                }
                                adapter.setData(allLanguageList!!)


                            }

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

        viewModel.getLanguageTextResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {
                            binding.tvTitle.text = response.data?.showText
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

        binding.rvChooseLanguage.adapter = adapter
        adapter.setClickListner(this)
    }

    override fun clickItem(languageId: String) {

        PreferenceHelper.languageHeader = languageId

        var tz = TimeZone.getDefault()
        var timeZone = tz.getDisplayName(true, TimeZone.SHORT)

        val body = RequestBodies.ChangeLanguageBody(
            "Android",
            PreferenceHelper.deviceId,
            PreferenceHelper.deviceToken,
            timeZone,tz.id
        )
        viewModel.changeLanguage(body)

        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
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
        viewModel = ViewModelProvider(this, factory)[LanguageChangeViewModel::class.java]

    }
}