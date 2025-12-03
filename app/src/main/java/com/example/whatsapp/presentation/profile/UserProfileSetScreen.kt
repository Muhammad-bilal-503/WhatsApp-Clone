package com.example.whatsapp.presentation.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.whatsapp.presentation.viewmodels.PhoneAuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun userProfilesetScreen(phoneAuthViewModel: PhoneAuthViewModel, navHostController: NavHostController){


    var name by remember { mutableStateOf("") }

    var status by remember { mutableStateOf("") }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    val firebaseAuth = Firebase.auth

    val phoneNumber = firebaseAuth.currentUser?.phoneNumber?:""

    val userid = firebaseAuth.currentUser?.uid?:""

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(

        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->

            profileImageUri = uri

            uri?.let {

                bitmapImage = if (Build.VERSION.SDK_INT < 28){

                    @Suppress("DEPRECATION")
                    android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, it)

                }
                else{
                    val source = ImageDecoder.createSource(context.contentResolver,it)
                    ImageDecoder.decodeBitmap(source)
                }
            }

        }
    )

}
