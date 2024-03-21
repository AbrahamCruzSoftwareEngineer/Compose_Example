package com.example.compose_example

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getImageList(@Url url:String):Response<APIResponse>
}