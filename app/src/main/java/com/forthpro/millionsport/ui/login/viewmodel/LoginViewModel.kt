package com.demoapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {
    private val _loginResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val loginResponse: LiveData<Event<Resource<CommonResponse>>> = _loginResponse

    fun loginDetail(body: RequestBodies.LoginBody) = viewModelScope.launch {
        loginDetailData(body)
    }

    private suspend fun loginDetailData(body: RequestBodies.LoginBody) {
        _loginResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.loginUser(body)
                _loginResponse.postValue(response?.let { handleLoginEventResponse(it) })
            } else {
                _loginResponse.postValue(
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
                    _loginResponse.postValue(
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
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleLoginEventResponse(response: retrofit2.Response<CommonResponse>): Event<Resource<CommonResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}