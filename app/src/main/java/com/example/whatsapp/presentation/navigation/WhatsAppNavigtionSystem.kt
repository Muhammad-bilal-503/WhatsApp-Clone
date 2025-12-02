package com.example.whatsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.presentation.callscreen.CallScreen
import com.example.whatsapp.presentation.communitiesscreen.CommunitiesScreen
import com.example.whatsapp.presentation.homescreen.HomeScreen
import com.example.whatsapp.presentation.splashscreen.SplashScreen
import com.example.whatsapp.presentation.updatescreen.UpdateScreen
import com.example.whatsapp.presentation.userregistrationscreen.UserRegistrationScreen
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
            HomeScreen()
        }

        composable(Routes.UpdateScreen.route){
            UpdateScreen()
        }

        composable(Routes.CommunitiesScreen.route){
            CommunitiesScreen()
        }

        composable(Routes.CallScreen.route){
            CallScreen()
        }


    }

}