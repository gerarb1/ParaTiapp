package com.para_ti.chocoapp.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.para_ti.chocoapp.data.local.AppDatabase
import com.para_ti.chocoapp.data.local.entity.UserEntity
import com.para_ti.chocoapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(context: Context) : ViewModel() {

    private val userDao = AppDatabase.getDatabase(context).userDao()
    private val repository = UserRepository(userDao)

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                repository.loginUser(email, password)
            }
            onResult(user != null)
        }
    }

    fun register(user: UserEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.registerUser(user)
            }
            onResult(true)
        }
    }
}
