package com.example.up.view.Profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import com.example.up.model.profiles
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class ProfileViewModel:  ViewModel() {
//    var user by mutableStateOf(profiles())

//    fun getUser() {
//        viewModelScope.launch {
//            try {
//                val currentUser = Constant.supabase.auth.currentUserOrNull()
//                if(currentUser != null) {
//
//                    user = Constant.supabase.from("users")
//                        .select{
//                            filter {
//                                eq("id_user", currentUser.id)
//                            }
//                        }.decodeSingle<Users>()
//                    Log.d("user", user.toString())
//                }
//
//            } catch (e: Exception) {
//                println(e.message.toString())
//            }
//        }
//    }

    fun updateUser(user_id: String, photo: String, firstname: String, lastname: String, address: String, phone: String) {
        viewModelScope.launch {
            try {
                if (user_id != "" && firstname != "" && lastname != "" && address != "" && phone != "") {
                    Constant.supabase.from("users").update(
                        {
                            set("user_id", user_id)
                            set("photo", photo)
                            set("firstname", firstname)
                            set("lastname", lastname)
                            set("address", address)
                            set("phone", phone)
                        }
                    ) {
                        filter {
                            //eq("id_user", profiles.id)
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }
}