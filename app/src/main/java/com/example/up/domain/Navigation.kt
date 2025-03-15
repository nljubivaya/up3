package com.example.up.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.up.view.SignIn.SignIn
import kotlinx.coroutines.delay

@Composable
fun Navigation() {
    val navHostController = rememberNavController()
    val showPrevieshka = remember { mutableStateOf(true) }
    if (showPrevieshka.value) {
        LaunchedEffect(key1 = Unit) {
            delay(2500)
            showPrevieshka.value = false
            navHostController.navigate("information")
        }
    }
    NavHost(
        navController = navHostController,
        startDestination = if (showPrevieshka.value) "previeshka" else "information"
    ) {

        composable("SignIn") {
//            SignIn(navHostController)
        }


    }
}