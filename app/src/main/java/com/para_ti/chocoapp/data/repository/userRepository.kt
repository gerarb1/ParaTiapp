package com.para_ti.chocoapp.data.repository

import com.para_ti.chocoapp.data.local.dao.UserDao
import com.para_ti.chocoapp.data.local.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun loginUser(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }

    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }
}


