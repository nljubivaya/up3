package com.example.up.view.Verification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant.supabase
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class VerificationViewModel :  ViewModel() {
    fun signInWithOTP(userEmail: String, userOtp: String, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            try{
                supabase.auth.verifyEmailOtp(type = OtpType.Email.EMAIL, email = userEmail, token = userOtp)
                callback(true)
            }
            catch (e:Exception){
                Log.e("errror", e.message ?: "")
                callback(false)
            }
        }
    }
}