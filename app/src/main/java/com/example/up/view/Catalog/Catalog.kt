package com.example.up.view.Catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
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
import com.example.up.view.SideMenu.SideMenu

@Composable
fun Catalog(navHostController: NavHostController, onDismissRequest: () -> Unit) {
    val vm = viewModel { CatalogViewModel() }
    val vm1 = viewModel { HomeViewModel() }
    val selectedCategories = remember { mutableStateListOf<String>() }
    var isFavourite by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        vm.getProducts()
        vm.getCatrgories()
    }

    val filteredProducts = vm.products.filter { product ->
        selectedCategory == null || product.category_id == selectedCategory
    }
    var isMenuOpen by remember { mutableStateOf(false) }
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { isMenuOpen = !isMenuOpen}, modifier = Modifier.size(32.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Меню",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                if (isMenuOpen) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.8f))
                            .clickable { isMenuOpen = false }
                    ) {
                        Column(
                            modifier = Modifier
                                .width(250.dp)
                                .fillMaxHeight()
                                .background(Color.White)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Top
                        ) {
                            SideMenu(navHostController)
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.korzina),
                        contentDescription = "Корзина",
                        modifier = Modifier.fillMaxSize()
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
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(vm.categories, key = { it.id }) { category ->
                Button(
                    onClick = {
                        selectedCategory =
                            if (selectedCategory == category.id) null else category.id // Переключение категории
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category.id) Color(0xFF48B2E7) else Color.White
                    )
                ) {
                    Text(
                        text = category.title,
                        color = if (selectedCategory == category.id) Color.White else Color.Black
                    )
                }
            }
        }

        // Отображение продуктов
        LazyColumn {
            items(
                filteredProducts.chunked(2),
                key = { products -> products.joinToString(",") { it.id.toString() } }
            ) { productPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    productPair.forEach { product ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)

                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                // Кнопка "лайк"
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
                                        colorFilter = if (isFavourite) ColorFilter.tint(Color.Red) else null
                                    )
                                }

                                // Загрузка изображения продукта
                                val imageState = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(product.photo)
                                        .size(coil.size.Size.ORIGINAL).build()
                                ).state

                                // Обработка состояния загрузки
                                if (imageState is AsyncImagePainter.State.Error) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "Ошибка загрузки", color = Color.Red)
                                    }
                                } else if (imageState is AsyncImagePainter.State.Success) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        painter = imageState.painter,
                                        contentDescription = product.title,
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    // Показать индикатор загрузки
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                // Подпись "BEST SELLER"
                                Text(
                                    text = "BEST SELLER",
                                    modifier = Modifier.padding(start = 10.dp),
                                    color = Color(0xFF48B2E7)
                                )

                                // Название продукта
                                Text(
                                    text = product.title,
                                    modifier = Modifier.padding(start = 10.dp, top = 4.dp),
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.Bold // Сделаем текст жирным
                                )

                                // Цена продукта
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "₽${product.cost}",
                                        modifier = Modifier.padding(start = 10.dp),
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
