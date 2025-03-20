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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.up.view.SideMenu.SideMenu
@Composable
fun Home(navHostController: NavHostController, onDismissRequest: () -> Unit) {
    val vm = viewModel { HomeViewModel() }
    var isFavourite by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val filteredProducts = vm.products.filter { product ->
        (selectedCategory == null || product.category_id == selectedCategory) &&
                product.title.contains(searchText, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        vm.getProducts()
        vm.getCatrgories()
    }
    var isMenuOpen by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { isMenuOpen = !isMenuOpen }, modifier = Modifier.size(32.dp)) {
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

            OutlinedTextField(
                value = searchText,
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 18.sp),
                placeholder = {
                    Text("поиск", fontSize = 15.sp, color = Color(0xFF6A6A6A))
                },
                onValueChange = { newText ->
                    searchText = newText
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F7F9)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Категории",
                fontSize = 16.sp,
                color = Color(0xFF2B2B2B),
                modifier = Modifier.align(Alignment.Start)
            )

            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(vm.categories, key = { it.id }) { category ->
                        Button(
                            onClick = {
                                selectedCategory = if (selectedCategory == category.id) null else category.id
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Популярное",
                        fontSize = 16.sp,
                        color = Color(0xFF2B2B2B),
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )

                        Text(
                            text = "Все",
                            fontSize = 16.sp,
                            color = Color(0xFF48B2E7),
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    navHostController.navigate("Catalog") {
                                        popUpTo("Catalog") {
                                            inclusive = true
                                        }
                                    }
                                }

                    )
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(filteredProducts.take(2), key = { it.id }) { product ->
                        val imageUrl = product.photo ?: "eye.png"
                        val imageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current).data(product.photo)
                                .size(coil.size.Size.ORIGINAL).build()
                        ).state

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        isFavourite = !isFavourite
                                        vm.addFavourite(product.id, onDismissRequest)
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
                                    text = "BEST SELLER",
                                    modifier = Modifier.padding(start = 10.dp),
                                    color = Color(0xFF48B2E7)
                                )
                                Text(
                                    product.title,
                                    modifier = Modifier.padding(start = 10.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "₽${product.cost} ",
                                    modifier = Modifier.padding(start = 10.dp),
                                    fontSize = 16.sp,
                                    color = Color(0xFF48B2E7)
                                )
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Акции",
                                    fontSize = 16.sp,
                                    color = Color(0xFF2B2B2B),
                                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                                )
                                Text(
                                    text = "Все",
                                    fontSize = 16.sp,
                                    color = Color(0xFF48B2E7),
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .clickable {
                                            // Действие для перехода на страницу акций
                                        }
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.akzii),
                                contentDescription = "Акции",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Gray)
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {
                navHostController.navigate("Catalog") {
                    popUpTo("Catalog") { inclusive = true }
                }
            }, modifier = Modifier.size(32.dp)) {
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
