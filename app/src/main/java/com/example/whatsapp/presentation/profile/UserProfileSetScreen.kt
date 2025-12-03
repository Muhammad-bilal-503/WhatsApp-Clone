package com.example.whatsapp.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.whatsapp.presentation.viewmodels.PhoneAuthViewModel

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun userProfileSetScreen(phoneAuthViewModel: PhoneAuthViewModel, navHostController: NavHostController){


    var name by remember { mutableStateOf("") }

    var status by remember { mutableStateOf("") }

}
