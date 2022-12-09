package com.forthpro.millionsport.ui.language.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.model.response.GetAllLanguageResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
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