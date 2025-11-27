package com.example.whatsapp.presentation.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.whatsapp.models.PhoneAuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState.asStateFlow()

    private val userRef = database.reference.child("users")

    fun sendVerificationCode(phoneNumber: String,activity: Activity) {

        _authState.value= AuthState.Loading

    }


}
sealed class AuthState{
    object Ideal : AuthState()
    object Loading : AuthState()
    data class CodeSent(val varificationId : String) : AuthState()
    data class Success(val user : PhoneAuthUser) : AuthState()
    data class Error(val message : String) : AuthState()
}


