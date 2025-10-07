package com.para_ti.chocoapp.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.para_ti.chocoapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert suspend fun insert(product: Product)
    @Update suspend fun update(product: Product)
    @Delete suspend fun delete(product: Product)
    @Query("SELECT * FROM products") fun getAll(): Flow<List<Product>>
}
