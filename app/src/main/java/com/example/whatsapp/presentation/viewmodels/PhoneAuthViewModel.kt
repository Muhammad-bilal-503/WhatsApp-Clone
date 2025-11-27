package com.example.whatsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PhoneAuthViewModel @Inject constructor() : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState



}
sealed class AuthState{
    object Ideal : AuthState()
    object Loading : AuthState()
    data class CodeSent(val varificationId : String) : AuthState()
    data class Success(val user :phoneAuthUser) : AuthState()
    data class Error(val message : String) : AuthState()
}


