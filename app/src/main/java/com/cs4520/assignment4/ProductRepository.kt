package com.cs4520.assignment4

import android.content.Context
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductDao
import com.cs4520.assignment4.model.ProductDatabase
import com.cs4520.assignment4.model.api.ProductFactory

class ProductRepository(private val context: Context) {
    private var productDao: ProductDao

    init {
        val database = ProductDatabase.getInstance(context)
        productDao = database.productDao()
    }

    suspend fun getProducts(page: Int? = null): Result<List<Product>> {
        return try {
                val response = ProductFactory.makeProductService().getProductList()
                if (response.isSuccessful) {
                    val validProducts = response.body()!!.filter {
                        (it.type == "Food" || it.type == "Equipment") && it.name.isNotBlank()
                                && it.price >= 0.0
                    }
                    // Go to cached data if none of the retrieved data is valid
                    if (validProducts.isEmpty()) {
                        throw Exception("API returned no valid data...")
                    }
                    productDao.deleteAllProducts()
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
