package com.example.restuarantfinder.api

import com.example.restuarantfinder.data.MenuItem
import com.example.restuarantfinder.data.Restaurant
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


private const val BASE_URL = "http://152.66.183.24:8080/restaurant/"

interface RestaurantApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("/restaurant/findall")
    abstract fun getAllRestaurants(): Call<List<Restaurant>>?

    @GET("/restaurant/findbyid/{id}")
    abstract  fun findRestaurant(@Path("id") id: Long): Call<Restaurant?>?

    @GET("/restaurant/findmenu/drink/{restaurantId}")
    abstract fun findMenu_Drink(@Path("restaurantId") id: Long): Call<List<MenuItem>>?

    @GET("/restaurant/findmenu/food/{restaurantId}")
    abstract fun findMenu_Food(@Path("restaurantId") id: Long): Call<List<MenuItem>>?

    @GET("/restaurant/findbyname/{name}")
    abstract fun findByName(@Path("name") name: String): Call<List<Restaurant>>?

    companion object {
        var apiService: RestaurantApi? = null
        fun getInstance(): RestaurantApi {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RestaurantApi::class.java)
            }
            return apiService!!
        }
    }
}