package com.para_ti.chocoapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.para_ti.chocoapp.data.firebase.FirebaseAuthRepository
import com.para_ti.chocoapp.data.firebase.FirebaseUserRepository
import com.para_ti.chocoapp.data.firebase.UserProfile // ✅ Usa el modelo del paquete data.firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository(),
    private val firestoreRepository: FirebaseUserRepository = FirebaseUserRepository()
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    fun loadUserProfile() {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            val userData = firestoreRepository.getUserProfile(userId)
            _userProfile.value = userData
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
