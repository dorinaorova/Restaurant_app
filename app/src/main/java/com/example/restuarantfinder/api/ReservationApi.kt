package com.example.restuarantfinder.api

import com.example.restuarantfinder.data.Reservation
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://152.66.183.24:8080/reservation/"

interface ReservationApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("findbyuser/{id}")
    abstract fun findByUser(@Path("id") id: Long) : Call<List<Reservation>>?

    @POST("save/{userId}/{restaurantId}")
    abstract fun saveReservation(@Path("userId") userId: Long, @Path("restaurantId") restaurantId: Long, @Body reservation: Reservation) : Call<Reservation?>?

    companion object {
        var apiService: ReservationApi? = null
        fun getInstance(): ReservationApi {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ReservationApi::class.java)
            }
            return apiService!!
        }
    }
}