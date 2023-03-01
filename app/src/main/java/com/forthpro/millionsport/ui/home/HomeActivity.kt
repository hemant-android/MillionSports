package com.forthpro.millionsport.ui.home

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

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

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        binding.imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.imgFav.setOnClickListener {
        }

        val headerLayout: View = navView.inflateHeaderView(R.layout.app_bar_menu)

        val rlNotification: RelativeLayout = (headerLayout).findViewById(R.id.rlNotification)
        val rlFav: RelativeLayout = (headerLayout).findViewById(R.id.rlFav)
        val rlTranslate: RelativeLayout = (headerLayout).findViewById(R.id.rlTranslate)
        val rlTime: RelativeLayout = (headerLayout).findViewById(R.id.rlTime)
        val rlFeedback: RelativeLayout = (headerLayout).findViewById(R.id.rlFeedback)
        val rlShare: RelativeLayout = (headerLayout).findViewById(R.id.rlShare)
        val rlRateApp: RelativeLayout = (headerLayout).findViewById(R.id.rlRateApp)
        val rlTermUse: RelativeLayout = (headerLayout).findViewById(R.id.rlTermUse)
        val rlPrivacy: RelativeLayout = (headerLayout).findViewById(R.id.rlPrivacy)

        rlNotification.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlFav.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlTranslate.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlTime.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlFeedback.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlShare.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlRateApp.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlTermUse.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        rlPrivacy.setOnClickListener {
            drawerLayout.closeDrawers()
        }
    }
}