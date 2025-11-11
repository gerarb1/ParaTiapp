package com.para_ti.chocoapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.para_ti.chocoapp.data.firebase.FirebaseAuthRepository
import com.para_ti.chocoapp.data.firebase.FirebaseUserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}



class AuthViewModel(
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository(),
    private val userRepository: FirebaseUserRepository = FirebaseUserRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole = _userRole.asStateFlow()

    fun register(name: String,email: String, password: String, rol: String = "user") {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.registerUser(email, password, name,rol)
            _authState.value = if (result.isSuccess) AuthState.Success("Usuario registrado correctamente")
            else AuthState.Error(result.exceptionOrNull()?.message ?: "Error al registrar")
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.loginUser(email, password)
            if (result.isSuccess) {
                // Obtener rol del usuario autenticado
                val role = userRepository.getUserRole()
                _userRole.value = role
                _authState.value = AuthState.Success("Inicio de sesión exitoso")
            } else {
                _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _authState.value = AuthState.Idle
        _userRole.value = null
    }
    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                val role = userRepository.getUserRole()
                _userRole.value = role
                _authState.value = AuthState.Success("Usuario ya autenticado")
            } else {
                _authState.value = AuthState.Idle
            }
        }
    }

}
