package com.example.restuarantfinder.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restuarantfinder.api.ReservationApi
import com.example.restuarantfinder.api.UserApi
import com.example.restuarantfinder.data.Reservation
import com.example.restuarantfinder.data.User
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ProfileViewModel : ViewModel(){
    private var _user = mutableStateOf(User(0L,"","","","",0L))
    private var _reservations = mutableStateListOf<Reservation>()
    var errorMessage: String by mutableStateOf("")
    val user : User
        get() = _user.value
    val reservations: List<Reservation>
        get() = _reservations

    fun fetchDatas(context: Context){
        viewModelScope.launch {
            val sharedPref = context.getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE)
            val userId = sharedPref.getLong("USER_ID", 1L)

            try{
                val userApi = UserApi.getInstance()
                val call_User = userApi.findById(userId)
                val response_User = call_User?.awaitResponse()

                if(response_User?.isSuccessful == true){
                    _user.value = response_User.body()!!
                }

                val reservationApi = ReservationApi.getInstance()
                val call_Res = reservationApi.findByUser(userId)
                val response_Res = call_Res?.awaitResponse()

                if(response_Res?.isSuccessful == true){
                    _reservations.addAll(response_Res.body()!!)
                }

            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }
}