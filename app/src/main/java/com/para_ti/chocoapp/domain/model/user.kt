package com.para_ti.chocoapp.domain.model

data class User(
    val id: Int = 0,
    val name: String,
    val password: String = "",
    val email: String = ""
)
