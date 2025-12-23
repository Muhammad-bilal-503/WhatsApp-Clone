package com.example.whatsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.presentation.callscreen.CallScreen
import com.example.whatsapp.presentation.communitiesscreen.CommunitiesScreen
import com.example.whatsapp.presentation.homescreen.HomeScreen
import com.example.whatsapp.presentation.profile.userProfilesetScreen
import com.example.whatsapp.presentation.splashscreen.SplashScreen
import com.example.whatsapp.presentation.updatescreen.UpdateScreen
import com.example.whatsapp.presentation.userregistrationscreen.UserRegistrationScreen
import com.example.whatsapp.presentation.viewmodels.BaseViewModel
import com.example.whatsapp.presentation.welcomescreen.WelcomeScreen

@Composable
fun WhatsAppNavigationSystem() {
    val navController = rememberNavController()
    NavHost(startDestination = Routes.SplashScreen.route, navController = navController ) {

        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(Routes.WelcomeScreen.route){
            WelcomeScreen(navController)
        }

        composable(Routes.UserRegistrationScreen.route){
            UserRegistrationScreen(navController)
        }

        composable(Routes.HomeScreen.route){
            val baseViewModel: BaseViewModel = hiltViewModel()
            HomeScreen(navController, baseViewModel)
        }

        composable(Routes.UpdateScreen.route){
            UpdateScreen(navController)
        }

        composable(Routes.CommunitiesScreen.route){
            CommunitiesScreen(navController)
        }

        composable(Routes.CallScreen.route){
            CallScreen(navController)
        }

        composable(Routes.UserProfileSetScreen.route) {
            userProfilesetScreen(navHostController = navController)
        }

    }

}