package com.milione.ui.language

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.milione.BaseActivity
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivityLanguageChooseBinding
import com.milione.model.RequestBodies
import com.milione.model.response.GetAllLanguageResponse
import com.milione.repository.AppRepository
import com.milione.ui.language.adapter.LanguageChooseAdapter
import com.milione.ui.language.viewmodel.LanguageChooseViewModel
import com.milione.ui.timing.ChooseTimeFormatActivity
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.TimeZone


class LanguageChooseActivity : BaseActivity(), LanguageChooseAdapter.onClickListner {

    private lateinit var viewModel: LanguageChooseViewModel
    private lateinit var binding: ActivityLanguageChooseBinding

    private var allLanguageList: ArrayList<GetAllLanguageResponse.Language>? = arrayListOf()
    private val adapter: LanguageChooseAdapter by lazy { LanguageChooseAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PreferenceHelper.languageHeader = ""

        setupViewModel()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            requestPermissionsAbove12()
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
                                response.data?.defaultLanguage!!
                            )
                            viewModel.getLanguageLabel(body)

                            if (allLanguageList != null && allLanguageList!!.size > 0) {
                                allLanguageList!!.clear()
                            }
                            if (response.data?.language != null && response.data?.language.isNotEmpty()) {
                                var allLanguageList = response.data?.language

                                for (items in allLanguageList!!) {
                                    items.isSelect = items.id == response.data?.defaultLanguage
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
                            Toast.makeText(this, response.data?.message, Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        getFCMToken()
    }

    override fun clickItem(languageId: String) {

        PreferenceHelper.languageHeader = languageId

        var tz = TimeZone.getDefault()
        var timeZone = tz.getDisplayName(true, TimeZone.SHORT)

        val body = RequestBodies.ChangeLanguageBody(
            "Android",
            PreferenceHelper.deviceId,
            PreferenceHelper.deviceToken, timeZone, tz.id
        )
        viewModel.changeLanguage(body)

        Intent(this, ChooseTimeFormatActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            if (token != null && !TextUtils.isEmpty(token)) {
                PreferenceHelper.deviceToken = token!!
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
        viewModel = ViewModelProvider(this, factory)[LanguageChooseViewModel::class.java]
    }

    private fun requestPermissionsAbove12() {
        Dexter.withActivity(this) // below line is use to request the number of permissions which are required in our app.
            .withPermissions(Manifest.permission.POST_NOTIFICATIONS) // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now
                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permanently, we will show user a dialog message.
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken,
                ) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? ->
                // we are displaying a toast message for error message.
            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }
}