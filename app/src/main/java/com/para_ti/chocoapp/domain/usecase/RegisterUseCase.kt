package com.para_ti.chocoapp.domain.usecase

import com.para_ti.chocoapp.data.repository.UserRepository
import com.para_ti.chocoapp.data.local.entity.UserEntity

class RegisterUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: UserEntity) {
        repository.registerUser(user)
    }
}
