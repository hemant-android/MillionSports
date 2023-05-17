package com.forthpro.millionsport.ui.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.R
import com.forthpro.millionsport.databinding.ActivityFavouriteBinding
import com.forthpro.millionsport.model.response.FavouriteCommonResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.favourite.adapter.DateWiseFavAdapter
import com.forthpro.millionsport.ui.favourite.adapter.SportsFavAdapter
import com.forthpro.millionsport.ui.favourite.adapter.ViewPagerAdapter
import com.forthpro.millionsport.ui.favourite.fragment.competition.CompetitionFragment
import com.forthpro.millionsport.ui.favourite.fragment.match.MatchFragment
import com.forthpro.millionsport.ui.favourite.fragment.team.TeamFragment
import com.forthpro.millionsport.ui.favourite.viewmodel.FavouriteViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class FavouriteActivity : BaseActivity(), SportsFavAdapter.onClickListner,
    DateWiseFavAdapter.onClickListner {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel

    private var arrDate: ArrayList<FavouriteCommonResponse.DateArray>? = arrayListOf()
    private var arrSports: ArrayList<FavouriteCommonResponse.Sport>? = arrayListOf()

    private val mSportAdapter: SportsFavAdapter by lazy { SportsFavAdapter(this) }
    private val mDateWiseAdapter: DateWiseFavAdapter by lazy { DateWiseFavAdapter(this) }

    var adapter: ViewPagerAdapter? = null

    var playerImage: String? = ""
    var sportId: String? = ""
    var chooseDate: String? = ""

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        adapter = ViewPagerAdapter(supportFragmentManager, sportId, chooseDate)
        binding.viewPager.adapter = adapter!!

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.tvMatch.setOnClickListener {
            binding.tvMatch.typeface = ResourcesCompat.getFont(this, R.font.roboto_bold_700)
            binding.tvCompetition.typeface =ResourcesCompat.getFont(this, R.font.roboto_regular_400)
            binding.tvTeams.typeface = ResourcesCompat.getFont(this, R.font.roboto_regular_400)

            /*if (binding.viewPager.currentItem == 0) {
                val frag = (binding.viewPager.adapter as ViewPagerAdapter).instantiateItem(
                    binding.viewPager,
                    binding.viewPager.currentItem
                ) as MatchFragment
                frag.callMatchDetail(sportId!!, chooseDate!!)
            }*/

//            val frag = MatchFragment(sportId!!, chooseDate!!)
//            frag.callMatchDetail(sportId!!, chooseDate!!)

            binding.viewPager.currentItem = 0
        }

        binding.tvCompetition.setOnClickListener {
            binding.tvMatch.typeface = ResourcesCompat.getFont(this, R.font.roboto_regular_400)
            binding.tvCompetition.typeface = ResourcesCompat.getFont(this, R.font.roboto_bold_700)
            binding.tvTeams.typeface = ResourcesCompat.getFont(this, R.font.roboto_regular_400)

           /* if (binding.viewPager.currentItem == 1) {
                val frag = (binding.viewPager.adapter as ViewPagerAdapter).instantiateItem(
                    binding.viewPager,
                    binding.viewPager.currentItem
                ) as CompetitionFragment
            }*/

            binding.viewPager.currentItem = 1
        }

        binding.tvTeams.setOnClickListener {
            binding.tvMatch.typeface = ResourcesCompat.getFont(this, R.font.roboto_regular_400)
            binding.tvCompetition.typeface =
                ResourcesCompat.getFont(this, R.font.roboto_regular_400)
            binding.tvTeams.typeface = ResourcesCompat.getFont(this, R.font.roboto_bold_700)

//            val frag = TeamFragment(sportId!!, chooseDate!!)
//            frag.callMatchDetail(sportId!!, chooseDate!!)

            binding.viewPager.currentItem = 2

            /*if (binding.viewPager.currentItem == 2) {
                val frag = (binding.viewPager.adapter as ViewPagerAdapter).instantiateItem(
                    binding.viewPager,
                    binding.viewPager.currentItem
                ) as TeamFragment

                frag.callMatchDetail(sportId!!, chooseDate!!)
            }*/
        }

        binding.rvDateWise.adapter = mDateWiseAdapter
        mDateWiseAdapter.setClickListner(this)

        binding.viewPager.addOnAdapterChangeListener(object : OnPageChangeListener,
            ViewPager.OnAdapterChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (binding.viewPager.currentItem) {
                    0 -> {
                        val frag = (binding.viewPager.adapter as ViewPagerAdapter).instantiateItem(
                            binding.viewPager,
                            binding.viewPager.currentItem
                        ) as MatchFragment
                        frag.callMatchDetail(sportId!!, chooseDate!!)

                        binding.tvMatch.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_bold_700)
                        binding.tvCompetition.typeface =ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_regular_400)
                        binding.tvTeams.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_regular_400)
                    }

                    1 -> {
                        val frag = (binding.viewPager.adapter as ViewPagerAdapter).instantiateItem(
                            binding.viewPager,
                            binding.viewPager.currentItem
                        ) as CompetitionFragment

                        frag.callMatchDetail(sportId!!, chooseDate!!)

                        binding.tvMatch.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_regular_400)
                        binding.tvCompetition.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_bold_700)
                        binding.tvTeams.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_regular_400)
                    }

                    2 -> {
                        val frag = (binding.viewPager.adapter as ViewPagerAdapter).instantiateItem(
                            binding.viewPager,
                            binding.viewPager.currentItem
                        ) as TeamFragment

                        frag.callMatchDetail(sportId!!, chooseDate!!)

                        binding.tvMatch.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_regular_400)
                        binding.tvCompetition.typeface =
                            ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_regular_400)
                        binding.tvTeams.typeface = ResourcesCompat.getFont(this@FavouriteActivity, R.font.roboto_bold_700)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onAdapterChanged(
                viewPager: ViewPager,
                oldAdapter: PagerAdapter?,
                newAdapter: PagerAdapter?,
            ) {
            }
        })

        viewModel.getFav()

        viewModel.getFavResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data?.status == 1) {

                            if (response.data?.sports != null && response.data?.sports.isNotEmpty()) {
                                arrSports = response.data.sports

                                if (arrSports != null && arrSports!!.isNotEmpty()) {
                                    for (i in arrSports!!.indices) {
                                        if (i == 0) {
                                            playerImage = arrSports!![i].light_logo
                                        }
                                        arrSports!![i].isSelect = i == 0
                                    }
                                }
                                mSportAdapter.setSportIdData(sportId!!, arrSports!!)

                                binding.rvGame.adapter = mSportAdapter
                                mSportAdapter.setClickListner(this)
                            }

                            if (response.data?.dateArray != null && response.data?.dateArray.isNotEmpty()) {
                                arrDate = response.data.dateArray

                                if (arrDate != null && arrDate!!.isNotEmpty()) {
                                    for (i in arrDate!!.indices) {
                                        arrDate!![i].isSelect = i == 0
                                    }
                                }
                                mDateWiseAdapter.setData(arrDate!!, chooseDate)
                            }

                        } else {
                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
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

    override fun clickDateItem(date: String) {
        chooseDate = date

        if (arrDate != null && arrDate!!.isNotEmpty()) {
            for (i in arrDate!!.indices) {
                arrDate!![i].isSelect = arrDate!![i].date_value == chooseDate
            }
            mDateWiseAdapter.setData(arrDate!!, chooseDate)
        }

    }

    override fun clickSportItem(id: String, icon: String) {
        sportId = id
        playerImage = icon

        if (arrSports != null && arrSports!!.isNotEmpty()) {
            for (i in arrSports!!.indices) {
                arrSports!![i].isSelect = arrSports!![i].id == sportId
            }
            mSportAdapter.setSportIdData(sportId!!, arrSports!!)
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
        viewModel = ViewModelProvider(this, factory)[FavouriteViewModel::class.java]
    }
}