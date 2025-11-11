package com.para_ti.chocoapp.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.para_ti.chocoapp.data.cloudinary.CloudinaryUploader
import kotlinx.coroutines.tasks.await

data class Product(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val categoria: String = "",
    val imagenUrl: String = "",
    val disponible: Boolean = true,
    val stock: Int = 0,
    val temporada: String = ""
)

class FirebaseProductRepository {

    private val db = FirebaseFirestore.getInstance()
    private val productosRef = db.collection("productos")

    // Obtener todos los productos
    suspend fun getAllProducts(): List<Product> {
        val snapshot = productosRef.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Product::class.java)?.copy(id = doc.id)
        }
    }

    //  Agregar un producto (sube imagen a Cloudinary si hay)
    suspend fun addProduct(product: Product, imagePath: String?): Result<Unit> {
        return try {
            // 1️⃣ Subir imagen a Cloudinary (si el admin seleccionó una)
            val imageUrl = if (!imagePath.isNullOrEmpty()) {
                CloudinaryUploader.uploadImage(imagePath)
            } else {
                product.imagenUrl // si ya tiene una
            }

            // 2️⃣ Crear una copia del producto con el link de Cloudinary
            val productWithImage = product.copy(imagenUrl = imageUrl ?: "")

            // 3️⃣ Subir los datos a Firestore
            productosRef.add(productWithImage).await()

            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // ✅ Eliminar producto
    suspend fun deleteProduct(productId: String): Result<Unit> {
        return try {
            productosRef.document(productId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
