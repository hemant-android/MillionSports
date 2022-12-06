package com.forthpro.millionsport.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.databinding.ActivityMainBinding
import com.forthpro.millionsport.model.response.GetMovieList
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.main.adaptor.MainAdapter
import com.forthpro.millionsport.ui.main.viewmodel.MainViewModel
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.viewmodel.ViewModelProviderFactory

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter: MainAdapter by lazy { MainAdapter(this) }
    private var mAllListArr: ArrayList<GetMovieList.Result> = ArrayList()


    private var isLoading: Boolean = false
    lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.rvMainList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!isLoading) {
                    //findLastCompletelyVisibleItemPostition() returns position of last fully visible view.
                    ////It checks, fully visible view is the last one.
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mAllListArr.size - 1) {

                        loadMoreData()
                        isLoading = true

                    }
                }

            }
        })

        viewModel.getAllList()

        viewModel.getAllListResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data != null) {
                            if (response.data?.results!!.isNotEmpty()) {
                                mAllListArr = response.data?.results
                                mLayoutManager = LinearLayoutManager(this)
                                binding.rvMainList.layoutManager = mLayoutManager
                                binding.rvMainList.setHasFixedSize(true)
                                binding.rvMainList.adapter = adapter
                                adapter.setData(mAllListArr)
                                adapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(
                                    this,
                                    response.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(
                                this,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        })

        viewModel.getAllListNextResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (response.data != null) {
                            if (response.data?.results!!.isNotEmpty()) {
                                isLoading = true
                                mAllListArr.addAll(response.data?.results)
                                adapter.setData(mAllListArr)
                                adapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(
                                    this,
                                    response.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(
                                this,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
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
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

    }

    private fun loadMoreData() {
        viewModel.getAllListNext()
    }
}