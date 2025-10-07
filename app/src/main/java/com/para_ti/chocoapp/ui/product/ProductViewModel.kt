package com.para_ti.chocoapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.para_ti.chocoapp.data.repository.ProductRepository
import com.para_ti.chocoapp.domain.model.Product
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products = repository.products.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addProduct(name: String, price: Double, stock: Int) {
        viewModelScope.launch {
            repository.insert(Product(name = name, price = price, stock = stock))
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch { repository.delete(product) }
    }
}
