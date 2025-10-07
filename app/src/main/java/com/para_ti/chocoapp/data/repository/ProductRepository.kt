package com.para_ti.chocoapp.data.repository

import com.para_ti.chocoapp.data.local.dao.ProductDao
import com.para_ti.chocoapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {
    val products: Flow<List<Product>> = dao.getAll()
    suspend fun insert(product: Product) = dao.insert(product)
    suspend fun update(product: Product) = dao.update(product)
    suspend fun delete(product: Product) = dao.delete(product)
}
