package com.forthpro.millionsport.network

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.model.response.GetMovieList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @POST("api/check_vechile")
    suspend fun userLoginData(@Body body: RequestBodies.LoginBody): Response<CommonResponse>

    @GET("top_rated?api_key=ec01f8c2eb6ac402f2ca026dc2d9b8fd")
    suspend fun getAllListData(): Response<GetMovieList>

    @GET("top_rated?api_key=ec01f8c2eb6ac402f2ca026dc2d9b8fd&page=2")
    suspend fun getAllListNextData(): Response<GetMovieList>

}