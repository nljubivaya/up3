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

class ProfileViewModel:  ViewModel() {
    //var user by mutableStateOf<profiles?>(null)
    var user by mutableStateOf(profiles())

    fun getUser () {
        viewModelScope.launch {
            try {
                val currentUser  = Constant.supabase.auth.currentUserOrNull()
                if (currentUser  != null) {
                    // Получаем данные пользователя из таблицы users
                    user = Constant.supabase.from("users")
                        .select {
                            filter {
                                eq("id_user", currentUser .id) // Предполагается, что в таблице users есть поле id_user
                            }
                        }
                        .decodeSingle<profiles>() // Декодируем данные в объект profiles
                    Log.d("user", user.toString())
                }
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }


    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            // Дополнительная логика, если нужно
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