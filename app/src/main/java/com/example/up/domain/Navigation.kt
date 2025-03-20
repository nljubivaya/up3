package com.example.up.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.up.view.Catalog.Catalog
import com.example.up.view.CreateNewPassword.CreateNewPassword
import com.example.up.view.Favourite.Favourite
import com.example.up.view.ForgotPassword.ForgotPassword
import com.example.up.view.Home.Home
import com.example.up.view.LoyaltyCard.LoyaltyCard
import com.example.up.view.Profile.Profile
import com.example.up.view.RegisterAccount.RegisterAccount
import com.example.up.view.SideMenu.SideMenu
import com.example.up.view.SignIn.SignIn
import com.example.up.view.Verification.Verification
import com.example.up.view.preview.preview
import kotlinx.coroutines.delay
@Composable

fun Navigation() {
    val navHostController = rememberNavController()
    var isPreviewVisible by remember { mutableStateOf(true) }

    LaunchedEffect(isPreviewVisible) {
        if (isPreviewVisible) {
            delay(1500)
            isPreviewVisible = false
            navHostController.navigate("RegisterAccount") {
                popUpTo("RegisterAccount") { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = if (isPreviewVisible) "preview" else "RegisterAccount"
    ) {
        composable("preview") {
            preview(navHostController)
        }
        composable("RegisterAccount") {
            RegisterAccount(navHostController)
        }
        composable("SignIn") {
            SignIn(navHostController)
        }

        composable("Catalog") {
            Catalog(navHostController, onDismissRequest = {})
        }

        composable("CreateNewPassword") {
            CreateNewPassword(navHostController)
        }

        composable("Favourite") {
            Favourite(navHostController)
        }

        composable("ForgotPassword") {
            ForgotPassword(navHostController)
        }

        composable("Home") {
            Home(navHostController, onDismissRequest = {})
        }

        composable("LoyaltyCard") {
            LoyaltyCard(navHostController)
        }

        composable("Profile") {
            Profile(navHostController)
        }

        composable("SideMenu") {
            SideMenu(navHostController)
        }

        composable("Verification") {
            Verification(navHostController)
        }
    }
}

