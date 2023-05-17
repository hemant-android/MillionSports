package com.forthpro.millionsport.ui.change_time.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.response.TimeFormatResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class ChangeTimeFormatViewModel(app: Application, private val appRepository: AppRepository) :
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