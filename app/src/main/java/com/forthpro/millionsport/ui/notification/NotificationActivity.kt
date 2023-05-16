package com.forthpro.millionsport.ui.notification

import android.os.Bundle
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    private var isPush: Boolean = true
    private var isSound: Boolean = true
    private var isVibration: Boolean = true
    private var isMute: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.togglePushNotification.setOnClickListener {
            if (isPush) {
                isPush = false
                binding.togglePushNotification.setImageResource(R.drawable.ic_off)
            } else {
                isPush = true
                binding.togglePushNotification.setImageResource(R.drawable.ic_on)
            }
        }
        binding.toggleNotificationSound.setOnClickListener {
            if (isSound) {
                isSound = false
                binding.toggleNotificationSound.setImageResource(R.drawable.ic_off)
            } else {
                isSound = true
                binding.toggleNotificationSound.setImageResource(R.drawable.ic_on)
            }
        }
        binding.toggleNotificationVibration.setOnClickListener {
            if (isVibration) {
                isVibration = false
                binding.toggleNotificationVibration.setImageResource(R.drawable.ic_off)
            } else {
                isVibration = true
                binding.toggleNotificationVibration.setImageResource(R.drawable.ic_on)
            }
        }
        binding.toggleNotificationMute.setOnClickListener {
            if (isMute) {
                isMute = false
                binding.toggleNotificationMute.setImageResource(R.drawable.ic_off)
            } else {
                isMute = true
                binding.toggleNotificationMute.setImageResource(R.drawable.ic_on)
            }
        }
    }
}