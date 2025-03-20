package com.example.up.view.SideMenu

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.up.R


@Composable
fun SideMenu(navHostController: NavController) {
    val icons = listOf(
        R.drawable.human, // Профиль
        R.drawable.korzina, // Корзина
        R.drawable.like, // Избранное
        R.drawable.bibika, // Заказы
        R.drawable.yvedi, // Уведомления
        R.drawable.nastroki // Настройки
    )

    val labels = listOf(
        "Профиль",
        "Корзина",
        "Избранное",
        "Заказы",
        "Уведомления",
        "Настройки"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF48B2E7)) // Устанавливаем фон
            .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start // Выравнивание по левому краю
    ) {
        // Круглое изображение
        Image(
            painter = painterResource(id = R.drawable.like),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ФИО
        Text(
            text = "Ваше ФИО",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Список элементов с иконками и текстом
        LazyColumn(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(icons.indices.toList()) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            when (index) {
                                0 -> navHostController.navigate("Profile") // Профиль
                                1 -> navHostController.navigate("") // Корзина
                                2 -> navHostController.navigate("Favorite") // Избранное
                                3 -> navHostController.navigate("") // Заказы
                                4 -> navHostController.navigate("") // Уведомления
                                5 -> navHostController.navigate("") // Настройки
                            }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = "Icon ${index + 1}",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = labels[index],
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    if (index < icons.size ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic1),
                            contentDescription = "Icon 2",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            item {
                Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
            }
           item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            navHostController.navigate("SignIn") {
                                popUpTo("SignIn") { inclusive = true }
                            }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bye),
                            contentDescription = "Выйти",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Выйти",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                }
            }
        }
    }
}