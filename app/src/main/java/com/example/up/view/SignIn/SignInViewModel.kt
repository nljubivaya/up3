package com.example.up.view.SignIn

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel()  {
    var userEmail by mutableStateOf("")
    var userPassword by mutableStateOf("")
  //  controller: NavHostController
  fun isValidEmail(email: String): Boolean {
      val emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$".toRegex()
      return emailPattern.matches(email)
  }
    fun onSignInEmailPassword() {
        viewModelScope.launch {
            try {
                Constant.supabase.auth.signInWith(Email) {
                    this.email = userEmail
                    this.password = userPassword
                }
                Log.d("user id", Constant.supabase.auth.currentUserOrNull()!!.id)
//                controller.navigate("main") {
//                    popUpTo("auth") {
//                        inclusive = true
//                    }
//                }
            } catch (e: Exception) {
                Log.e("error regist", e.message.toString())
                println("Error")
                println(e.message.toString())
            }
        }
    }
}