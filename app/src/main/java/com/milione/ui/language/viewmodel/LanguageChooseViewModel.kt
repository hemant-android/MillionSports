package com.milione.ui.language.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.milionesports.de.R
import com.milione.app.MyApplication
import com.milione.model.RequestBodies
import com.milione.model.response.CommonResponse
import com.milione.model.response.GetAllLanguageResponse
import com.milione.repository.AppRepository
import com.milione.util.Event
import com.milione.util.Resource
import com.milione.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class LanguageChooseViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getAllLanguageResponse = MutableLiveData<Event<Resource<GetAllLanguageResponse>>>()
    val getAllLanguageResponse: LiveData<Event<Resource<GetAllLanguageResponse>>> =
        _getAllLanguageResponse

    private val _getLanguageTextResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val getLanguageTextResponse: LiveData<Event<Resource<CommonResponse>>> =
        _getLanguageTextResponse

    fun getAllLanguageList() = viewModelScope.launch {
        getAllLanguageData()
    }

    fun getLanguageLabel(body: RequestBodies.LanguageLabelBody) = viewModelScope.launch {
        getLanguageLabelData(body)
    }

    fun changeLanguage(body: RequestBodies.ChangeLanguageBody) = viewModelScope.launch {
        changeLanguageData(body)
    }

    private suspend fun getAllLanguageData() {
        _getAllLanguageResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getAllLanguageData()
                _getAllLanguageResponse.postValue(response?.let { handleCommonResponse(it) })
            } else {
                _getAllLanguageResponse.postValue(
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
                    _getAllLanguageResponse.postValue(
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
                    _getAllLanguageResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private suspend fun getLanguageLabelData(body: RequestBodies.LanguageLabelBody) {
        _getLanguageTextResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getLanguageLabelData(body)
                _getLanguageTextResponse.postValue(response?.let { handleLanguageLabelResponse(it) })
            } else {
                _getLanguageTextResponse.postValue(
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
                    _getLanguageTextResponse.postValue(
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
                    _getLanguageTextResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private suspend fun changeLanguageData(body: RequestBodies.ChangeLanguageBody) {
        _getLanguageTextResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.changeLanguageData(body)
                _getLanguageTextResponse.postValue(response?.let { handleLanguageLabelResponse(it) })
            } else {
                _getLanguageTextResponse.postValue(
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
                    _getLanguageTextResponse.postValue(
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
                    _getLanguageTextResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleCommonResponse(response: retrofit2.Response<GetAllLanguageResponse>): Event<Resource<GetAllLanguageResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    private fun handleLanguageLabelResponse(response: retrofit2.Response<CommonResponse>): Event<Resource<CommonResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}