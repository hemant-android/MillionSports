package com.forthpro.millionsport.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.response.GetMovieList
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    app: Application,
    private val appRepository: AppRepository,
) : AndroidViewModel(app) {

    private val _getAllListResponse = MutableLiveData<Event<Resource<GetMovieList>>>()
    private val _getAllListNextResponse = MutableLiveData<Event<Resource<GetMovieList>>>()
    val getAllListResponse: LiveData<Event<Resource<GetMovieList>>> = _getAllListResponse
    val getAllListNextResponse: LiveData<Event<Resource<GetMovieList>>> = _getAllListNextResponse


    fun getAllList() = viewModelScope.launch {
        getAllListData()
    }

    fun getAllListNext() = viewModelScope.launch {
        getAllListNextData()
    }

    private suspend fun getAllListData() {
        _getAllListResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getAllListData()
                _getAllListResponse.postValue(response?.let { handleAllListResponse(it) })
            } else {
                _getAllListResponse.postValue(
                    Event(
                        Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.no_internet_connection
                            )
                        )
                    )
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _getAllListResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.network_failure
                                )
                            )
                        )
                    )
                }
                else -> {
                    _getAllListResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private suspend fun getAllListNextData() {
        _getAllListNextResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getAllListNextData()
                _getAllListNextResponse.postValue(response?.let { handleAllListResponse(it) })
            } else {
                _getAllListNextResponse.postValue(
                    Event(
                        Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.no_internet_connection
                            )
                        )
                    )
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _getAllListNextResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.network_failure
                                )
                            )
                        )
                    )
                }
                else -> {
                    _getAllListNextResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }


    private fun handleAllListResponse(response: retrofit2.Response<GetMovieList>): Event<Resource<GetMovieList>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}