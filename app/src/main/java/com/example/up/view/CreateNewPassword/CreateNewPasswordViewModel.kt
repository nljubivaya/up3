package com.example.up.view.CreateNewPassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class CreateNewPasswordViewModel :  ViewModel() {
    fun updatePassword(otp: String, newPassword: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                Constant.supabase.auth.updateUser{
                    password = newPassword
                    nonce = otp
                }
                callback(true, "")
            } catch (e: Exception) {
                Log.e("error", e.message ?: "")

            }
        }
    }
}