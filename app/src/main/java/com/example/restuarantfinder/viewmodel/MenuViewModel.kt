package com.example.restuarantfinder.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.data.MenuItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse

class MenuViewModel : ViewModel() {
    private var _menu = mutableStateListOf<MenuItem>()
    var errorMessage: String by mutableStateOf("")
    val menu : List<MenuItem>
        get() = _menu

    fun getMenu(food: Boolean, id: Long){
        viewModelScope.launch {
            try {
                val api = RestaurantApi.getInstance()
                val call= if(food){
                    api.findMenu_Food(id)
                } else{
                    api.findMenu_Drink(id)
                }

                val response = call?.awaitResponse()

                if (response?.isSuccessful == true) {
                    _menu.clear()
                    _menu.addAll(response.body()!!)
                    Log.d("menu", menu.size.toString())
                }
                else{
                    Log.d("Hiba", response.toString())
                }
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
                Log.d("ERROR", errorMessage)
            }
        }
    }
}