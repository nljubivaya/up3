package com.example.up.view.Home

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun Home(navHostController: NavHostController) {
    val vm = viewModel { HomeViewModel() }
    val selectedCategories = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        vm.getProducts()
        vm.getCatrgories()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.Top, // Изменено на Arrangement.Top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Распределение пространства между элементами
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Меню",
                    modifier = Modifier.fillMaxSize() // Этот модификатор обеспечит, что изображение заполнит кнопку
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Заполнение пространства между кнопками

            IconButton(
                onClick = {},
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.korzina),
                    contentDescription = "Корзина",
                    modifier = Modifier.fillMaxSize() // Этот модификатор обеспечит, что изображение заполнит кнопку
                )
            }
        }
        Text(
            text = "Главная",
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = "",
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = {
                Text(
                    "поиск",
                    fontSize = 15.sp,
                    color = Color(0xFF6A6A6A)
                )
            },
            onValueChange = { },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF7F7F9)
            )
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
                            // Если кнопка уже выбрана, убираем ее из списка, иначе добавляем
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
            Row(
                modifier = Modifier.fillMaxWidth(), // Занимает всю ширину
                horizontalArrangement = Arrangement.SpaceBetween // Распределяет элементы по краям
            ) {
                Text(
                    text = "Популярное",
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B)
                )
                Text(
                    text = "Все",
                    fontSize = 12.sp,
                    color = Color(0xFF48B2E7)
                )
            }

            Box {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
                LazyColumn {
                    items(
                        vm.products,
                        key = { products -> products.id },
                    ) { products ->
                        val imageUrl = products.photo ?: "eye.png"
                        Spacer(modifier = Modifier.height(20.dp))
                        val imageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current).data(products.photo)
                                .size(coil.size.Size.ORIGINAL).build()
                        ).state
                        if (imageState is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(32.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.like),
                                contentDescription = "лайк",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        if (imageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                painter = imageState.painter,
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            products.title,
                            modifier = Modifier.padding(8.dp),
                        )
                        Text(
                            products.cost.toString(),
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
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
}