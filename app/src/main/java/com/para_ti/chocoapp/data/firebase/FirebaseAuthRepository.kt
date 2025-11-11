package com.para_ti.chocoapp.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        rol: String
    ): Result<Unit> {
        return try {
            // 🔹 Crear el usuario en Authentication
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("No se pudo obtener el UID del usuario")

            // 🔹 Crear documento en Firestore
            val userData = mapOf(
                "uid" to uid,
                "nombre" to name,
                "email" to email,
                "rol" to rol,
                "fechaRegistro" to System.currentTimeMillis()
            )

            firestore.collection("usuarios").document(uid).set(userData).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUserEmail(): String? = auth.currentUser?.email
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    //   Agrega este método:
    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}
