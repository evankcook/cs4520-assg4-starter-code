package com.cs4520.assignment4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment4.model.Product
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val repository = ProductRepository(application)

    init {
        initRefreshProducts(application)
    }

    private fun initRefreshProducts(application: Application) {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<RefreshProducts>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(application).enqueue(workRequest)
    }

    fun getProducts(page: Int? = null) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getProducts(page)
                result.onSuccess {
                    _products.value = result.getOrNull()
                }
                result.onFailure {
                    throw Exception("Fetching data failed...")
                }
            } catch (e: Exception) {
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

}