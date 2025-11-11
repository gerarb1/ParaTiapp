package com.para_ti.chocoapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CartItem(
    val id: String = "",
    val nombre: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 0,
    val imagenUrl: String = ""
)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun loadCart() {
        // Si quisieras cargar de Firebase o memoria local, aquí iría.
    }

    fun addItem(item: CartItem) {
        val currentList = _cartItems.value.toMutableList()
        val existing = currentList.find { it.id == item.id }

        if (existing != null) {
            val updated = existing.copy(cantidad = existing.cantidad + item.cantidad)
            currentList[currentList.indexOf(existing)] = updated
        } else {
            currentList.add(item)
        }
        _cartItems.value = currentList
    }

    fun removeItem(id: String) {
        _cartItems.value = _cartItems.value.filter { it.id != id }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}

