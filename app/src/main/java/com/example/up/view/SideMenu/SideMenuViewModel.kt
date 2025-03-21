package com.example.up.view.SideMenu

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

class SideMenuViewModel : ViewModel() {
    var profiles by mutableStateOf<List<profiles>>(listOf())

    // Функция для загрузки профиля
    fun showProfile() {
        viewModelScope.launch {
            try {
                val user = Constant.supabase.auth.currentUserOrNull()
                if (user != null) {
                    profiles= Constant.supabase.from("profiles")
                        .select{
                            filter {
                                eq("user_id",user.id)

                            }
                        }.decodeList<profiles>()
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }
}