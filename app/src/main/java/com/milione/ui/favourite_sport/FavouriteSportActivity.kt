package com.milione.ui.favourite_sport

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.milione.BaseActivity
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivityFavSportBinding
import com.milione.model.RequestBodies
import com.milione.model.response.FavouriteSportResponse
import com.milione.repository.AppRepository
import com.milione.ui.favourite_sport.adapter.FavSportsDragDropAdapter
import com.milione.ui.favourite_sport.dragdrop.ItemMoveCallbackListener
import com.milione.ui.favourite_sport.dragdrop.OnStartDragListener
import com.milione.ui.favourite_sport.viewmodel.FavouriteSportViewModel
import com.milione.util.Resource
import com.milione.viewmodel.ViewModelProviderFactory
import java.util.ArrayList

class FavouriteSportActivity : BaseActivity(),
    OnStartDragListener, FavSportsDragDropAdapter.onClickListner {

    private lateinit var arrFavSport: ArrayList<FavouriteSportResponse.SportsArray>
    private lateinit var binding: ActivityFavSportBinding
    private lateinit var viewModel: FavouriteSportViewModel

//    private var arrFavSport: ArrayList<FavouriteSportResponse.SportsArray>? = arrayListOf()

//    private val mSportAdapter: FavSportsDragDropAdapter by lazy { FavSportsDragDropAdapter(this) }

    private lateinit var mSportAdapter: FavSportsDragDropAdapter
    lateinit var touchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavSportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        mSportAdapter = FavSportsDragDropAdapter(this, this)

        val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(mSportAdapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvFavSport)
        binding.rvFavSport.adapter = mSportAdapter

        mSportAdapter.setClickListner(this)

        binding.imgBack.setOnClickListener {
            finish()
        }

        val body = RequestBodies.GetNotificationBody(PreferenceHelper.deviceId)
        viewModel.getFavouriteSportItem(body)

        viewModel.getFavouriteResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()

                        if (response.data?.status == 1 && response.data?.sports_array?.size!! > 0) {

                            if (::arrFavSport.isInitialized)
                            {
                                if (arrFavSport != null && arrFavSport!!.size > 0) {
                                    arrFavSport!!.clear()
                                }
                            }


                            binding.tvTitle.text = response.data?.label_name

                            arrFavSport = response.data?.sports_array

                            mSportAdapter.setData(arrFavSport!!)


                            /*itemTouchHelper.attachToRecyclerView(binding.rvFavSport)

                            mSportAdapter.differ.submitList(arrFavSport!!)
                            binding.rvFavSport.adapter = mSportAdapter
                            mSportAdapter.setClickListner(this)*/

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
        viewModel = ViewModelProvider(this, factory)[FavouriteSportViewModel::class.java]
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    override fun dragAndDropSportItem(fromPosition: Int, toPosition: Int) {
        /*val body = RequestBodies.UpdatedSportItemBody(PreferenceHelper.deviceId,arrFavSport[fromPosition].id,arrFavSport[fromPosition].position,arrFavSport[toPosition].id,arrFavSport[toPosition].position)
        viewModel.updateSportItem(body)

        viewModel.updateSportItemResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()

                        if (response.data?.status == 1) {
                            PreferenceHelper.isFavSport = true
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
        }*/
    }

    override fun updatePosition(updateItem: String) {
        val body = RequestBodies.UpdatedSportPositionBody(PreferenceHelper.deviceId,updateItem)
        viewModel.updateSportItem(body)

        viewModel.updateSportItemResponse.observe(this) { event ->
            event?.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()

                        if (response.data?.status == 1) {
                            PreferenceHelper.isFavSport = true
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