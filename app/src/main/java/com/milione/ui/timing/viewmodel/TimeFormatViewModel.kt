package com.milione.ui.timing.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.milionesports.de.R
import com.milione.app.MyApplication
import com.milione.model.response.TimeFormatResponse
import com.milione.repository.AppRepository
import com.milione.util.Event
import com.milione.util.Resource
import com.milione.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class TimeFormatViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getAllTimeFormatResponse = MutableLiveData<Event<Resource<TimeFormatResponse>>>()
    val getAllTimeFormatResponse : LiveData<Event<Resource<TimeFormatResponse>>> =
        _getAllTimeFormatResponse


    fun getAllTimeFormatList() = viewModelScope.launch {
        getAllTimeFormatData()
    }

    private suspend fun getAllTimeFormatData() {
        _getAllTimeFormatResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getAllTimeFormatData()
                _getAllTimeFormatResponse.postValue(response?.let { handleCommonResponse(it) })
            } else {
                _getAllTimeFormatResponse.postValue(
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
                    _getAllTimeFormatResponse.postValue(
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
                    _getAllTimeFormatResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleCommonResponse(response: retrofit2.Response<TimeFormatResponse>): Event<Resource<TimeFormatResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}