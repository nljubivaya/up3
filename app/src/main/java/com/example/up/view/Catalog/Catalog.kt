package com.example.up.view.Catalog

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.up.view.Home.HomeViewModel

@Composable
fun Catalog(navHostController: NavHostController, onDismissRequest: () -> Unit) {
    val vm = viewModel { CatalogViewModel() }
    val vm1 = viewModel { HomeViewModel() }
    val selectedCategories = remember { mutableStateListOf<String>() }
    var isFavourite by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        vm.getProducts()
        vm.getCatrgories()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Распределение пространства между элементами
        ) {
            IconButton(
                onClick = { navHostController.navigate("Home") {
                    popUpTo("Home") { inclusive = true }
                }},
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.strela),
                    contentDescription = "Меню",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text(
            text = "Outdoor",
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Категории",
            fontSize = 16.sp,
            color = Color(0xFF2B2B2B),
            modifier = Modifier
                .align(Alignment.Start)
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(vm.categories, key = { it.id }) { category ->
                    Button(
                        onClick = {
                            if (selectedCategories.contains(category.id)) {
                                selectedCategories.remove(category.id)
                            } else {
                                selectedCategories.add(category.id)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategories.contains(category.id)) Color(0xFF48B2E7) else Color.White
                        )
                    ) {
                        Text(text = category.title, color = if (selectedCategories.contains(category.id)) Color.White else Color.Black)
                    }
                }
            }
            Box {
                Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
                    LazyColumn {
                        items(
                            vm.products.chunked(2), // Разбиваем на группы по 2 элемента
                            key = { products -> products.joinToString(",") { it.id.toString() } } // Уникальный ключ для каждой группы
                        ) { productPair -> // Получаем пару продуктов
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween // Распределяем карточки по ширине
                            ) {
                                productPair.forEach { product -> // Итерируем по паре продуктов
                                    Card(
                                        modifier = Modifier
                                            .weight(1f) // Занимаем равную ширину
                                            .padding(end = 10.dp), // Отступ между карточками

                                    ) {
                                        Column(modifier = Modifier.padding(8.dp)) { // Отступ внутри карточки
                                            IconButton(
                                                onClick = {
                                                    isFavourite = !isFavourite
                                                    vm1.addFavourite(product.id, onDismissRequest)
                                                },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.like),
                                                    contentDescription = "лайк",
                                                    modifier = Modifier.fillMaxSize(),
                                                    colorFilter = if (isFavourite) ColorFilter.tint(
                                                        Color.Red
                                                    ) else null
                                                )
                                            }

                                            val imageState = rememberAsyncImagePainter(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(product.photo)
                                                    .size(coil.size.Size.ORIGINAL).build()
                                            ).state

                                            if (imageState is AsyncImagePainter.State.Error) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(100.dp), // Уменьшаем высоту
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator()
                                                }
                                            }

                                            if (imageState is AsyncImagePainter.State.Success) {
                                                Image(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(100.dp), // Уменьшаем высоту
                                                    painter = imageState.painter,
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop
                                                )
                                            }

                                            Text(
                                                text = "BEST SELLER",
                                                modifier = Modifier.padding(start = 10.dp),
                                                color = Color(0xFF48B2E7)
                                            )
                                            Text(
                                                text = product.title,
                                                modifier = Modifier.padding(start = 10.dp),
                                                color = Color(0xFF6A6A6A)
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = "₽${product.cost} ",
                                                    modifier = Modifier
                                                        .padding(start = 10.dp),
                                                    color = Color(0xFF6A6A6A)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 25.dp),
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
