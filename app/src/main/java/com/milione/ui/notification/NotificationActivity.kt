package com.milione.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.milione.BaseActivity
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivityNotificationBinding
import com.milione.model.RequestBodies
import com.milione.model.response.NotificationResponse
import com.milione.repository.AppRepository
import com.milione.ui.notification.adapter.NotificationAdapter
import com.milione.ui.notification.viewmodel.NotificationViewModel
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory

class NotificationActivity : BaseActivity(), NotificationAdapter.onClickListner {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var viewModel: NotificationViewModel
    private val adapter: NotificationAdapter by lazy { NotificationAdapter(this) }

    private var arrNotification: ArrayList<NotificationResponse.NotificationArray>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.rvNotification.adapter = adapter
        adapter.setClickListner(this)

        binding.imgBack.setOnClickListener {
            finish()
        }

        val body = RequestBodies.GetNotificationBody(PreferenceHelper.deviceId)
        viewModel.getNotificationItem(body)


        viewModel.getNotificationResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()

                        if (response.data?.status == 1 && response.data?.notification_array?.size!! > 0) {

                            if (arrNotification != null && arrNotification!!.size > 0) {
                                arrNotification!!.clear()
                            }

                            binding.tvTitle.text = response.data?.label_name

                            arrNotification = response.data?.notification_array

                            adapter.setData(response.data?.notification_array)

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
        viewModel = ViewModelProvider(this, factory)[NotificationViewModel::class.java]
    }

    override fun clickItem(position: Int, notificationId: Int, isToggle: Int) {
        var notification: Int = if (isToggle == 1) {
            2
        } else {
            1
        }

        val body = RequestBodies.UpdatedNotificationBody(
            PreferenceHelper.deviceId,
            notificationId.toString(), notification!!.toString()
        )
        viewModel.updateNotificationItem(body)


        viewModel.updateNotificationResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()

                        if (response.data?.status == 1) {
                            val body = RequestBodies.GetNotificationBody(PreferenceHelper.deviceId)
                            viewModel.getNotificationItem(body)

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
    }
}