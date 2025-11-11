package com.para_ti.chocoapp.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val rol: String = ""
)

class FirebaseUserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // 🔹 Obtiene el rol del usuario actual
    suspend fun getUserRole(): String? {
        val uid = auth.currentUser?.uid ?: return null
        val snapshot = db.collection("usuarios").document(uid).get().await()
        return snapshot.getString("rol")
    }

    // 🔹 NUEVO: Obtiene toda la información del usuario
    suspend fun getUserProfile(userId: String): UserProfile? {
        val uid = auth.currentUser?.uid ?: return null
        val snapshot = db.collection("usuarios").document(uid).get().await()
        if (!snapshot.exists()) return null

        return UserProfile(
            name = snapshot.getString("Nombre") ?: "",
            email = snapshot.getString("Correo") ?: "",
            rol = snapshot.getString("rol") ?: ""
        )
    }
}
