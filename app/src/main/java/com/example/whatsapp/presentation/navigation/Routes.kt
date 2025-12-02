package com.example.whatsapp.presentation.navigation

sealed class Routes(val route: String) {
    data object SplashScreen : Routes("splash_screen")
    data object WelcomeScreen : Routes("welcome_screen")
    data object UserRegistrationScreen : Routes("user_registration_screen")
    data object HomeScreen : Routes("home_screen")
    data object UpdateScreen : Routes("update_screen")
    data object CommunitiesScreen : Routes("communities_screen")
    data object CallScreen : Routes("call_screen")

    data object UserProfileScreen : Routes("user_profile_screen")
}