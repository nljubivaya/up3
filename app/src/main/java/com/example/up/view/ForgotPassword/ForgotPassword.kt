package com.example.up.view.ForgotPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.up.R

@Composable
fun ForgotPassword(navHostController: NavHostController) {
    val vm = viewModel { ForgotPasswordViewModel() }
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 66.dp), // Добавляем отступ сверху
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start // Выравнивание по левому краю
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Закрыть",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Забыл Пароль",
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)

        )
        Text(
            text = "Введите Свою Учетную Запись\n" +
                    " Для Сброса",
            fontSize = 16.sp,
            color = Color(0xFF707B81),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            // Поле для email
            OutlinedTextField(
                value = email,
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 18.sp),
                placeholder = {
                    Text(
                        "xyz@gmail.com",
                        fontSize = 15.sp,
                        color = Color(0xFF6A6A6A)
                    )
                },
                onValueChange = { email = it },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F7F9) // Цвет фона
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Кнопка "Отправить"
            Button(
                onClick = {
                    if (vm.isValidEmail(email)) {
                        showDialog = true
                    } else {
                        errorMessage = "Некорректный e-mail"
                        showDialog = true
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF48B2E7),
                    contentColor = Color.White
                )
            ) {
                Text("Отправить", fontSize = 14.sp)
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    text = {
                        Surface(
                            color = Color.White, // Устанавливаем белый фон
                            shape = RoundedCornerShape(16.dp) // Закругление углов
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Изображение размером 24x24
                                Image(
                                    painter = painterResource(id = R.drawable.zabil), // Замените на ваше изображение
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp) // Устанавливаем размер изображения 24x24
                                        .padding(bottom = 8.dp) // Отступ снизу
                                )

                                // Заголовок
                                Text("Проверьте Ваш Email", fontSize = 20.sp, color = Color.Black)

                                // Текст сообщения
                                Text(
                                    if (vm.isValidEmail(email)) {
                                        "Мы отправили код восстановления пароля на вашу электронную почту."
                                    } else {
                                        errorMessage
                                    },
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("OK")
                        }
                    }
                )

        }
        }
    }
}