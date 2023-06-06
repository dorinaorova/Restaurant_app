package com.example.restuarantfinder.api

import com.example.restuarantfinder.R
import com.example.restuarantfinder.data.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://152.66.183.24:8080/user/"


interface UserApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("findbyid/{id}")
    abstract fun findById(@Path("id") id: Long) : Call<User?>?

    @POST("login")
    abstract fun login(@Body user: User) : Call<User?>?

    @POST("signup")
    abstract fun signup(@Body user: User) : Call<User?>?

    companion object {
        var apiService: UserApi? = null
        fun getInstance(): UserApi {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UserApi::class.java)
            }
            return apiService!!
        }

    }

}