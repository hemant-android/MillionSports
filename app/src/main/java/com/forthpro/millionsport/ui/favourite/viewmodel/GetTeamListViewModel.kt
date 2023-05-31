package com.forthpro.millionsport.ui.favourite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.model.response.GetTeamListResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class GetTeamListViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getTeamListResponse = MutableLiveData<Event<Resource<GetTeamListResponse>>>()
    val getTeamListResponse: LiveData<Event<Resource<GetTeamListResponse>>> = _getTeamListResponse

    private val _favAddRemoveResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val favAddRemoveResponse: LiveData<Event<Resource<CommonResponse>>> = _favAddRemoveResponse

    fun getTeamListData(body: RequestBodies.GetTeamListBody) = viewModelScope.launch {
        getTeamListDetail(body)
    }

    fun favAddRemoveData(body: RequestBodies.FavAddRemoveBody) = viewModelScope.launch {
        favAddRemoveDetail(body)
    }


    private suspend fun getTeamListDetail(body: RequestBodies.GetTeamListBody) {
        _getTeamListResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getTeamListData(body)
                _getTeamListResponse.postValue(response?.let { handleResponse(it) })
            } else {
                _getTeamListResponse.postValue(
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
                    _getTeamListResponse.postValue(
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
                    _getTeamListResponse.postValue(
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

    private fun handleResponse(response: retrofit2.Response<GetTeamListResponse>): Event<Resource<GetTeamListResponse>>? {
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