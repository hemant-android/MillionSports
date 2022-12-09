package com.forthpro.millionsport.ui.timing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityChooseTimeFormatBinding
import com.forthpro.millionsport.ui.home.HomeActivity

class ChooseTimeFormatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChooseTimeFormatBinding

    var selectTimeFormat = "24"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseTimeFormatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rl24.setOnClickListener {
            binding.imgCheck24.setImageResource(R.drawable.ic_radio_button_checked)
            binding.imgCheck12.setImageResource(R.drawable.ic_radio_button_unchecked)

            selectTimeFormat = "24"

            Intent(this, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        binding.rl12.setOnClickListener {
            binding.imgCheck12.setImageResource(R.drawable.ic_radio_button_checked)
            binding.imgCheck24.setImageResource(R.drawable.ic_radio_button_unchecked)

            selectTimeFormat = "12"

            Intent(this, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}