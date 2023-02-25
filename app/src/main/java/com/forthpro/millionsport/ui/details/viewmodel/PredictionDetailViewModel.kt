package com.forthpro.millionsport.ui.details.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.PredictionDetailResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class PredictionDetailViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getDetailResponse = MutableLiveData<Event<Resource<PredictionDetailResponse>>>()
    val getDetailResponse: LiveData<Event<Resource<PredictionDetailResponse>>> = _getDetailResponse

    fun getPredictionDetailList(body: RequestBodies.PredictionDetailsBody, sportId: String) =
        viewModelScope.launch {
            getPredictionDetailData(body, sportId)
        }

    private suspend fun getPredictionDetailData(
        body: RequestBodies.PredictionDetailsBody,
        sportId: String,
    ) {
        _getDetailResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                when (sportId) {
                    "1" -> {
                        val response = appRepository.getSoccerPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "2" -> {
                        val response = appRepository.getHockeyPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "3" -> {
                        val response = appRepository.getBasketPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                }

            } else {
                _getDetailResponse.postValue(
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
                    _getDetailResponse.postValue(
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
                    _getDetailResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleCommonResponse(response: retrofit2.Response<PredictionDetailResponse>): Event<Resource<PredictionDetailResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}