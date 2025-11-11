package com.para_ti.chocoapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.para_ti.chocoapp.data.firebase.FirebaseProductRepository
import com.para_ti.chocoapp.data.firebase.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: FirebaseProductRepository = FirebaseProductRepository()
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _products.value = repository.getAllProducts()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addProduct(product: Product, imagePath: String?) {
        viewModelScope.launch {
            val result = repository.addProduct(product, imagePath)
            if (result.isSuccess) {
                loadProducts()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            repository.deleteProduct(id)
            loadProducts()
        }
    }
}
