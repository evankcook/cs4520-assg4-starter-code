package com.cs4520.assignment4

import android.app.Application
import android.util.Log
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductDao
import com.cs4520.assignment4.model.ProductDatabase
import com.cs4520.assignment4.model.api.ProductFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val application: Application) {
    private var productDao: ProductDao

    init {
        val database = ProductDatabase.getInstance(application)
        productDao = database.productDao()
    }

    suspend fun getProducts(page: Int? = null): Result<List<Product>> {
        return try {
                val response = ProductFactory.makeProductService().getProductList()
                if (response.isSuccessful) {
                    productDao.deleteAllProducts()
                    val validProducts = response.body()!!.filter {
                        (it.type == "Food" || it.type == "Equipment") && it.name.isNotBlank()
                                && it.price >= 0.0
                    }
                    validProducts.forEach { product ->
                        productDao.insert(product)
                    }
                    Result.success(validProducts)
                } else {
                    throw Exception("Fetching was not successful...")
                }
            } catch (e: Exception) {
            val cachedProducts = getCachedProducts()
            if (cachedProducts.isNotEmpty()) {
                Result.success(cachedProducts)
            } else {
                Result.failure(e)
            }
            }
    }

    private suspend fun getCachedProducts(): List<Product> {
        return productDao.getAllProducts()
    }
}
