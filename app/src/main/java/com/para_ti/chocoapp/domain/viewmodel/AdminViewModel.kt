package com.para_ti.chocoapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.para_ti.chocoapp.data.firebase.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AdminViewModel(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val snapshot = db.collection("productos").get().await()
                val fetched = snapshot.documents.mapNotNull { doc ->
                    // Si guardaste el id como campo dentro del documento, toObject lo tomará.
                    // Si no, puedes crear el objeto y asignar el id manualmente:
                    val item = doc.toObject(Product::class.java)
                    item?.copy(id = doc.id)
                }
                _products.value = fetched
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar productos"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addProduct(newProduct: Product, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val id = db.collection("productos").document().id
                val productWithId = newProduct.copy(id = id)
                db.collection("productos").document(id).set(productWithId).await()
                // actualizar estado localmente para reflejar el cambio inmediatamente
                _products.value = _products.value + productWithId
                onComplete(true, null)
            } catch (e: Exception) {
                _error.value = e.message
                onComplete(false, e.message)
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteProduct(productId: String, onComplete: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                db.collection("productos").document(productId).delete().await()
                _products.value = _products.value.filterNot { it.id == productId }
                onComplete(true, null)
            } catch (e: Exception) {
                _error.value = e.message
                onComplete(false, e.message)
            } finally {
                _loading.value = false
            }
        }
    }
}
