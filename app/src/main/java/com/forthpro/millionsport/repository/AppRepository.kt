package com.forthpro.millionsport.repository

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.network.RetrofitInstance

class AppRepository {
    suspend fun loginUser(body: RequestBodies.LoginBody) =
        RetrofitInstance().retrofitApi?.userLoginData(body)

    suspend fun getAllListData() = RetrofitInstance().retrofitApi?.getAllListData()
    suspend fun getAllListNextData() = RetrofitInstance().retrofitApi?.getAllListNextData()
}