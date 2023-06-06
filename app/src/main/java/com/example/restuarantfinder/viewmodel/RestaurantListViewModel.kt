package com.example.restuarantfinder.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restuarantfinder.api.RestaurantApi
import com.example.restuarantfinder.data.Restaurant
import com.example.restuarantfinder.data.TypeEnum
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class RestaurantListViewModel: ViewModel() {
    private var _restaurants = mutableStateListOf<Restaurant>()
    var errorMessage: String by mutableStateOf("")
    val restaurants : List<Restaurant>
        get() = filteredRestaurants()
    val filters = mutableStateListOf<TypeEnum>()

    fun getRestaurants(){
        viewModelScope.launch {
            try{
                val api = RestaurantApi.getInstance()
                val call = api.getAllRestaurants()
                val response = call?.awaitResponse()

                if(response?.isSuccessful == true){
                    _restaurants.clear()
                    _restaurants.addAll(response.body()!!)
                }
            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }

    fun searchByName(name: String){
        viewModelScope.launch {
            try{
                val api = RestaurantApi.getInstance()
                val call = api.findByName(name)
                val response = call?.awaitResponse()

                if(response?.isSuccessful == true){
                    _restaurants.clear()
                    _restaurants.addAll(response.body()!!)
                }
            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }

    private fun filteredRestaurants(): List<Restaurant>{
        val rest  = arrayListOf<Restaurant>()
        if(filters.size>0) {
            for (item in _restaurants) {
                if (item.types != null) {
                    var cnt =0
                    for (filter in filters) {
                        if (item.types!!.contains(filter)) {
                            cnt++
                        }
                    }
                    if(cnt==filters.size){
                        rest.add(item)
                    }
                }
            }
        }
        else rest.addAll(_restaurants)
        return rest
    }

    fun setFilter(name: String){
        if(name.contentEquals("Vegán")){
            if(filters.contains(TypeEnum.VEGAN)) filters.remove(TypeEnum.VEGAN)
            else filters.add(TypeEnum.VEGAN)
        }else if(name.contentEquals("Állatbarát")){
            if(filters.contains(TypeEnum.PET)) filters.remove(TypeEnum.PET)
            else filters.add(TypeEnum.PET)
        }else if(name.contentEquals("Kávézó")){
            if(filters.contains(TypeEnum.CAFE)) filters.remove(TypeEnum.CAFE)
            else filters.add(TypeEnum.CAFE)
        }else{
            if(filters.contains(TypeEnum.BAR)) filters.remove(TypeEnum.BAR)
            else filters.add(TypeEnum.BAR)
        }
    }
}