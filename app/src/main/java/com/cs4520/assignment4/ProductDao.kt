package com.cs4520.assignment4

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insert(Product: Product)

    @Query("SELECT * FROM Product")
    fun getAllProducts(): LiveData<List<Product>>
}