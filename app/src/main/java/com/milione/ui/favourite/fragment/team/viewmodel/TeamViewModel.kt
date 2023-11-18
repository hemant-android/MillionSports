package com.milione.ui.favourite.fragment.team.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.milionesports.de.R
import com.milione.app.MyApplication
import com.milione.model.RequestBodies
import com.milione.model.response.CommonResponse
import com.milione.model.response.TeamResponse
import com.milione.repository.AppRepository
import com.milione.util.Event
import com.milione.util.Resource
import com.milione.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class TeamViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getFavResponse = MutableLiveData<Event<Resource<TeamResponse>>>()
    val getFavResponse: LiveData<Event<Resource<TeamResponse>>> = _getFavResponse

    private val _favAddRemoveResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val favAddRemoveResponse: LiveData<Event<Resource<CommonResponse>>> = _favAddRemoveResponse

    fun getFav(body: RequestBodies.FavBody) = viewModelScope.launch {
        getFavData(body)
    }

    fun favAddRemoveData(body: RequestBodies.FavAddRemoveBody) = viewModelScope.launch {
        favAddRemoveDetail(body)
    }

    private suspend fun getFavData(body: RequestBodies.FavBody) {
        _getFavResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getTeamData(body)
                _getFavResponse.postValue(response?.let { handleResponse(it) })
            } else {
                _getFavResponse.postValue(
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
                    _getFavResponse.postValue(
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
                    _getFavResponse.postValue(
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

    private fun handleResponse(response: retrofit2.Response<TeamResponse>): Event<Resource<TeamResponse>>? {
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