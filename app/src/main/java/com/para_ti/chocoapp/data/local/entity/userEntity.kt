package com.para_ti.chocoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.para_ti.chocoapp.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String = "",
    val password: String = ""
) {
    fun toDomain() = User(
        id = id,
        name = username,
        email = email,
        password = password
    )
}
