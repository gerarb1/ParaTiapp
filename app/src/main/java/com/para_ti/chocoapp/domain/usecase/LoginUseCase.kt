/*package com.para_ti.chocoapp.domain.usecase

import com.para_ti.chocoapp.data.repository.UserRepository
import com.para_ti.chocoapp.data.local.entity.UserEntity

class LoginUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): UserEntity? {
        return repository.loginUser(email, password)
    }
}
*/