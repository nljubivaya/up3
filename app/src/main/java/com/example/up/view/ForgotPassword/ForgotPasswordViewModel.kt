package com.example.up.view.ForgotPassword

import android.provider.SyncStateContract
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class ForgotPasswordViewModel: ViewModel()  {
    fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$".toRegex()
        return emailPattern.matches(email)
    }

    fun resetPassword(email: String, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            try {
                Constant.supabase.auth.resetPasswordForEmail(email)
                callback(true)
            }
            catch(e:Exception){
                Log.e("error", e.message?:"")
                callback(false)
            }
        }
    }
}