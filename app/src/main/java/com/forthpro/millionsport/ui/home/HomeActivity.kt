package com.forthpro.millionsport.ui.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var navControllerMain: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment

        navControllerMain = navHostFragment.findNavController()
    }
}