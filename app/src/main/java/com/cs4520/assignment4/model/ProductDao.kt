package com.cs4520.assignment4.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)

    @Query("DELETE FROM Product")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM Product")
    suspend fun getAllProducts(): List<Product>
}