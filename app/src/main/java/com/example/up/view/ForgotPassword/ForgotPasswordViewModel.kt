package com.example.up.view.ForgotPassword

import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel: ViewModel()  {
    fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$".toRegex()
        return emailPattern.matches(email)
    }
}