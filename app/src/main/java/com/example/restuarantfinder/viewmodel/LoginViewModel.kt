package com.example.restuarantfinder.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
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

class LoginViewModel : ViewModel() {
    private var _user = mutableStateOf(User(0L,"","","","",0L))
    var errorMessage: String by mutableStateOf("")
    val userId : Long
        get() = _user.value.id

    fun login(user: User, navController: NavController, context: Context){
        viewModelScope.launch {
            try{
                val api = UserApi.getInstance()
                var call = api.login(user)
                val response = call?.awaitResponse()

                if(response?.isSuccessful == true){
                    _user.value = response.body()!!
                    navController.navigate(route = Screen.RestaurantListScreen.route)
                    var sharedPref : SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",MODE_PRIVATE)
                    var editor : SharedPreferences.Editor = sharedPref.edit()
                    editor.putLong("USER_ID", userId);
                    editor.commit()
                }
                else{
                    Toast.makeText(context, "Hibás adatok", Toast.LENGTH_SHORT ).show()
                }
            }catch(e: java.lang.Exception){
                errorMessage = e.message.toString()
                Log.d("ERROR ", errorMessage)
            }
        }

    }
}