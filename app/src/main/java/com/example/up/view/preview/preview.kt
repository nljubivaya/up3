package com.example.up.view.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.up.R

@Composable
fun preview(navHostController: NavHostController) {
    // Устанавливаем цвет фона
    val backgroundColor = Color(0xFF48B2E7)
// Используем Box для размещения элементов
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor), // Устанавливаем фон
        contentAlignment = Alignment.Center // Центрируем содержимое
    ) {
        // Отображаем первое изображение
        Image(
            painter = painterResource(id = R.drawable.preview),
            contentDescription = "Preview Image",
            modifier = Modifier.size(200.dp)
        )

        // Вложенный Box для второго изображения
        Box(
            modifier = Modifier.size(100.dp) // Размер второго изображения
        ) {
            // Отображаем второе изображение
            Image(
                painter = painterResource(id = R.drawable.toprew),
                contentDescription = "Toprew Image",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
