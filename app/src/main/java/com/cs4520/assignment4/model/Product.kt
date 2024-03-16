package com.cs4520.assignment4.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Product")
data class Product(val type: String,
                   val name: String,
                   val expiryDate: String?,
                   val price: Double,
                   @PrimaryKey(autoGenerate = false) val id: Int? = null)