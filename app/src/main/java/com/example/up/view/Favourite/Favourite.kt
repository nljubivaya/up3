package com.example.up.view.Favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.up.R

@Composable
fun Favourite(navHostController: NavHostController) {
    val vm = viewModel { FavouriteViewModel() }
    LaunchedEffect(Unit) {
        vm.getFavourite()
        vm.getProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Заголовок и кнопки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Кнопка меню
            IconButton(
                onClick = {  navHostController.navigate("RegisterAccount") {
                    popUpTo("RegisterAccount") {
                        inclusive = true
                    }
                } },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Закрыть",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Кнопка корзины
            IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                Image(painter = painterResource(id = R.drawable.redlove), contentDescription = "Корзина", modifier = Modifier.fillMaxSize())
            }
        }

        Text(
            text = "Избранное",
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (vm.favourite.isEmpty() || vm.products.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (vm.favourite.isEmpty()) {
            Text("Нет избранных товаров", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(vm.favourite, key = { favourite -> favourite.id }) { favourite ->
                    val product = vm.products.find { it.id == favourite.product_id }
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val imageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(product?.photo)
                                .size(coil.size.Size.ORIGINAL)
                                .build()
                        ).state

                        // Проверка состояния загрузки изображения
                        if (imageState is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else if (imageState is AsyncImagePainter.State.Success) {
                            Card(shape = RoundedCornerShape(8.dp)) { // Используем RoundedCornerShape
                                Image(
                                    modifier = Modifier.size(100.dp),
                                    painter = imageState.painter,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = product?.title ?: "",
                                modifier = Modifier.padding(8.dp),
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                lineHeight = 16.sp
                            )
                            Text(
                                text = product?.cost.toString() ?: "",
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                lineHeight = 16.sp
                            )

                            IconButton(
                                onClick = {
                                    // Вызов метода удаления из избранного
                                     vm.deleteFavourite(favourite.id)
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Удалить",
                                    tint = Color.Black
                                )
                            }
                        }

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp), // Отступ снизу
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "дом",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(
                    onClick = {
                        navHostController.navigate("Favourite") {
                            popUpTo("Favourite") { inclusive = true }
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = "Лайк",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.korzina),
                        contentDescription = "Корзина",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.zvonok),
                        contentDescription = "звонок",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(
                    onClick = {
                        navHostController.navigate("Profile") {
                            popUpTo("Profile") { inclusive = true }
                        }
                    },
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


