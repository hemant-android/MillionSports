package com.forthpro.millionsport.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityHomeBinding
import com.forthpro.millionsport.model.response.SideMenuResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.favourite.FavouriteActivity
import com.forthpro.millionsport.ui.home.viewmodel.HomeViewModel
import com.forthpro.millionsport.ui.notification.NotificationActivity
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory
import com.google.android.material.navigation.NavigationView

class HomeActivity : BaseActivity() {
    private var arrNotification: ArrayList<SideMenuResponse.SideBar.LabelArray>? = arrayListOf()

    lateinit var binding: ActivityHomeBinding
    private lateinit var navControllerMain: NavController
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment

        navControllerMain = navHostFragment.findNavController()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        binding.imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.imgFav.setOnClickListener {
            Intent(this, FavouriteActivity::class.java).also {
                startActivity(it)
            }
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

        rlTermUse.visibility = View.GONE
        rlPrivacy.visibility = View.GONE

        var tvLeagueName: TextView = rlNotification[1].findViewById(R.id.tvLeagueName)
        var tvFav: TextView = rlFav[1].findViewById(R.id.tvFav)
        var tvTranslate: TextView = rlTranslate[1].findViewById(R.id.tvTranslate)
        var tvTime: TextView = rlTime[1].findViewById(R.id.tvTime)
        var tvFeedback: TextView = rlFeedback[1].findViewById(R.id.tvFeedback)
        var tvShare: TextView = rlShare[1].findViewById(R.id.tvShare)
        var tvRateApp: TextView = rlRateApp[1].findViewById(R.id.tvRateApp)
        var tvTermUse: TextView = rlTermUse[1].findViewById(R.id.tvTermUse)
        var tvPrivacy: TextView = rlPrivacy[1].findViewById(R.id.tvPrivacy)

        rlNotification.setOnClickListener {
            drawerLayout.closeDrawers()

            Intent(this, NotificationActivity::class.java).also {
                it.putExtra("data", arrNotification)
                startActivity(it)
            }
        }

        rlFav.setOnClickListener {
            drawerLayout.closeDrawers()
            Intent(this, FavouriteActivity::class.java).also {
                startActivity(it)
            }
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

        viewModel.getSideMenuItem()

        viewModel.getSideMenuResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        if (response.data?.status == 1 && response.data?.sideBar?.size!! > 0) {


                            if (response.data?.sideBar[0].label_array?.size!! > 0) {
                                arrNotification = response.data?.sideBar[0].label_array
                            }

                            var tvLeagueName: TextView =
                                rlNotification[1].findViewById(R.id.tvLeagueName)
                            var tvFav: TextView = rlFav[1].findViewById(R.id.tvFav)
                            var tvTranslate: TextView =
                                rlTranslate[1].findViewById(R.id.tvTranslate)
                            var tvTime: TextView = rlTime[1].findViewById(R.id.tvTime)
                            var tvFeedback: TextView = rlFeedback[1].findViewById(R.id.tvFeedback)
                            var tvShare: TextView = rlShare[1].findViewById(R.id.tvShare)
                            var tvRateApp: TextView = rlRateApp[1].findViewById(R.id.tvRateApp)
                            var tvTermUse: TextView = rlTermUse[1].findViewById(R.id.tvTermUse)
                            var tvPrivacy: TextView = rlPrivacy[1].findViewById(R.id.tvPrivacy)

                            var first = response.data?.sideBar[0].label_name
                            var second = response.data?.sideBar[1].label_name
                            var third = response.data?.sideBar[2].label_name
                            var fourth = response.data?.sideBar[3].label_name
                            var five = response.data?.sideBar[4].label_name
                            var six = response.data?.sideBar[5].label_name
                            var seven = response.data?.sideBar[6].label_name

                            tvLeagueName.text = first
                            tvFav.text = second
                            tvTranslate.text = third
                            tvTime.text = fourth
                            tvFeedback.text = five
                            tvShare.text = six
                            tvRateApp.text = seven

                        } else {
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Log.e("error", message)
                        }
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(this.application, repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

    }
}