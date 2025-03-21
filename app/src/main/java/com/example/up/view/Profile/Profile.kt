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
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.up.R
import com.example.up.model.products
import com.example.up.model.profiles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.*

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.up.view.SideMenu.SideMenu


@Composable
fun Profile(navController: NavController) {
    val vm = viewModel { ProfileViewModel() }
    var isEditing by remember { mutableStateOf(false) } // Состояние для отслеживания режима редактирования
    vm.showProfile()
    // Состояния для каждого поля
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isMenuOpen by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val barcodeBitmap = remember { mutableStateOf<Bitmap?>(null) }
   // val userId = vm.profiles.id
    val errorMessage = remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Лаунчер для выбора изображения из галереи
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        profileImageUri = uri // Сохраняем выбранное изображение
    }

    // Лаунчер для съемки фото с камеры
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            // Если снимок успешен, можно использовать profileImageUri
        }
    }
    if (showDialog) {
        ImageSourceDialog(
            onDismiss = { showDialog = false },
            onGallerySelected = {
                showDialog = false
                galleryLauncher.launch("image/*") // Открыть галерею
            },
            onCameraSelected = {
                showDialog = false
                val photoFile = vm.createImageFile(context) // Вызов метода из ViewModel
                profileImageUri = Uri.fromFile(photoFile)
               // cameraLauncher.launch(profileImageUri) // Запуск камеры
            }
        )
    }

    if (vm.profiles.isNotEmpty()) {
        val profile = vm.profiles.firstOrNull()

        // Пересоздаём состояние при изменении режима редактирования
        LaunchedEffect(vm.profiles) {
            if (!isEditing && profile != null) {
                firstname = profile.firstname
                lastname = profile.lastname
                address = profile.address
                phone = profile.phone
                // Также можно загрузить изображение профиля, если оно есть
                profileImageUri = profile.photo.toUri() // Предполагаем, что у профиля есть поле imageUri
            }
        }

        if (vm.profiles.isNotEmpty()) {
            val profile = vm.profiles.firstOrNull()

            // Пересоздаём состояние при изменении режима редактирования
            LaunchedEffect(vm.profiles) {
                if (!isEditing && profile != null) {
                    firstname = profile.firstname
                    lastname = profile.lastname
                    address = profile.address
                    phone = profile.phone
                }
            }
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
                            SideMenu(navController)
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
                IconButton(onClick = {
                    if (isEditing) { // Если был режим редактирования и нажали кнопку – сохраняем
                        profile?.let {
                            vm.updateProfile(
                                firstname,
                                lastname,
                                address,
                                phone,
                                ""
                            )
                        }
                    }
                    isEditing = !isEditing // Переключаем состояние после сохранения
                }) {
                    if (isEditing) {
                        Text(
                            text = "Сохранить",
                            fontSize = 16.sp,
                            color = Color.Blue
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Редактировать",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(    painter = rememberAsyncImagePainter(vm.profiles.firstOrNull()?.photo),
                    contentDescription = "Фото профиля",    modifier = Modifier
                        .padding(bottom = 8.dp) // Отступ снизу        .size(80.dp) // Размер изображения
                        .clip(CircleShape)
                )
                // Кнопка для изменения фото профиля
                Button(onClick = { showDialog = true }) {
                    Text("Выбрать источник изображения")
                }
                barcodeBitmap.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Бар-код",
                        modifier = Modifier
                            .size(400.dp, 200.dp)
                            .padding(top = 16.dp)
                    )
                } ?: run {
                    Text(
                        text = errorMessage.value.ifEmpty { "Генерация бар-кода..." },
                        color = if (errorMessage.value.isNotEmpty()) Color.Red else Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                // Имя пользователя
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Имя", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = firstname, // Имя
                    onValueChange = { if (isEditing) firstname = it },
                    label = { Text("Имя") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(247, 247, 249), RoundedCornerShape(16.dp)),
                    readOnly = !isEditing, // Режим редактирования
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(247, 247, 249, 255),
                        unfocusedContainerColor = Color(247, 247, 249, 255),
                        disabledContainerColor = Color(247, 247, 249, 255),
                        errorContainerColor = Color(247, 247, 249, 255),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                // Фамилия пользователя
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Фамилия", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = lastname, // Фамилия
                    onValueChange = { if (isEditing) lastname = it },
                    label = { Text("Фамилия") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(247, 247, 249), RoundedCornerShape(16.dp)),
                    readOnly = !isEditing,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(247, 247, 249, 255),
                        unfocusedContainerColor = Color(247, 247, 249, 255),
                        disabledContainerColor = Color(247, 247, 249, 255),
                        errorContainerColor = Color(247, 247, 249, 255),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                // Адрес пользователя (если есть)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Адрес", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = address, // Адрес (если есть)
                    onValueChange = { if (isEditing) address = it },
                    label = { Text("Адрес") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(247, 247, 249), RoundedCornerShape(16.dp)),
                    readOnly = !isEditing,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(247, 247, 249, 255),
                        unfocusedContainerColor = Color(247, 247, 249, 255),
                        disabledContainerColor = Color(247, 247, 249, 255),
                        errorContainerColor = Color(247, 247, 249, 255),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                // Телефон пользователя (если есть)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Телефон", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = phone, // Телефон (если есть)
                    onValueChange = { if (isEditing) phone = it },
                    label = { Text("Телефон") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(247, 247, 249), RoundedCornerShape(16.dp)),
                    readOnly = !isEditing,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(247, 247, 249, 255),
                        unfocusedContainerColor = Color(247, 247, 249, 255),
                        disabledContainerColor = Color(247, 247, 249, 255),
                        errorContainerColor = Color(247, 247, 249, 255),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
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