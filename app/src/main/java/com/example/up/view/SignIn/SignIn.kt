package com.example.up.view.SignIn


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.up.R

@Composable
fun SignIn(navHostController: NavHostController) {
    val vm = viewModel { SignInViewModel() }
    var passwordVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
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
            }

            Text(
                text = "Привет!",
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)

            )
            Text(
                text = "Заполните Свои Данные",
                fontSize = 16.sp,
                color = Color(0xFF707B81),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                // Поле для email
                Text("Email", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = vm.userEmail,
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = {
                        Text(
                            "xyz@gmail.com",
                            fontSize = 15.sp,
                            color = Color(0xFF6A6A6A)
                        )
                    },
                    onValueChange = { newText -> vm.userEmail = newText },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9) // Цвет фона
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Пароль", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = vm.userPassword,
                    onValueChange = {  newText -> vm.userPassword = newText  },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    placeholder = { Text("●●●●●", fontSize = 15.sp, color = Color(0xFF6A6A6A)) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Управление отображением пароля
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF7F7F9)
                    ),
                    trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                val icon = if (passwordVisible) {
                                    painterResource(id = R.drawable.eye)
                                } else {
                                    painterResource(id = R.drawable.eye_off)
                                }
                                Image(
                                    painter = icon,
                                    contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
                                )
                            }
                        }
                        )
                // Кликабельный текст "Восстановить"
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Восстановить",
                    fontSize = 12.sp,
                    color = Color(0xFF707B81),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            navHostController.navigate("ForgotPassword") {
                                popUpTo("ForgotPassword") { inclusive = true }
                            }
                        }
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (vm.userEmail.isEmpty() || vm.userPassword.isEmpty()) {
                            errorMessage = "Заполните все поля"
                            showDialog = true
                        } else if (!vm.isValidEmail(vm.userEmail)) {
                            errorMessage = "Некорректный email"
                            showDialog = true
                        } else {
                            vm.onSignInEmailPassword(navHostController)
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF48B2E7),
                        contentColor = Color.White
                    )
                ) {
                    Text("Войти", fontSize = 14.sp)
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Ошибка") },
                        text = { Text(errorMessage) },
                        confirmButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Вы впервые? ",
                        fontSize = 16.sp,
                        color = Color(0xFF707B81),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Создать",
                        fontSize = 16.sp,
                        color = Color(0xFF007BFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable(onClick = {
                                navHostController.navigate("RegisterAccount") {
                                    popUpTo("RegisterAccount") {
                                        inclusive = true
                                    }
                                }
                            })
                    )
                }
            }

        }
    }
}