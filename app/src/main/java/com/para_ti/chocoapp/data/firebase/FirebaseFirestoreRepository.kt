/*
package com.para_ti.chocoapp.data.firebase


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val uid: String = "",
    val nombre: String = "",
    val email: String = ""
)

class FirebaseFirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val usersCollection = firestore.collection("users")

    //  Guarda el perfil del usuario en Firestore
    suspend fun saveUserProfile(nombre: String, email: String) {
        val uid = auth.currentUser?.uid ?: throw Exception("Usuario no autenticado")

        val user = UserProfile(
            uid = uid,
            nombre = nombre,
            email = email
        )

        usersCollection.document(uid)
            .set(user, SetOptions.merge())
            .await()
    }

    // 🔹 Obtiene el perfil del usuario actual
    suspend fun getUserProfile(): UserProfile? {
        val uid = auth.currentUser?.uid ?: return null

        val snapshot = usersCollection.document(uid).get().await()
        return snapshot.toObject(UserProfile::class.java)
    }

    // 🔹 Actualiza solo algunos campos del perfil
    suspend fun updateUserProfile(data: Map<String, Any>) {
        val uid = auth.currentUser?.uid ?: throw Exception("Usuario no autenticado")

        usersCollection.document(uid)
            .set(data, SetOptions.merge())
            .await()
    }
}
*/