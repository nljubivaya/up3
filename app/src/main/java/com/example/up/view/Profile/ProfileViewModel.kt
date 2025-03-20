package com.example.up.view.Profile

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import com.example.up.model.profiles
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.io.File
import java.lang.reflect.Modifier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel: ViewModel() {
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
    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
        }
    }

    fun generateBarcode(userId: String): Bitmap? {
        return try {
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.encodeBitmap(userId, BarcodeFormat.CODE_128, 400, 200)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    fun updateProfile(firstname: String, lastname: String, address: String, phone: String, photo: String) {
        viewModelScope.launch {
            try {
                val user = Constant.supabase.auth.currentUserOrNull()
                if (user != null) { // Проверяем, что пользователь вошел
                    Constant.supabase.from("profiles").update(
                        {
                            set("firstname", firstname)
                            set("lastname", lastname)
                            set("address", address)
                            set("phone", phone)
                        }
                    ) {
                        filter {
                            eq("user_id", user.id) // Обновляем только профиль текущего пользователя
                        }
                    }
                    showProfile() // Перезагружаем данные после обновления
                }
                Log.d("ProfileUpdate", "Профиль обновлен успешно")
            } catch (e: Exception) {
                Log.e("ProfileUpdateError", "Ошибка обновления профиля: ${e.message}")
            }
        }
    }
}