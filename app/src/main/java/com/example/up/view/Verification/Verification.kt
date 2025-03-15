package com.example.up.view.Verification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Verification() {
//    val vm = viewModel { RegisterAccountViewModel() }
    var timerValue by remember { mutableStateOf(60) }
    var isRunning by remember { mutableStateOf(true) }
    LaunchedEffect(isRunning) {
        while (isRunning && timerValue > 0) {
            delay(1000) // Задержка на 1 секунду
            timerValue-- // Уменьшаем значение таймера
        }
    }
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
            text = "ОTP Проверка",
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Пожалуйста, Проверьте Свою \nЭлектронную Почту, Чтобы Увидеть Код \nПодтверждения",
            fontSize = 16.sp,
            color = Color(0xFF707B81),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Общие отступы для всей колонки
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем содержимое
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween // Используем SpaceBetween для пробелов между полями
            ) {
                repeat(6) { index ->
                    TextField(
                        value = "", // Замените на состояние для управления вводом
                        onValueChange = { newValue ->
                            // Позволяем вводить только одну цифру
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                // Обновите состояние для конкретного поля
                            }
                        },
                        modifier = Modifier
                            .size(width = 46.dp, height = 99.dp) // Устанавливаем размеры
                            .background(Color(0xFFF7F7F9), shape = RoundedCornerShape(12.dp)) // Закругленные углы и цвет фона
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp)), // Закругленная граница
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent, // Убираем индикатор при фокусе
                            unfocusedIndicatorColor = Color.Transparent // Убираем индикатор при потере фокуса
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp)) // Пробел между полями
                }
            }
            Text(
                text = "$timerValue",
                fontSize = 12.sp,
                color = Color(0xFF707B81),
                textAlign = TextAlign.Right, // Центрируем текст
                modifier = Modifier.padding(top = 12.dp) // Отступ сверху
            )
        }
    }
}