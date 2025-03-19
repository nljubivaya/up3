package com.example.up.view.RegisterAccount

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.up.domain.Constant
import com.example.up.model.profiles
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class RegisterAccountViewModel:  ViewModel()  {

    var userName by mutableStateOf("")
    var userEmail by mutableStateOf("")
    var userPassword by mutableStateOf("")

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$".toRegex()
        return emailPattern.matches(email)
    }
    //, onResult: (Boolean) -> Unit
    fun onSignUpEmail(controller: NavHostController) {
        viewModelScope.launch {
            try {
                // Регистрация пользователя
                val response = Constant.supabase.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }

                // Проверка успешной регистрации
                val user = Constant.supabase.auth.currentUserOrNull()
                if (user != null) {
                    // Создание нового пользователя
                    val newUserData = profiles(
                    id = "", // Замените на реальный ID, если необходимо
                    user_id = user.id,
                    photo = "",
                    firstname = "",
                    lastname = "",
                    address = "",
                    phone = "",
                    name = userName,
                    email = userEmail,
                    password = userPassword
                    )

                    // Вставка данных в таблицы
                    Constant.supabase.from("users").insert(newUserData)
                    Constant.supabase.from("profiles").insert(newUserData)

                    // Успешная регистрация
                    // Здесь вы можете вызвать onResult(true) или другую логику, если нужно
                }
            } catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                // Здесь вы можете показать ошибку пользователю, если необходимо
            }
        }
    }
}