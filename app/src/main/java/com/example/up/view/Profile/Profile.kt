package com.example.up.view.Profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.asImageBitmap
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
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.up.R
import com.example.up.model.products
import com.example.up.view.SideMenu.SideMenu


@Composable
fun Profile(navHostController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val vm = viewModel { ProfileViewModel() }
    vm.getUser()
    var isMenuOpen by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val barcodeBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val userId = vm.user.user_id
    val errorMessage = remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(userId) {
        errorMessage.value = ""
        barcodeBitmap.value = vm.generateBarcode(userId) ?: run {
            errorMessage.value = "Ошибка при генерации бар-кода"
            null
        }
     }
    LaunchedEffect(vm.user) {
        name = vm.user.name
        firstname = vm.user.firstname
        address = vm.user.address
        phone = vm.user.phone
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
    }

    // Диалоговое окно для выбора источника изображения
    if (showDialog) {
        ImageSourceDialog(
            onDismiss = { showDialog = false },
            onGallerySelected = {
                showDialog = false
                launcher.launch("image/*") // Открыть галерею
            },
            onCameraSelected = {
                showDialog = false
                val photoFile = vm.createImageFile(context) // Вызов метода из ViewModel
                cameraLauncher.launch(Uri.fromFile(photoFile))
            }
        )
    }
    if (showDialog) {
        ImageSourceDialog(
            onDismiss = { showDialog = false },
            onGallerySelected = {
                showDialog = false
                launcher.launch("image/*") // Открыть галерею
            },
            onCameraSelected = {
                showDialog = false
                val photoFile = vm.createImageFile(context)
                cameraLauncher.launch(Uri.fromFile(photoFile))
            }
        )
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {  isMenuOpen = !isMenuOpen},
                modifier = Modifier.size(32.dp)
            ) {
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
            Text(
                text = "Профиль",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            IconButton(
                onClick = {  },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Редактировать",
                    modifier = Modifier.fillMaxSize()
                ) } }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = if (profileImageUri != null) {
                    rememberImagePainter(profileImageUri)
                } else {
                    painterResource(id = R.drawable.like) // Изображение по умолчанию
                },
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            // Кнопка для изменения фото профиля
            Button(onClick = { showDialog = true }) {
                Text("Выбрать источник изображения")
            }
            Text(
                text = "Ваше ФИО",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            barcodeBitmap.value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Бар-код",
                    modifier = Modifier
                        .size(400.dp, 200.dp) // Установите нужный размер для бар-кода
                        .padding(top = 16.dp) // Отступ сверху
                )
            } ?: run {
                Text(
                    text = errorMessage.value.ifEmpty { "Генерация бар-кода..." },
                    color = if (errorMessage.value.isNotEmpty()) Color.Red else Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Имя", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = name,
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = {
                        Text(
                            "xxxxxxxx",
                            fontSize = 15.sp,
                            color = Color(0xFF6A6A6A)
                        ) },
                    onValueChange = { newn -> name = newn},
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Фамилия", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = vm.user.firstname,
                    onValueChange = { newf -> firstname = newf },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Поле 2", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)),
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Text("Адрес", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { newa -> address = newa },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Поле 3", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Телефон", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { newp -> phone = newp},
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("Поле 3", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ),
                    modifier = Modifier.fillMaxWidth()
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
                        onClick = {  navHostController.navigate("Home") {
                            popUpTo("Home") { inclusive = true }
                        }},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "дом",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {  navHostController.navigate("Favourite") {
                            popUpTo("Favourite") { inclusive = true }
                        }},
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
                        onClick = {  navHostController.navigate("Profile") {
                            popUpTo("Profile") { inclusive = true }
                        }},
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

@Composable
fun ImageSourceDialog(
    onDismiss: () -> Unit,
    onGallerySelected: () -> Unit,
    onCameraSelected: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Выберите источник изображения") },
        text = {
            Column {
                TextButton(onClick = onGallerySelected) {
                    Text("Галерея")
                }
                TextButton(onClick = onCameraSelected) {
                    Text("Камера")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}