package com.example.up.view.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import com.example.up.view.Catalog.CatalogViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.up.R
import com.example.up.model.products


@Composable
fun Profile(navHostController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var adress by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхняя панель с кнопками
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { /* Действие для кнопки меню */ },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Меню",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = "Профиль",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            IconButton(
                onClick = { /* Действие для кнопки редактирования */ },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Редактировать",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Основное содержимое профиля
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем содержимое
        ) {
            // Круглое изображение профиля
            Image(
                painter = painterResource(id = R.drawable.like), // Замените на изображение профиля
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Отображение ФИО
            Text(
                text = "Ваше ФИО",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Три текстовых поля
            Column(modifier = Modifier.padding(16.dp)) {
                // Поле для имени
                Text("Имя", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = "",
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = {
                        Text(
                            "xxxxxxxx",
                            fontSize = 15.sp,
                            color = Color(0xFF6A6A6A)
                        )
                    }, // Исправлено на 'color'
                    onValueChange = { },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9) // Цвет фона
                    )
                )
                Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстовыми полями
                Text("Фамилия", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Обработка ввода */ },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Поле 2", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ),
                    modifier = Modifier.fillMaxWidth() // Занимаем всю ширину
                )
                Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстовыми полями
                Text("Адрес", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Обработка ввода */ },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Поле 3", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ),
                    modifier = Modifier.fillMaxWidth() // Занимаем всю ширину
                )
                Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстовыми полями
                Text("Телефон", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Обработка ввода */ },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Поле 3", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ),
                    modifier = Modifier.fillMaxWidth() // Занимаем всю ширину
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "дом",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.like),
                            contentDescription = "Лайк",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.korzina),
                            contentDescription = "Корзина",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.zvonok),
                            contentDescription = "звонок",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.human),
                            contentDescription = "Человек",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}