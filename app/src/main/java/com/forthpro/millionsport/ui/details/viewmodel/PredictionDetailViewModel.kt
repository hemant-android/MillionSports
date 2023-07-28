package com.forthpro.millionsport.ui.details.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
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

    private val _favAddRemoveResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val favAddRemoveResponse: LiveData<Event<Resource<CommonResponse>>> = _favAddRemoveResponse

    fun getPredictionDetailList(body: RequestBodies.PredictionDetailsBody, sportId: String) =
        viewModelScope.launch {
            getPredictionDetailData(body, sportId)
        }

    fun favAddRemoveData(body: RequestBodies.FavAddRemoveBody) = viewModelScope.launch {
        favAddRemoveDetail(body)
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
                    "4" -> {
                        val response = appRepository.getHandballPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "5" -> {
                        val response = appRepository.getFutsalPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "6" -> {
                        val response = appRepository.getVolleyballPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "7" -> {
                        val response = appRepository.getRugbyLeaguePredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "8" -> {
                        val response = appRepository.getRugbyUnionPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "9" -> {
                        val response = appRepository.getTennisPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "10" -> {
                        val response = appRepository.getAmericanFootballPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "11" -> {
                        val response = appRepository.getBaseballPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "12" -> {
                        val response = appRepository.getPesapalloPredictionDetailsData(body)
                        _getDetailResponse.postValue(response?.let { handleCommonResponse(it) })
                    }
                    "13" -> {
                        val response = appRepository.geteSportsDetailsData(body)
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

    private suspend fun favAddRemoveDetail(body: RequestBodies.FavAddRemoveBody) {
        _favAddRemoveResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.favAddRemoveData(body)
                _favAddRemoveResponse.postValue(response?.let { handleFavAddRemoveResponse(it) })
            } else {
                _favAddRemoveResponse.postValue(
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
                    _favAddRemoveResponse.postValue(
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
                    _favAddRemoveResponse.postValue(
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

    private fun handleFavAddRemoveResponse(response: retrofit2.Response<CommonResponse>): Event<Resource<CommonResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}