package com.example.compose_example

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import retrofit2.http.Query

data class CreateUserData(
    val name: String? = null,
    val age: String? = null
)

interface APIService {
    @GET
    suspend fun getImageList(@Url url:String):Response<APIResponse>

    //This would be the most accepted way to use path and get.
    @GET("/example/example2/v2/{id}/user")
    suspend fun getUserById(@Path("id") id:String): Call<APIResponse>

    //lets say you need to use query parameters like
    // /example/example2/v2/user&age=40&name=Abraham
    // then you can use queries:
    @GET("/example/example2/v2/user")
    suspend fun getUserByQuery(
        @Query("name") name: String,
        @Query("age") age: String
    ): Call<APIResponse>

    @POST("/api/v1/users")
    suspend fun createUser(@Body postData: CreateUserData): Call<*>
}