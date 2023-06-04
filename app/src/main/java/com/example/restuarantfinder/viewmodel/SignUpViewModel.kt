package com.example.restuarantfinder.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.restuarantfinder.api.UserApi
import com.example.restuarantfinder.data.User
import com.example.restuarantfinder.navigation.Screen
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class SignUpViewModel : ViewModel() {
    var errorMessage: String by mutableStateOf("")

    fun signUp(user: User, navController: NavController, context: Context){
        viewModelScope.launch{
            try{
                val api = UserApi.getInstance()
                val call = api.signup(user)
                val response = call?.awaitResponse()

                if(response?.isSuccessful == true){
                    navController.navigate(route = Screen.RestaurantListScreen.route)
                    val sharedPref : SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",
                        Context.MODE_PRIVATE
                    )
                    val editor : SharedPreferences.Editor = sharedPref.edit()
                    editor.putLong("USER_ID", response.body()!!.id);
                    editor.commit()
                }
                else{
                    Log.d("HIBA ", response?.code().toString())
                }
            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }
    }
}